package com.project.benchmark.algorithm.endpoints;

public class Endpoints {
    //Adres do serwisu
    public static String address;

    //Token logowania [GET]
    public static final String OAUTH_TOKEN = "/oauth/token";

    //Endpoint do rejestracji nowego użytkownika [POST]
    public static final String API_REGISTER = "/api/register";

    /* Endpoint do listy akcji [GET] [POST]
    Pozostałe endpointy:
    /api/stock/{id} - [GET] [PATCH] - pobranie i aktualizowanie konkretnej akcji
    /api/stock/{id}/index - [GET] - pobranie historii indeksowania akcji    */
    public static final String API_STOCK = "/api/stock";

    /* Endpoint do listy zleceń [GET]
    Pozostałe endpointy:
    /api/order/{id} - [GET] - pobranie konkretnego zlecenia
    /api/order/{id}/deactivation - [POST] - dezaktywacja zlecenia
    /api/order/{id}/transactions - [GET] - pobranie transakcji zlecenia     */
    public static final String API_ORDER = "/api/order";

    /* Endpoint do transakcji [GET]
    Pozostałe endpointy:
    /api/transaction/{id} - [GET] - pobranie konkretnej transakcji    */
    public static final String API_TRANSACTION = "/api/transaction";
}
