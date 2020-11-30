package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.order.NewOrderTO;
import com.project.benchmark.algorithm.dto.order.OrderFiltersTO;
import com.project.benchmark.algorithm.dto.order.OrderTO;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.*;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import com.project.benchmark.algorithm.dto.user.UserDetailsTO;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.admin.AdminStockService;
import com.project.benchmark.algorithm.service.admin.AdminTagService;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class AlgorithmTest {

    static UserService userService;
    AdminStockService adminStockService;
    UserDetailsService userDetailsService;
    StockService stockService;
    OrderService adminOrderService;

    List<String> firstName = new ArrayList<>();
    List<String> lastName = new ArrayList<>();
    List<String> email = new ArrayList<>();
    List<String> password = new ArrayList<>();
    List<UserDetailsTO> users = new ArrayList<>();
    List<StockTO> stocks = new ArrayList<>();
    List<OrderTO> orders = new ArrayList<>();
    int stockAmount = 30;
    private static final LinkedBlockingQueue<ResponseTO> responseQueue = new LinkedBlockingQueue<>();

    private static String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @BeforeClass
    public static void initialize() {
        userService = new UserService(new LinkedBlockingQueue<>());
    }

    @Test
    public void testGetUserDetails()  {
        userService = new UserService(new LinkedBlockingQueue<>());
        String auth = login();
        assertNotNull(auth);
        userDetailsService = new UserDetailsService(auth, new LinkedBlockingQueue<>());
        ResponseDataTO<UserDetailsTO> details = userDetailsService.getUserDetails();
        assertNull(details.getError());
        assertNotNull(details.getData());
        users.add(details.getData());
        printUsers();
    }

    @Test
    public void testRegister() throws JsonProcessingException {
        testGenerateUserData();
        RegisterUserTO body = new RegisterUserTO();
        body.setEmail(email.get(0));
        body.setPassword(password.get(0));
        body.setFirstName(firstName.get(0));
        body.setLastName(lastName.get(0));
        UserService userService = new UserService(responseQueue);
        userService.register(body, "BENCHMARK");
        testGetUserDetails();
    }

    @Test
    public void testGenerateUserData() {
        String www = "password";
        password.add(encodePassword(www));
        int leftLimitUpp = 'A';
        int rightLimitUpp = 'Z';
        int leftLimitLow = 'a';
        int rightLimitLow = 'z';
        int targetStringLength = 10;

        StringBuilder bufferFirstName = new StringBuilder(targetStringLength);
        int randomLimitedInt = leftLimitUpp + (int) (new Random().nextFloat() * (rightLimitUpp - leftLimitUpp + 1));
        bufferFirstName.append((char) randomLimitedInt);
        for (int i = 0; i < targetStringLength; i++) {
            randomLimitedInt = leftLimitLow + (int) (new Random().nextFloat() * (rightLimitLow - leftLimitLow + 1));
            bufferFirstName.append((char) randomLimitedInt);
        }
        firstName.add(bufferFirstName.toString());

        StringBuilder bufferSecondName = new StringBuilder(targetStringLength);
        randomLimitedInt = leftLimitUpp + (int) (new Random().nextFloat() * (rightLimitUpp - leftLimitUpp + 1));
        bufferSecondName.append((char) randomLimitedInt);
        for (int i = 0; i < targetStringLength; i++) {
            randomLimitedInt = leftLimitLow + (int) (new Random().nextFloat() * (rightLimitLow - leftLimitLow + 1));
            bufferSecondName.append((char) randomLimitedInt);
        }
        lastName.add(bufferSecondName.toString());

        email.add("benchmark_" + bufferFirstName.toString() + "_" + bufferSecondName.toString() + "@gmail.com");
    }

    public void printUsers() {
        firstName.forEach(System.out::println);
        lastName.forEach(System.out::println);
        email.forEach(System.out::println);
        password.forEach(System.out::println);
    }

    private static int[] splitIntoParts(int whole, int parts) { //nieużywane
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

    private String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername(email.get(0));
        user.setPassword(password.get(0));
        return userService.login(user).getData();
    }

    private String loginAdmin() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("admin@admin.pl");
        user.setPassword("Admin!23");
        return userService.login(user).getData();
    }

    @Test
    public void testGenerateRandomStock() {
        NewStockTO newStock = new NewStockTO();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int x;
            if (new Random().nextDouble() > 0.5)
                x = 'A' + (int) (new Random().nextFloat() * ('Z' - 'A' + 1));
            else
                x = 'a' + (int) (new Random().nextFloat() * ('z' - 'a' + 1));
            stringBuilder.append((char) x);
        }
        newStock.setAbbreviation(stringBuilder.toString());
        newStock.setAmount(stockAmount);
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
        user.setId(users.get(0).getId());
        owner.setUser(user);
        newStock.setOwners(Collections.singletonList(owner));
    }

    public NewStockTO generateRandomStock() {
        NewStockTO newStock = new NewStockTO();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int x;
            if (new Random().nextDouble() > 0.5)
                x = 'A' + (int) (new Random().nextFloat() * ('Z' - 'A' + 1));
            else
                x = 'a' + (int) (new Random().nextFloat() * ('z' - 'a' + 1));
            stringBuilder.append((char) x);
        }
        newStock.setAbbreviation(stringBuilder.toString());
        newStock.setAmount(stockAmount);
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
        user.setId(users.get(0).getId());
        owner.setUser(user);
        newStock.setOwners(Collections.singletonList(owner));
        return newStock;
    }

    @Test
    public void testGetStocksByTag() {
        String auth = loginAdmin();
        StockFiltersTO filters = new StockFiltersTO();
        filters.setTag("BENCHMARK");
        SortParams sort = new SortParams("name", true);
        int i = 0;
        do {
            PageParams params = new PageParams(i, 20, Collections.singletonList(sort));
            filters.setPageParams(params);
            StockService stockService = new StockService(auth, responseQueue);
            var response = stockService.getStocks(filters);
            if (response.getData().size() == 0)
                break;
            stocks.addAll(response.getData());
            i++;
        } while(true);
        stocks.forEach(p -> System.out.println(p.getName() + " " + p.getAmount() + " " + p.getId() + " " + p.getTag() + " " + p.getCurrentPrice() + " " + p.getPriceChangeRatio() + " " + p.getAbbreviation()));
    }

    @Test
    public void testRemoveTag() {
        String auth = loginAdmin();
        String tag = "BENCHMARK";
        AdminTagService adminTagService = new AdminTagService(auth, responseQueue);
        var response = adminTagService.removeTag(tag);
        assertNull(response.getError());
        assertEquals(200, response.getParams().getStatus().intValue());
    }

    @Test
    public void testCreateStock() throws JsonProcessingException {
        testRegister();
        String auth = loginAdmin();
        adminStockService = new AdminStockService(auth, new LinkedBlockingQueue<>());
        NewStockTO stock = generateRandomStock();
        adminStockService.createStock(stock, "BENCHMARK");
    }

    @Test
    public void testRemoveStock() {
        StockFiltersTO filtersTO = new StockFiltersTO();
        filtersTO.setTag("BENCHMARK");
        String auth = loginAdmin();
        adminStockService = new AdminStockService(auth, new LinkedBlockingQueue<>());
        stockService = new StockService(auth, new LinkedBlockingQueue<>());
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        filtersTO.setPageParams(params);
        var response = stockService.getStocks(filtersTO);
        response.getData().forEach(p -> adminStockService.removeStock(p.getId()));
    }

    @Test
    public void testCreateOrder() throws JsonProcessingException {
        testCreateStock();
        testGetStocksByTag();
        testGetUserDetails();
        String auth = login();
        NewOrderTO order = createExampleBuyingOrder();
        adminOrderService = new OrderService(auth, responseQueue);
        ResponseDataTO<Void> response = adminOrderService.createOrder(order);
        assertEquals(200, response.getParams().getStatus().intValue());
    }

    private NewOrderTO createExampleBuyingOrder() {
        NewOrderTO newOrder = new NewOrderTO();
        newOrder.setAmount((long) new Random().nextInt(stockAmount));
        newOrder.setDateClosing(OffsetDateTime.now().plusDays(3));
        newOrder.setDateCreation(OffsetDateTime.now());
        newOrder.setDateExpiration(OffsetDateTime.now().plusDays(3));
        newOrder.setOrderType("BUYING_ORDER");
        newOrder.setPrice(stocks.get(0).getCurrentPrice());
        newOrder.setPriceType("EQUAL");
        newOrder.setStock(stocks.get(0));
        return newOrder;
    }

    @Test
    public void getOwnedStocks() throws JsonProcessingException {
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("id", true);
        testCreateStock();
        userDetailsService = new UserDetailsService(login(), responseQueue);
        int i = 0;
        do {
            PageParams params = new PageParams(i, 20, Collections.singletonList(sort));
            filters.setPageParams(params);
            var response = userDetailsService.getOwnedStocks(filters);
            if (response.getData().size() == 0)
                break;
            i++;
            stocks.addAll(response.getData());
        } while(true);
    }

    @Test
    public void getOwnedOrders() throws JsonProcessingException {
        OrderFiltersTO filters = new OrderFiltersTO();
        SortParams sort = new SortParams("id", true);
        testCreateOrder();
        userDetailsService = new UserDetailsService(login(), responseQueue);
        int i = 0;
        do {
            PageParams params = new PageParams(i, 1000, Collections.singletonList(sort));
            filters.setPageParams(params);
            var response = userDetailsService.getOwnedOrders(filters);
            if (response.getData().size() <1000)
                break;
            i++;
            orders.addAll(response.getData());
        } while(true);
    }

    @Test
    public void testDeactivateOrder() throws JsonProcessingException {
        ResponseDataTO<Void> response = adminOrderService.deactivateOrder(orders.get(0).getId());
        assertNull(response.getError());
        assertEquals(200, response.getParams().getStatus().intValue());
    }
}
