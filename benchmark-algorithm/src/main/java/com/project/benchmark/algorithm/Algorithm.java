package com.project.benchmark.algorithm;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.internal.ResponseTO;
import com.project.benchmark.algorithm.service.StockService;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import com.project.benchmark.algorithm.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import com.project.benchmark.algorithm.service.admin.AdminStockService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Algorithm extends  BackendCoreService {

    static int algPercentages[] = {33, 33, 24, 10, 30, 70, 30, 70, 15, 70, 15, 50, 50};
    static int numOfOperations = 20;
    static int moneyAmount = 1000;
    static boolean register = false;
    static List<String> firstName;
    static List<String> lastName;
    static List<String> email;
    static List<String> password;
    AdminStockService stockService;

    private static final LinkedBlockingQueue<ResponseTO> responseQueue = new LinkedBlockingQueue<>();

    private static String encodePassword(String password) { return new BCryptPasswordEncoder().encode(password); }

    public static void generateUserData (){
        String www="password";
        password.add(encodePassword(www));
        int leftLimitUpp = 65; // letter 'A'
        int rightLimitUpp = 90; // letter 'Z'
        int leftLimitLow = 97; // letter 'a'
        int rightLimitLow = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

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

        email.add("benchmark_" + firstName + "_" + lastName + "_" +"@gmail.com");

    }

    private static void register(String email, String password, String firstName, String lastName) throws JsonProcessingException {
        generateUserData();
        RegisterUserTO body = new RegisterUserTO();
        body.setEmail(email);
        body.setPassword(password);
        body.setFirstName(firstName);
        body.setLastName(lastName);
        UserService userService = new UserService(responseQueue);
        userService.register(body, null);
    }

    private static String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername(email);
        user.setPassword(password);
        UserService userService = new UserService(responseQueue);
        return userService.login(user).getData();
    }

    private static ResponseDataTO<List<StockTO>> getStocks(String auth) {
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        StockService stockService = new StockService(auth, responseQueue);
        return stockService.getStocks(filters);
    }

    public void createStock() throws JsonProcessingException {
        String auth = loginAdmin();
        //assertNotNull(auth);
        NewStockTO stock = createRandomStock();
        ResponseTO<Void> response = stockService.createStock(stock, auth);
        //assertNull(response.getError());
        //assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }

    private NewStockTO createRandomStock() {
        NewStockTO newStock = new NewStockTO();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int x = 'A' + (int) (new Random().nextFloat() * ('Z' - 'A' + 1));
            stringBuilder.append((char) x);
        }
        newStock.setAbbreviation(stringBuilder.toString());
        newStock.setAmount(123);
        newStock.setCurrentPrice(1.23);
        newStock.setName("abcMyFunc");
        newStock.setPriceChangeRatio(0.0);
        StockOwnerTO owner = new StockOwnerTO();
        owner.setAmount(123);
        StockUserTO user = new StockUserTO();
        user.setId(30); //change user id to correct one (between 2 and 30 should work)
        owner.setUser(user);
        newStock.setOwners(Collections.singletonList(owner));
        return newStock;
    }

    private void AlgorithmMain() throws IOException { //przerobić na metodę

        {//Coby nie przeliczać tego w "if" - możnaby do funkcji //odpowiada za obliczenie poszczególnych progów (1-33, 34-66 itd.)
            algPercentages[1] = algPercentages[0] + algPercentages[1];
            algPercentages[2] = algPercentages[1] + algPercentages[2];
            algPercentages[3] = algPercentages[2] + algPercentages[3];
            algPercentages[5] = algPercentages[4] + algPercentages[5];
            algPercentages[7] = algPercentages[6] + algPercentages[7];
            algPercentages[9] = algPercentages[8] + algPercentages[9];
            algPercentages[10] = algPercentages[9] + algPercentages[10];
            algPercentages[12] = algPercentages[11] + algPercentages[12];
        }

        //Wczytywanie danych z komunikatu.

        for (int i=0; i<3; i ++){
            generateUserData();
            register(email.get(i), password.get(i), firstName.get(i), lastName.get(i));
            System.out.println("Rejestracja" + i);   //Rejestracja
        }

        String authorization = login(email.get(0), password.get(0));
        System.out.println("Logowanie");   //Logowanie

        while (numOfOperations > 0) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 101);
            if (randomNum <= algPercentages[0]) {
                StockFiltersTO filters = new StockFiltersTO();
                getStocks(authorization);
                System.out.println("Sprawdzenie listy dostępnych akcji");
                numOfOperations--;
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[4]) {
                    if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                    else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                    numOfOperations--;
                }
            } else if (randomNum <= algPercentages[1]) {
                System.out.println("Sprawdzenie listy posiadanych akcji");
                //Sprawdzenie listy posiadanych akcji
                numOfOperations--;
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[6]) {
                    if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                    else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                    numOfOperations--;
                }
            } else if (randomNum <= algPercentages[2]) {
                System.out.println("Sprawdzenie listy aktualnych zleceń");
                //Sprawdzenie listy aktualnych zleceń
                numOfOperations--;
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[8]) {
                    if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                    else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                    numOfOperations--;
                } else if (randomNum > algPercentages[9]) {
                    System.out.println("Usunięcie zlecenia");   //Usunięcie zlecenia //order/deactivation
                    numOfOperations--;
                }
            } else {
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                numOfOperations--;
            }
        }
    }
}
