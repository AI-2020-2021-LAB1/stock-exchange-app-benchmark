package com.project.benchmark.algorithm;

import java.sql.Timestamp;

public class Configurations {
    private int id;
    private String name;
    private Timestamp createdAt;
    private boolean archived;
    private boolean registration;
    private int certaintyLevel;
    private int loginAllStocks;
    private int loginOwnedStocks;
    private int loginUserOrders;
    private int loginMakeOrder;
    private int allStocksMakeOrder;
    private int allStocksEnd;
    private int ownedStocksMakeOrder;
    private int ownedStocksEnd;
    private int userOrdersMakeOrder;
    private int userOrdersEnd;
    private int userOrdersDeleteOrder;
    private int makeOrderBuyOrder;
    private int makeOrderSellOrder;
    private int noOfOperations;
    private int noOfMoney;

    public Configurations(int id, String name, Timestamp createdAt, boolean archived, boolean registration, int certaintyLevel, int loginAllStocks, int loginOwnedStocks, int loginUserOrders, int loginMakeOrder, int allStocksMakeOrder, int allStocksEnd, int ownedStocksMakeOrder, int ownedStocksEnd, int userOrdersMakeOrder, int userOrdersEnd, int userOrdersDeleteOrder, int makeOrderBuyOrder, int makeOrderSellOrder, int noOfOperations, int noOfMoney) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.archived = archived;
        this.registration = registration;
        this.certaintyLevel = certaintyLevel;
        this.loginAllStocks = loginAllStocks;
        this.loginOwnedStocks = loginOwnedStocks;
        this.loginUserOrders = loginUserOrders;
        this.loginMakeOrder = loginMakeOrder;
        this.allStocksMakeOrder = allStocksMakeOrder;
        this.allStocksEnd = allStocksEnd;
        this.ownedStocksMakeOrder = ownedStocksMakeOrder;
        this.ownedStocksEnd = ownedStocksEnd;
        this.userOrdersMakeOrder = userOrdersMakeOrder;
        this.userOrdersEnd = userOrdersEnd;
        this.userOrdersDeleteOrder = userOrdersDeleteOrder;
        this.makeOrderBuyOrder = makeOrderBuyOrder;
        this.makeOrderSellOrder = makeOrderSellOrder;
        this.noOfOperations = noOfOperations;
        this.noOfMoney = noOfMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isRegistration() {
        return registration;
    }

    public void setRegistration(boolean registration) {
        this.registration = registration;
    }

    public int getCertaintyLevel() {
        return certaintyLevel;
    }

    public void setCertaintyLevel(int certaintyLevel) {
        this.certaintyLevel = certaintyLevel;
    }

    public int getLoginAllStocks() {
        return loginAllStocks;
    }

    public void setLoginAllStocks(int loginAllStocks) {
        this.loginAllStocks = loginAllStocks;
    }

    public int getLoginOwnedStocks() {
        return loginOwnedStocks;
    }

    public void setLoginOwnedStocks(int loginOwnedStocks) {
        this.loginOwnedStocks = loginOwnedStocks;
    }

    public int getLoginUserOrders() {
        return loginUserOrders;
    }

    public void setLoginUserOrders(int loginUserOrders) {
        this.loginUserOrders = loginUserOrders;
    }

    public int getLoginMakeOrder() {
        return loginMakeOrder;
    }

    public void setLoginMakeOrder(int loginMakeOrder) {
        this.loginMakeOrder = loginMakeOrder;
    }

    public int getAllStocksMakeOrder() {
        return allStocksMakeOrder;
    }

    public void setAllStocksMakeOrder(int allStocksMakeOrder) {
        this.allStocksMakeOrder = allStocksMakeOrder;
    }

    public int getAllStocksEnd() {
        return allStocksEnd;
    }

    public void setAllStocksEnd(int allStocksEnd) {
        this.allStocksEnd = allStocksEnd;
    }

    public int getOwnedStocksMakeOrder() {
        return ownedStocksMakeOrder;
    }

    public void setOwnedStocksMakeOrder(int ownedStocksMakeOrder) {
        this.ownedStocksMakeOrder = ownedStocksMakeOrder;
    }

    public int getOwnedStocksEnd() {
        return ownedStocksEnd;
    }

    public void setOwnedStocksEnd(int ownedStocksEnd) {
        this.ownedStocksEnd = ownedStocksEnd;
    }

    public int getUserOrdersMakeOrder() {
        return userOrdersMakeOrder;
    }

    public void setUserOrdersMakeOrder(int userOrdersMakeOrder) {
        this.userOrdersMakeOrder = userOrdersMakeOrder;
    }

    public int getUserOrdersEnd() {
        return userOrdersEnd;
    }

    public void setUserOrdersEnd(int userOrdersEnd) {
        this.userOrdersEnd = userOrdersEnd;
    }

    public int getUserOrdersDeleteOrder() {
        return userOrdersDeleteOrder;
    }

    public void setUserOrdersDeleteOrder(int userOrdersDeleteOrder) {
        this.userOrdersDeleteOrder = userOrdersDeleteOrder;
    }

    public int getMakeOrderBuyOrder() {
        return makeOrderBuyOrder;
    }

    public void setMakeOrderBuyOrder(int makeOrderBuyOrder) {
        this.makeOrderBuyOrder = makeOrderBuyOrder;
    }

    public int getMakeOrderSellOrder() {
        return makeOrderSellOrder;
    }

    public void setMakeOrderSellOrder(int makeOrderSellOrder) {
        this.makeOrderSellOrder = makeOrderSellOrder;
    }

    public int getNoOfOperations() {
        return noOfOperations;
    }

    public void setNoOfOperations(int noOfOperations) {
        this.noOfOperations = noOfOperations;
    }

    public int getNoOfMoney() {
        return noOfMoney;
    }

    public void setNoOfMoney(int noOfMoney) {
        this.noOfMoney = noOfMoney;
    }
}
