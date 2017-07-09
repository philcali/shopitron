package me.philcali.shopitron.dynamo.model;

import com.amazonaws.services.dynamodbv2.document.Item;

import me.philcali.shopitron.data.model.ITeamAccount;
import me.philcali.slack.data.oauth.OAuthAccess;

public class TeamAccountDynamo implements ITeamAccount {
    private final Item item;

    public TeamAccountDynamo(final Item item) {
        this.item = item;
    }

    @Override
    public OAuthAccess getAuth() {
        return new OAuthAccessDynamo(Item.fromMap(item.getRawMap("auth")));
    }

    @Override
    public long getCreatedAt() {
        return item.getLong("createdAt");
    }

    @Override
    public String getId() {
        return item.getString("id");
    }

}
