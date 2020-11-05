package com.project.benchmark.algorithm;

import com.project.benchmark.algorithm.service.BackendService;
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
