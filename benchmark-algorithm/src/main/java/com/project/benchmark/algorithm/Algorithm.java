package com.project.benchmark.algorithm;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.response.ResponseTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.service.StockService;
import com.project.benchmark.algorithm.dto.stock.StockFiltersTO;
import com.project.benchmark.algorithm.dto.user.RegisterUserTO;
import com.project.benchmark.algorithm.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Algorithm {

    static int algPercentages[] = {33, 33, 24, 10, 30, 70, 30, 70, 15, 70, 15, 50, 50};
    static int numOfOperations = 20;
    static int moneyAmount = 1000;
    static boolean register = false;
    static String firstName;
    static String lastName;
    static String email;
    static String password;

    private static String encodePassword(String password) { return new BCryptPasswordEncoder().encode(password); }

    public static void generateUserData (){
        String www="password";
        password = encodePassword(www);
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
        firstName = bufferFirstName.toString();

        StringBuilder bufferSecondName = new StringBuilder(targetStringLength);
        randomLimitedInt = leftLimitUpp + (int) (new Random().nextFloat() * (rightLimitUpp - leftLimitUpp + 1));
        bufferSecondName.append((char) randomLimitedInt);
        for (int i = 0; i < targetStringLength; i++) {
            randomLimitedInt = leftLimitLow + (int) (new Random().nextFloat() * (rightLimitLow - leftLimitLow + 1));
            bufferSecondName.append((char) randomLimitedInt);
        }
        lastName = bufferSecondName.toString();

        email = firstName + lastName + "@gmail.pl";

    }

    private static void register() throws JsonProcessingException {
        generateUserData();
        RegisterUserTO body = new RegisterUserTO();
        body.setEmail(email);
        body.setPassword(password);
        body.setFirstName(firstName);
        body.setLastName(lastName);
        UserService userService = new UserService();
        userService.register(body);
    }

    private static String login() throws JsonProcessingException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername(email);
        user.setPassword(password);
        UserService userService = new UserService();
        return userService.login(user).getData();
    }

    private static String loginAdmin() throws JsonProcessingException {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("{userName}");
        user.setPassword("{password}");
        UserService userService = new UserService();
        return userService.login(user).getData();
    }

    private static ResponseTO<List<StockTO>> getStocks(String auth) throws IOException {
        StockFiltersTO filters = new StockFiltersTO();
        SortParams sort = new SortParams("name", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        StockService stockService = new StockService();
        return stockService.getStocks(filters, auth);
    }

    public static void main(String[] args) throws IOException {

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

        if (register) {
            register();
            System.out.println("Rejestracja");   //Rejestracja
        }

        String authorization = login();
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
            } else if (randomNum > algPercentages[0] && randomNum <= algPercentages[1]) {
                System.out.println("Sprawdzenie listy posiadanych akcji");
                //Sprawdzenie listy posiadanych akcji
                numOfOperations--;
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[6]) {
                    if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                    else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                    numOfOperations--;
                }
            } else if (randomNum > algPercentages[1] && randomNum <= algPercentages[2]) {
                System.out.println("Sprawdzenie listy aktualnych zleceń");
                //Sprawdzenie listy aktualnych zleceń
                numOfOperations--;
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[8]) {
                    if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                    else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                    numOfOperations--;
                } else if (randomNum > algPercentages[9]) {
                    System.out.println("Usunięcie zlecenia");   //Usunięcie zlecenia
                    numOfOperations--;
                }
            } else if (randomNum > algPercentages[2]) {
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                numOfOperations--;
            }
        }
    }
}
