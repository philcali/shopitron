package me.philcali.shopitron.api.model;

import java.util.HashSet;
import java.util.Set;

import me.philcali.shopitron.data.model.IShopSettings;

public class ShopSettings implements IShopSettings {
    private Set<String> notifications;

    public ShopSettings addNotification(String notification) {
        this.notifications.add(notification);
        return this;
    }

    @Override
    public Set<String> getNotifications() {
        if (notifications == null) {
            notifications = new HashSet<>();
        }
        return notifications;
    }

    public void setNotifications(Set<String> notifications) {
        this.notifications = notifications;
    }
}
