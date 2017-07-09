package me.philcali.shopitron.dynamo.model;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.document.Item;

import me.philcali.shopitron.data.model.IShopSettings;

public class ShopSettingsDynamo implements IShopSettings {
    private final Item item;

    public ShopSettingsDynamo(final Item item) {
        this.item = item;
    }

    @Override
    public Set<String> getNotifications() {
        return item.getStringSet("notifications");
    }

}
