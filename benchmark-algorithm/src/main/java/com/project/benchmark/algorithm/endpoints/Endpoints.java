package com.project.benchmark.algorithm.endpoints;

public class Endpoints {
    //Adres do serwisu
    public static final String address = "http://193.33.111.196:8000";

    //Token logowania [GET]
    public static final String OAUTH_TOKEN = "/oauth/token";

    // oauth [POST]
    public static final String OAUTH = "/oauth";

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

    /* Endpoint do listy użytkowników [GET]
    Pozostałe endpointy:
    /api/user/{id} - [GET] - pobranie konkretnego użytkownika
    /api/user/{id}/order - [GET] - pobranie listy zleceń użytkownika
    /api/user/{id}/stock - [GET] - pobranie listy akcji użytkownika
     */
    public static final String API_USER = "/api/user";

    // Posiadane zlecenia uzytkownika [GET]
    public static final String USER_OWNED_ORDERS = "/api/user/order/owned";

    // Transakcje użytkownika [GET]
    public static final String USER_OWNED_TRANSACTIONS = "/api/user/transaction/owned";
}
