package me.philcali.shopitron.dynamo.model;

import com.amazonaws.services.dynamodbv2.document.Item;

import me.philcali.shopitron.data.model.IShopAccount;
import me.philcali.shopitron.data.model.IShopInformation;
import me.philcali.shopitron.data.model.IShopSettings;

public class ShopAccountDynamo implements IShopAccount {
    private final Item item;

    public ShopAccountDynamo(final Item item) {
        this.item = item;
    }

    @Override
    public long getCreatedAt() {
        return item.getLong("createdAt");
    }

    @Override
    public String getDomain() {
        return item.getString("domain");
    }

    @Override
    public IShopInformation getInformation() {
        return new ShopInformationDynamo(Item.fromMap(item.getRawMap("information")));
    }

    @Override
    public IShopSettings getSettings() {
        return new ShopSettingsDynamo(Item.fromMap(item.getRawMap("settings")));
    }

    @Override
    public String getTeamId() {
        return item.getString("teamId");
    }
}
