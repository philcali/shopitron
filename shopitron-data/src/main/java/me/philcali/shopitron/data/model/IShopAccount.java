package me.philcali.shopitron.data.model;

public interface IShopAccount {
    long getCreatedAt();

    String getDomain();

    IShopInformation getInformation();

    IShopSettings getSettings();

    String getTeamId();
}
