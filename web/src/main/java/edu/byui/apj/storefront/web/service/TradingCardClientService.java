package edu.byui.apj.storefront.web.service;

import edu.byui.apj.storefront.web.model.TradingCard;
import lombok.Getter;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class TradingCardClientService {
    private static String urlString = "http://localhost:8081";

    private static HttpResponse setUpConnection(String endpoint) throws IOException {
        try {
            URL url = new URL(urlString + endpoint);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return new HttpResponse(reader, connection);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    private static void closeConnection(HttpsURLConnection connection) {
        connection.disconnect();
    }
    // Returns the list of cards starting at position page * size and returning size elements.
    public static ArrayList<TradingCard> getAllCardsPaginated(int page, int size)  {
        try {
            String endpoint = "/api/cards";

            HttpResponse response  = setUpConnection(endpoint);

            String line;
            while ((line = response.getReader().readLine()) != null) {
                System.out.println(line);
            }

            response.getReader().close();
            response.getConnection().disconnect();

            return null;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Returns the list resulting in filtering by minPrice, maxPrice or specialty, then sorting by sort
    //  sort can be "name" or "price"
    public static ArrayList<TradingCard> filterAndSort(BigDecimal minPrice, BigDecimal maxPrice, String specialty, String sort){
        return null;
    }

    // Returns the list of cards resulting in the query string (case-insensitive) found in the name or contribution.
    public static ArrayList<TradingCard> searchByNameOrContribution(String query){
        return null;
    }
}




class HttpResponse {
    private final BufferedReader reader;
    private final HttpsURLConnection connection;

    public HttpResponse(BufferedReader reader, HttpsURLConnection connection) {
        this.reader = reader;
        this.connection = connection;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public HttpsURLConnection getConnection() {
        return connection;
    }
}
