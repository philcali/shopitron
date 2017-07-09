package me.philcali.shopitron.data.model;

import me.philcali.slack.data.oauth.OAuthAccess;

public interface ITeamAccount {
    OAuthAccess getAuth();

    long getCreatedAt();

    String getId();
}
