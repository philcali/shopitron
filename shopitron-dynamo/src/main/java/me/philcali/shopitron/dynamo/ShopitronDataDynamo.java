package me.philcali.shopitron.dynamo;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import me.philcali.shopitron.data.IShopitronData;
import me.philcali.shopitron.data.model.IAuthNonce;
import me.philcali.shopitron.data.model.IShopAccount;
import me.philcali.shopitron.data.model.ITeamAccount;
import me.philcali.shopitron.dynamo.exception.ShopitronDataException;
import me.philcali.shopitron.dynamo.model.AuthNonceDynamo;
import me.philcali.shopitron.dynamo.model.OAuthAccessDynamo;
import me.philcali.shopitron.dynamo.model.ShopAccountDynamo;
import me.philcali.shopitron.dynamo.model.TeamAccountDynamo;

public class ShopitronDataDynamo implements IShopitronData {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopitronDataDynamo.class);
    private static final long TEN_MINUTES = TimeUnit.MINUTES.toSeconds(10);

    private final Table authNonces;
    private final Table teamAccounts;
    private final Table shopAccounts;
    private final Index shopDomainIndex;

    public ShopitronDataDynamo(final DynamoDB db) {
        this(db, new ShopitronDataDynamoConfig());
    }

    public ShopitronDataDynamo(final DynamoDB db, final ShopitronDataDynamoConfig config) {
        this.authNonces = db.getTable(config.getNonceTable());
        this.teamAccounts = db.getTable(config.getTeamTable());
        this.shopAccounts = db.getTable(config.getShopTable());
        this.shopDomainIndex = this.shopAccounts.getIndex(config.getShopDomainIndex());
    }

    public boolean delete(IAuthNonce nonce) {
        boolean success = true;
        try {
            authNonces.deleteItem("id", nonce.getId());
        } catch (AmazonServiceException se) {
            LOGGER.error("Failed to delete nonce {}", nonce.getId(), se);
            success = false;
        }
        return success;
    }

    @Override
    public boolean delete(IShopAccount shop) {
        boolean success = true;
        try {
            shopAccounts.deleteItem(
                    "teamId",shop.getTeamId(),
                    "domain", shop.getDomain());
        } catch (AmazonServiceException se) {
            LOGGER.error("Failed to delete shop account {} - {}",
                    shop.getTeamId(),
                    shop.getDomain(), se);
            success = false;
        }
        return success;
    }

    @Override
    public boolean delete(ITeamAccount teamAccount) {
        boolean success = true;
        try {
            teamAccounts.deleteItem("id", teamAccount.getId());
        } catch (AmazonServiceException se) {
            LOGGER.error("Failed to delete team account {}", teamAccount.getId(), se);
            success = false;
        }
        return success;
    }

    @Override
    public IAuthNonce generateAuthNonce(Map<String, String> params) {
        Item item = new Item()
                .with("id", UUID.randomUUID().toString())
                .withLong("expiresIn", now() + TEN_MINUTES)
                .withMap("params", params);
        try {
            authNonces.putItem(item);
            return new AuthNonceDynamo(item);
        } catch (AmazonServiceException se) {
            throw new ShopitronDataException(se);
        }
    }

    @Override
    public Optional<IShopAccount> getShopByDomain(String domain) {
        Item item = shopAccounts.getItem("domain", domain);
        return Optional.ofNullable(item).map(ShopAccountDynamo::new);
    }

    @Override
    public Stream<IShopAccount> getShopsByTeam(String team) {
        return StreamSupport.stream(shopDomainIndex.query("teamId", team).spliterator(), false)
                .map(ShopAccountDynamo::new);
    }

    @Override
    public Optional<ITeamAccount> getTeamAccount(String teamId) {
        Item item = teamAccounts.getItem("id", teamId);
        return Optional.ofNullable(item).map(TeamAccountDynamo::new);
    }

    private long now() {
        return System.currentTimeMillis() / 1000;
    }

    @Override
    public Optional<IShopAccount> save(IShopAccount shop) {
        Item item = new Item()
                .with("teamId", shop.getTeamId())
                .with("domain", shop.getDomain())
                .withLong("createdAt", shop.getCreatedAt())
                .withMap("information", new Item()
                        .with("accessToken", shop.getInformation().getAccessToken())
                        .with("domain", shop.getInformation().getDomain())
                        .with("email", shop.getInformation().getEmail())
                        .with("name", shop.getInformation().getName())
                        .with("planDisplayName", shop.getInformation().getPlanDisplayName())
                        .with("timezone", shop.getInformation().getTimezone())
                        .asMap())
                .withMap("settings", new Item()
                        .withStringSet("notifications", shop.getSettings().getNotifications())
                        .asMap());
        try {
            shopAccounts.putItem(item);
            return Optional.of(item).map(ShopAccountDynamo::new);
        } catch (AmazonServiceException se) {
            LOGGER.error("Failed to put shop account {} - {}",
                    shop.getTeamId(),
                    shop.getDomain(),
                    se);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ITeamAccount> save(ITeamAccount teamAccount) {
        Item item = new Item()
                .with("id", teamAccount.getId())
                .withLong("createdAt", teamAccount.getCreatedAt())
                .withMap("auth", OAuthAccessDynamo.toItem(teamAccount.getAuth()).asMap());
        try {
            teamAccounts.putItem(item);
            return Optional.of(item).map(TeamAccountDynamo::new);
        } catch (AmazonServiceException se) {
            LOGGER.error("Failed to save team account {}", teamAccount.getId(), se);
            return Optional.empty();
        }
    }

    @Override
    public Optional<IAuthNonce> verifyNonce(String id, long timestamp) {
        Item item = authNonces.getItem("id", id);
        Optional<IAuthNonce> nonce = Optional.ofNullable(item).map(AuthNonceDynamo::new);
        return nonce.filter(this::delete)
              .filter(auth -> auth.getExpiresIn() >= (timestamp / 1000));
    }
}
