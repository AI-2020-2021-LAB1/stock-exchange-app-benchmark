package com.project.benchmark.algorithm;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.order.NewOrderTO;
import com.project.benchmark.algorithm.dto.order.OrderFiltersTO;
import com.project.benchmark.algorithm.dto.order.OrderTO;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.*;
import com.project.benchmark.algorithm.dto.user.UserDetailsTO;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.BackendCoreService;
import com.project.benchmark.algorithm.service.StockService;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import com.project.benchmark.algorithm.service.UserDetailsService;
import com.project.benchmark.algorithm.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.service.admin.AdminOrderService;
import com.project.benchmark.algorithm.service.admin.AdminStockService;
import com.project.benchmark.algorithm.service.admin.AdminTagService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Algorithm extends BackendCoreService {

    int algPercentages[] = {33, 33, 24, 10, 30, 70, 30, 70, 15, 70, 15, 50, 50};
    int numOfOperations = 20;
    int stockAmount = 20;
    int stockMaxPrice = 100;
    int numOfUsers = 3;
    int maxItemsPerPage = 1000;
    List<String> firstName = new ArrayList<>();
    List<String> lastName = new ArrayList<>();
    List<String> email = new ArrayList<>();
    List<String> password = new ArrayList<>();
    List<String> authorization = new ArrayList<>();
    List<StockTO> allStocks = new ArrayList<>();
    List<UserDetailsTO> users = new ArrayList<>();
    List<OrderTO> userOrders = new ArrayList<>();
    List<StockTO> userStocks = new ArrayList<>();
    AdminStockService adminStockService;
    AdminOrderService adminOrderService;
    UserService userService;
    UserDetailsService userDetailsService;

    private static final LinkedBlockingQueue<ResponseTO> responseQueue = new LinkedBlockingQueue<>();

    public Algorithm(LinkedBlockingQueue<ResponseTO> queue) {
        super(queue);
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private void generateUserData() {
        String pass = "password";
        password.add(encodePassword(pass));
        int targetStringLength = 10;

        StringBuilder bufferFirstName = new StringBuilder(targetStringLength);
        int randomLimitedInt = 'A' + (int) (new Random().nextFloat() * ('Z' - 'A' + 1));
        bufferFirstName.append((char) randomLimitedInt);
        for (int i = 0; i < targetStringLength; i++) {
            randomLimitedInt = 'a' + (int) (new Random().nextFloat() * ('z' - 'a' + 1));
            bufferFirstName.append((char) randomLimitedInt);
        }
        firstName.add(bufferFirstName.toString());

        StringBuilder bufferLastName = new StringBuilder(targetStringLength);
        randomLimitedInt = 'A' + (int) (new Random().nextFloat() * ('Z' - 'A' + 1));
        bufferLastName.append((char) randomLimitedInt);
        for (int i = 0; i < targetStringLength; i++) {
            randomLimitedInt = 'a' + (int) (new Random().nextFloat() * ('z' - 'a' + 1));
            bufferLastName.append((char) randomLimitedInt);
        }
        lastName.add(bufferLastName.toString());
        email.add("benchmark_" + bufferFirstName.toString() + "_" + bufferLastName.toString() + "@gmail.com");
    }

    private void register(int i) throws JsonProcessingException {
        generateUserData();
        RegisterUserTO body = new RegisterUserTO();
        body.setEmail(email.get(i));
        body.setPassword(password.get(i));
        body.setFirstName(firstName.get(i));
        body.setLastName(lastName.get(i));
        UserService userService = new UserService(responseQueue);
        userService.register(body, "BENCHMARK");
    }

    private String login(String email, String password) {
        LoginUserTO user = new LoginUserTO();
        user.setUsername(email);
        user.setPassword(password);
        UserService userService = new UserService(responseQueue);
        return userService.login(user).getData();
    }

    private void getUserDetails(String email, String password) {
        userService = new UserService(new LinkedBlockingQueue<>());
        String auth = login(email, password);
        authorization.add(auth);
        userDetailsService = new UserDetailsService(auth, new LinkedBlockingQueue<>());
        ResponseDataTO<UserDetailsTO> details = userDetailsService.getUserDetails();
        users.add(details.getData());
    }

    private String loginAdmin() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("admin@admin.pl");
        user.setPassword("Admin!23");
        UserService userService = new UserService(responseQueue);
        return userService.login(user).getData();
    }

    private void getAllStocks() {
        String auth = loginAdmin();
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("name", true);
        int i = 0;
        do {
            PageParams params = new PageParams(i, maxItemsPerPage, Collections.singletonList(sort));
            filters.setPageParams(params);
            StockService stockService = new StockService(auth, responseQueue);
            var response = stockService.getStocks(filters);
            if (response.getData().size() < maxItemsPerPage)
                break;
            i++;
        } while (true);
    }

    private void getStocksByTag() {
        String auth = loginAdmin();
        StockFiltersTO filters = new StockFiltersTO();
        filters.setTag("BENCHMARK");
        SortParams sort = new SortParams("name", true);
        int i = 0;
        do {
            PageParams params = new PageParams(i, maxItemsPerPage, Collections.singletonList(sort));
            filters.setPageParams(params);
            StockService stockService = new StockService(auth, responseQueue);
            var response = stockService.getStocks(filters);
            if (response.getData().size() == 0)
                break;
            else {
                allStocks.addAll(response.getData());
                if (response.getData().size() < maxItemsPerPage)
                    break;
            }
            i++;
        } while (true);
    }

    private void createStock(int iter) throws JsonProcessingException {
        String auth = loginAdmin();
        adminStockService = new AdminStockService(auth, responseQueue);
        NewStockTO stock = generateRandomStock(iter);
        var response = adminStockService.createStock(stock, "BENCHMARK");
    }

    private void getOwnedStocks(int iter) {
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("id", true);
        userDetailsService = new UserDetailsService(authorization.get(iter), responseQueue);
        int i = 0;
        do {
            PageParams params = new PageParams(i, maxItemsPerPage, Collections.singletonList(sort));
            filters.setPageParams(params);
            var response = userDetailsService.getOwnedStocks(filters);
            if (response.getData().size() == 0)
                break;
            else {
                userStocks.addAll(response.getData());
                if (response.getData().size() < maxItemsPerPage)
                    break;
            }
            i++;
        } while (true);
    }

    private void getOwnedOrders(int iter) {
        OrderFiltersTO filters = new OrderFiltersTO();
        SortParams sort = new SortParams("id", true);
        userDetailsService = new UserDetailsService(authorization.get(iter), responseQueue);
        int i = 0;
        do {
            PageParams params = new PageParams(i, maxItemsPerPage, Collections.singletonList(sort));
            filters.setPageParams(params);
            var response = userDetailsService.getOwnedOrders(filters);
            if (response.getData().size() == 0)
                break;
            else {
                userOrders.addAll(response.getData());
                if (response.getData().size() < maxItemsPerPage)
                    break;
            }
            i++;
        } while (true);
    }

    private NewStockTO generateRandomStock(int iter) {
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
            String a = df.format(new Random().nextDouble() * new Random().nextInt(stockMaxPrice));
            price = Double.parseDouble(a.replace(",", "."));
        } while (price <= 0.0);
        newStock.setCurrentPrice(price);
        newStock.setName("benchmark_" + stringBuilder.toString());
        newStock.setPriceChangeRatio(0.0);
        StockOwnerTO owner = new StockOwnerTO();
        owner.setAmount(newStock.getAmount());
        StockUserTO user = new StockUserTO();
        user.setId(users.get(iter).getId());
        owner.setUser(user);
        newStock.setOwners(Collections.singletonList(owner));
        return newStock;
    }

    private void createOrder(int iter, String type) throws JsonProcessingException {
        NewOrderTO order = createExampleOrder(iter, type);
        adminOrderService = new AdminOrderService(authorization.get(iter), responseQueue);
        adminOrderService.createOrder(order);
    }

    private NewOrderTO createExampleOrder(int iter, String type) {
        NewOrderTO newOrder = new NewOrderTO();
        long amount = new Random().nextInt(userStocks.get(iter).getAmount().intValue());
        if (amount == 0) amount = 1;
        userStocks.get(iter).setAmount(userStocks.get(iter).getAmount() - amount);
        newOrder.setAmount(amount);
        newOrder.setRemainingAmount(amount);
        newOrder.setDateClosing(OffsetDateTime.now().plusDays(3));
        newOrder.setDateCreation(OffsetDateTime.now());
        newOrder.setDateExpiration(OffsetDateTime.now().plusDays(3));
        newOrder.setOrderType(type);
        newOrder.setPrice(userStocks.get(iter).getCurrentPrice());
        newOrder.setPriceType("EQUAL");
        newOrder.setStock(userStocks.get(iter));
        return newOrder;
    }

    private void deactivateOrder() throws JsonProcessingException {
        if (userOrders.size() > 0) {
            adminOrderService.deactivateOrder(userOrders.get(0).getId());
            userOrders.remove(0);
        }
    }

    public void removeTag() {
        String auth = loginAdmin();
        String tag = "BENCHMARK";
        AdminTagService adminTagService = new AdminTagService(auth, responseQueue);
        adminTagService.removeTag(tag);
    }

    private void AlgorithmMain() throws IOException {

        removeTag();
        int iter = 0; // do wykorzystania w przypadku wielowątkowości

        // Wczytywanie danych z komunikatu
        // Przypisanie danych do algPercentages, numOfOperations

        algPercentages[1] = algPercentages[0] + algPercentages[1];
        algPercentages[2] = algPercentages[1] + algPercentages[2];
        algPercentages[3] = algPercentages[2] + algPercentages[3];
        algPercentages[5] = algPercentages[4] + algPercentages[5];
        algPercentages[7] = algPercentages[6] + algPercentages[7];
        algPercentages[9] = algPercentages[8] + algPercentages[9];
        algPercentages[10] = algPercentages[9] + algPercentages[10];
        algPercentages[12] = algPercentages[11] + algPercentages[12];

        for (int i = 0; i < numOfUsers; i++) {
            generateUserData();
            register(i);
            getUserDetails(email.get(i), password.get(i));
            createStock(i);
        }

        getStocksByTag();
        getOwnedOrders(iter);
        getOwnedStocks(iter);

        while (numOfOperations > 0) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 101);
            if (randomNum <= algPercentages[0]) {
                getAllStocks();
                numOfOperations--;
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[4]) {
                    if (randomNum <= algPercentages[11]) {
                        if (userStocks.get(iter).getAmount() == 0)
                            createOrder(iter, "BUYING_ORDER");
                    } else if (userStocks.get(iter).getAmount() == 0)
                        createOrder(iter, "SELLING_ORDER");
                    numOfOperations--;
                }
            } else if (randomNum <= algPercentages[1]) {
                getOwnedStocks(iter);
                numOfOperations--;
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[6]) {
                    if (randomNum <= algPercentages[11]) {
                        if (userStocks.get(iter).getAmount() == 0)
                            createOrder(iter, "BUYING_ORDER");
                    } else if (userStocks.get(iter).getAmount() == 0)
                        createOrder(iter, "SELLING_ORDER");
                    numOfOperations--;
                }
            } else if (randomNum <= algPercentages[2]) {
                getOwnedOrders(iter);
                numOfOperations--;
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[8]) {
                    if (randomNum <= algPercentages[11]) {
                        if (userStocks.get(iter).getAmount() == 0)
                            createOrder(iter, "BUYING_ORDER");
                    } else if (userStocks.get(iter).getAmount() == 0)
                        createOrder(iter, "SELLING_ORDER");
                    numOfOperations--;
                } else if (randomNum > algPercentages[9]) {
                    if (userOrders.size() > 0)
                        deactivateOrder();
                    numOfOperations--;
                }
            } else {
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[11]) {
                    if (userStocks.get(iter).getAmount() == 0)
                        createOrder(iter, "BUYING_ORDER");
                } else if (userStocks.get(iter).getAmount() == 0)
                    createOrder(iter, "SELLING_ORDER");
                numOfOperations--;
            }
        }
    }
}
