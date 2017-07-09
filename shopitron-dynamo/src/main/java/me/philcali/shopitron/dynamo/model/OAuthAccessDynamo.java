package me.philcali.shopitron.dynamo.model;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;

import me.philcali.slack.data.oauth.BotAuth;
import me.philcali.slack.data.oauth.OAuthAccess;
import me.philcali.slack.data.oauth.WebhookAuth;

public class OAuthAccessDynamo extends OAuthAccess {
    public static Item toItem(OAuthAccess oauthAccess) {
        return new Item()
                .with("accessToken", oauthAccess.getAccessToken())
                .with("scope", oauthAccess.getScope())
                .with("teamName", oauthAccess.getTeamName())
                .with("teamId", oauthAccess.getTeamId())
                .withMap("bot", new Item()
                        .with("botAccessToken", oauthAccess.getBot().getBotAccessToken())
                        .with("botUserId", oauthAccess.getBot().getBotUserId())
                        .asMap())
                .withMap("incomingWebhook", new Item()
                        .with("channel", oauthAccess.getIncomingWebhook().getChannel())
                        .with("url", oauthAccess.getIncomingWebhook().getUrl())
                        .with("configurationUrl", oauthAccess.getIncomingWebhook().getConfigurationUrl())
                        .asMap());
    }

    public OAuthAccessDynamo(final Item item) {
        setAccessToken(item.getJSON("accessToken"));
        setScope(item.getString("scope"));
        setTeamName(item.getString("teamName"));
        setTeamId(item.getString("teamId"));
        // Bots
        if (item.hasAttribute("bot")) {
            BotAuth bot = new BotAuth();
            Map<String, String> botMap = item.getMap("bot");
            bot.setBotAccessToken(botMap.get("botAccessToken"));
            bot.setBotUserId(botMap.get("botUserId"));
            setBot(bot);
        }
        // Webhooks
        if (item.hasAttribute("incomingWebhook")) {
            WebhookAuth incomingWebhook = new WebhookAuth();
            Map<String, String> webhookMap = item.getMap("incomingWebhook");
            incomingWebhook.setChannel(webhookMap.get("channel"));
            incomingWebhook.setConfigurationUrl(webhookMap.get("configurationUrl"));
            incomingWebhook.setUrl(webhookMap.get("url"));
            setIncomingWebhook(incomingWebhook);
        }
    }
}
