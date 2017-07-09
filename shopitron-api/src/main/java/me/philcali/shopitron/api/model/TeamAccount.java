package me.philcali.shopitron.api.model;

import me.philcali.shopitron.data.model.ITeamAccount;
import me.philcali.slack.data.oauth.OAuthAccess;

public class TeamAccount implements ITeamAccount {
    private OAuthAccess auth;
    private long createdAt;
    private String id;

    @Override
    public OAuthAccess getAuth() {
        return auth;
    }

    @Override
    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setAuth(OAuthAccess auth) {
        this.auth = auth;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TeamAccount withAuth(OAuthAccess auth) {
        setAuth(auth);
        return this;
    }

    public TeamAccount withCreatedAt(long createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public TeamAccount withId(String id) {
        setId(id);
        return this;
    }
}
