package me.philcali.shopitron.api.model;

import me.philcali.shopitron.data.model.IShopAccount;
import me.philcali.shopitron.data.model.IShopInformation;
import me.philcali.shopitron.data.model.IShopSettings;

public class ShopAccount implements IShopAccount {
    private String teamId;
    private String domain;
    private IShopInformation information;
    private IShopSettings settings;
    private long createdAt;

    @Override
    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public IShopInformation getInformation() {
        return information;
    }

    @Override
    public IShopSettings getSettings() {
        return settings;
    }

    @Override
    public String getTeamId() {
        return teamId;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setInformation(IShopInformation information) {
        this.information = information;
    }

    public void setSettings(IShopSettings settings) {
        this.settings = settings;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public ShopAccount withCreatedAt(long createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public ShopAccount withDomain(String domain) {
        setDomain(domain);
        return this;
    }

    public ShopAccount withInformation(IShopInformation information) {
        setInformation(information);
        return this;
    }

    public ShopAccount withSettings(IShopSettings settings) {
        setSettings(settings);
        return this;
    }

    public ShopAccount withTeamId(String teamId) {
        setTeamId(teamId);
        return this;
    }

}
