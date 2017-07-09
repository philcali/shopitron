package me.philcali.shopitron.dynamo;

public class ShopitronDataDynamoConfig {
    private String tablePrefix = "Shopitron";
    private String nonceTable = "AuthNonces";
    private String teamTable = "TeamAccounts";
    private String shopTable = "ShopAccounts";
    private String shopDomainIndex = "domain-index";

    private String generateTable(String tableName) {
        return String.format("%s.%s", tablePrefix, tableName);
    }

    public String getNonceTable() {
        return generateTable(nonceTable);
    }

    public String getShopTable() {
        return generateTable(shopTable);
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public String getTeamTable() {
        return generateTable(teamTable);
    }

    public String getShopDomainIndex() {
        return shopDomainIndex;
    }

    public void setShopDomainIndex(String shopDomainIndex) {
        this.shopDomainIndex = shopDomainIndex;
    }

    public void setNonceTable(String nonceTable) {
        this.nonceTable = nonceTable;
    }

    public void setShopTable(String shopTable) {
        this.shopTable = shopTable;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public void setTeamTable(String teamTable) {
        this.teamTable = teamTable;
    }
}
