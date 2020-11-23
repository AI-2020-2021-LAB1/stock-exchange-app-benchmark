package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.stock.NewStockTO;
import com.project.benchmark.algorithm.dto.stock.StockOwnerTO;
import com.project.benchmark.algorithm.dto.stock.StockUserTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.service.admin.AdminStockService;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Random;

public class AlgorithmTest {

    AdminStockService stockService = new AdminStockService();
    UserService userService = new UserService();

    private static int[] splitIntoParts(int whole, int parts) {
        int[] arr = new int[parts];
        int remain = whole;
        int partsLeft = parts;
        for (int i = 0; partsLeft > 0; i++) {
            int size = (remain + partsLeft - 1) / partsLeft;
            arr[i] = size;
            remain -= size;
            partsLeft--;
        }
        return arr;
    }

    private String login() throws JsonProcessingException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("admin@admin.pl");
        user.setPassword("Admin!23");
        return userService.login(user).getData();
    }

    @Test
    public void  testGenerateRandomStock() {
        NewStockTO newStock = new NewStockTO();
        StringBuilder stringBuilder = new StringBuilder(); //dodać małe litery
        for (int i = 0; i < 3; i++) {
            int x;
            if (new Random().nextDouble() > 0.5)
                x = 'A' + (int) (new Random().nextFloat() * ('Z' - 'A' + 1));
            else
                x = 'a' + (int) (new Random().nextFloat() * ('z' - 'a' + 1));
            stringBuilder.append((char) x);
        }
        newStock.setAbbreviation(stringBuilder.toString());
        newStock.setAmount(new Random().nextInt(20));
        DecimalFormat df = new DecimalFormat(".##");
        double price;
        do {
            String a = df.format(new Random().nextDouble() * new Random().nextInt(100));
            price = Double.parseDouble(a.replace(",", "."));
        } while (price <= 0.0);
        newStock.setCurrentPrice(price);
        newStock.setName("benchmark_" + stringBuilder.toString());
        newStock.setPriceChangeRatio(0.0);
        StockOwnerTO owner = new StockOwnerTO();
        owner.setAmount(newStock.getAmount()); //możliwa przeróbka na przypisanie stocków do większej liczby userów
        StockUserTO user = new StockUserTO();
        user.setId(30); //user ID, zostanie zmienione na ID pobranych userów
        owner.setUser(user);
        newStock.setOwners(Collections.singletonList(owner));
    }

    @Test
    public void testLogin() throws JsonProcessingException {
        System.out.println(login());
    }

}
