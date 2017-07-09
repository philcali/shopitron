package me.philcali.shopitron.data;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import me.philcali.shopitron.data.model.IAuthNonce;
import me.philcali.shopitron.data.model.IShopAccount;
import me.philcali.shopitron.data.model.ITeamAccount;

public interface IShopitronData {
    boolean delete(IShopAccount shop);

    boolean delete(ITeamAccount teamAccount);

    IAuthNonce generateAuthNonce(Map<String, String> params);

    Optional<IShopAccount> getShopByDomain(String domain);

    Stream<IShopAccount> getShopsByTeam(String team);

    Optional<ITeamAccount> getTeamAccount(String teamId);

    Optional<IShopAccount> save(IShopAccount shop);

    Optional<ITeamAccount> save(ITeamAccount teamAccount);

    Optional<IAuthNonce> verifyNonce(String id, long timestamp);
}
