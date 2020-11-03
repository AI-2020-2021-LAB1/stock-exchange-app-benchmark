package com.project.stockexchangeappbenchmark;

import com.project.stockexchangeappbenchmark.service.BackendService;
import org.apache.http.HttpException;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Scanner;

public class BenchmarkMain {

    public static void main(String[] args) throws IOException, ValidationException, HttpException {

        new BackendService().login();
        new BackendService().loginRestEasy();
//        new BackendService().registerRestEasy();
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("(Type 'get' or 'set' now.)");
//        String getOrSet = scanner.nextLine();
//        if("get".equalsIgnoreCase(getOrSet)){
//            System.out.println("Whose info do you want to get?");
//            System.out.println("(Type a person's name now.)");
//            String name = scanner.nextLine();
//            String jsonString = getPersonData(name);
//            //JSONObject jsonObject = new JSONObject(jsonString);
//        }
//        else if("set".equalsIgnoreCase(getOrSet)){
//            System.out.println("Whose info do you want to set?");
//            System.out.println("(Type a person's name now.)");
//            String name = scanner.nextLine();
//            setPersonData(name);
//        }
//
//        scanner.close();
    }

    public static String getPersonData(String name) throws IOException{

        HttpURLConnection connection = (HttpURLConnection) new URL("http://193.33.111.196:5431").openConnection();
        connection.setRequestMethod("GET");
        String auth = "admin:admin";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));

        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            String response = "";
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response += scanner.nextLine();
                response += "\n";
            }
            scanner.close();
            System.out.println(response);

            return response;
        }

        // an error happened
        return null;
    }

    public static void setPersonData(String name) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) new URL("https://gorest.co.in/public-api/users/" + name).openConnection();

        connection.setRequestMethod("POST");

        String postData = "name=" + URLEncoder.encode(name, "UTF-8");
        /*postData += "&about=" + URLEncoder.encode(about, "UTF-8");;*/

        connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        if(responseCode == 200){
            System.out.println("POST was successful.");
        }
        else if(responseCode == 401){
            System.out.println("Wrong password.");
        }
    }
}
