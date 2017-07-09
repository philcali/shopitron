package me.philcali.shopitron.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SlackAuthUrlHandler implements RequestHandler<AuthRequest, AuthResponse> {

    @Override
    public AuthResponse handleRequest(AuthRequest input, Context context) {
        ISlackIntegration integration = new SlackIntegrationImpl(new SlackClientConfig()
                .withClientId(input.getClientId())
                .withClientSecret(input.getClientSecret())
                .withRedirectUrl(input.getRedirectUrl())
                .withScopes(input.getScopes()));
        return new AuthResponse(integration.getAuthUrl());
    }
}
