package me.philcali.shopitron.dynamo.model;

import com.amazonaws.services.dynamodbv2.document.Item;

import me.philcali.shopitron.data.model.IShopInformation;

public class ShopInformationDynamo implements IShopInformation {
    private final Item item;

    public ShopInformationDynamo(final Item item) {
        this.item = item;
    }

    @Override
    public String getAccessToken() {
        return item.getString("accessToken");
    }

    @Override
    public String getDomain() {
        return item.getString("domain");
    }

    @Override
    public String getEmail() {
        return item.getString("email");
    }

    @Override
    public String getName() {
        return item.getString("name");
    }

    @Override
    public String getPlanDisplayName() {
        return item.getString("planDisplayName");
    }

    @Override
    public String getTimezone() {
        return item.getString("timezone");
    }

}
