package me.philcali.shopitron.data.model;

import java.util.Map;

public interface IAuthNonce {
    long getExpiresIn();

    String getId();

    Map<String, String> getParams();
}
