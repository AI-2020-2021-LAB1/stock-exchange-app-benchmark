package org.project.benchmark.app.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "configurations")
public class Configuration {

    @Id
    @GeneratedValue(generator = "CONFIGURATION_SEQUENCE")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "created_at")
    private OffsetDateTime createdAt;

    @Column(nullable = false, name = "archived")
    private boolean archived;

    @Column(nullable = false, name = "registration")
    private boolean registration;

    @Column(nullable = false, name = "certainty_level")
    private BigDecimal certaintyLevel;

    @Column(nullable = false, name = "login_all_stocks")
    private BigDecimal loginAllStocks;

    @Column(nullable = false, name = "login_owned_stocks")
    private BigDecimal loginOwnedStocks;

    @Column(nullable = false, name = "login_user_orders")
    private BigDecimal loginUserOrders;

    @Column(nullable = false, name = "login_make_order")
    private BigDecimal loginMakeOrder;

    @Column(nullable = false, name = "all_stocks_make_order")
    private BigDecimal allStocksMakeOrder;

    @Column(nullable = false, name = "all_stocks_end")
    private BigDecimal allStocksEnd;

    @Column(nullable = false, name = "owned_stocks_make_order")
    private BigDecimal ownedStocksMakeOrder;

    @Column(nullable = false, name = "owned_stocks_end")
    private BigDecimal ownedStocksEnd;

    @Column(nullable = false, name = "user_orders_make_order")
    private BigDecimal userOrdersMakeOrder;

    @Column(nullable = false, name = "user_orders_end")
    private BigDecimal userOrdersEnd;

    @Column(nullable = false, name = "user_order_delete_order")
    private BigDecimal userOrderDeleteOrder;

    @Column(nullable = false, name = "make_order_buy_order")
    private BigDecimal makeOrderBuyOrder;

    @Column(nullable = false, name = "make_order_sell_order")
    private BigDecimal makeOrderSellOrder;

    @Column(nullable = false, name = "no_of_operations")
    private BigDecimal noOfOperations;

    @Column(nullable = false, name = "no_of_money")
    private BigDecimal noOfMoney;

    @OneToMany(targetEntity = Test.class, mappedBy = "configuration")
    private List<Test> tests;
}
