import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.*;
import java.net.InetSocketAddress;

public class MockServer {
    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        APIService apiService = new APIService();
        System.out.println("Starting Mock Server...\nStarted");

        server.createContext("/orders", exchange -> {
            System.out.println(exchange.getRequestURI());
            InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(),"utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder valueStringBuilder = new StringBuilder();
            String line;
            while( (line = br.readLine()) != null) {
                valueStringBuilder.append(line);
                valueStringBuilder.append("\n");
            }

            String inputValue = valueStringBuilder.toString();
            System.out.println(inputValue);


            try {
                JSONArray jsonArray = new JSONArray(inputValue);
                //JSONObject jsonObject = new JSONObject(inputValue);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONObject respJSON = apiService.acceptOrders(jsonObject);

                String outputValue = respJSON.toString();
                exchange.sendResponseHeaders(200, outputValue.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(outputValue.getBytes());
                System.out.println("Response code 200");
                System.out.println("Response body:");
                System.out.println(outputValue);
                output.flush();
                exchange.close();
            }
            catch (Exception e){
                System.out.println("[Error] Exception gotten while handling request body.");
                System.out.println(e.getMessage());
            }


        });

        server.createContext("/orders/status", exchange -> {
            System.out.println(exchange.getRequestURI());
            InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(),"utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder valueStringBuilder = new StringBuilder();
            String line;
            while( (line = br.readLine()) != null) {
                valueStringBuilder.append(line);
                valueStringBuilder.append("\n");
            }

            String inputValue = valueStringBuilder.toString();
            System.out.println(inputValue);

            try {
                JSONArray jsonArray = new JSONArray(inputValue);
                JSONObject respJSON = apiService.acceptOrdersStatus(jsonArray);

                String outputValue = respJSON.toString();
                exchange.sendResponseHeaders(200, outputValue.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(outputValue.getBytes());
                System.out.println("Response code 200");
                System.out.println("Response body:");
                System.out.println(outputValue);
                output.flush();
                exchange.close();
            }
            catch (Exception e){
                System.out.println("[Error] Exception gotten while handling request body.");
                System.out.println(e.getMessage());
            }

        });

        server.createContext("/invoices", exchange -> {
            System.out.println(exchange.getRequestURI());
            InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(),"utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder valueStringBuilder = new StringBuilder();
            String line;
            while( (line = br.readLine()) != null) {
                valueStringBuilder.append(line);
                valueStringBuilder.append("\n");
            }

            String inputValue = valueStringBuilder.toString();
            System.out.println(inputValue);

            try {
                JSONObject jsonObject = new JSONObject(inputValue);
                System.out.println("Before API service invoice");
                JSONObject respJSON = apiService.acceptInvoices(jsonObject);

                String outputValue = respJSON.toString();
                exchange.sendResponseHeaders(200, outputValue.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(outputValue.getBytes());
                System.out.println("Response code 200");
                System.out.println("Response body:");
                System.out.println(outputValue);
                output.flush();
                exchange.close();
            }
            catch (Exception e){
                System.out.println("[Error] Exception gotten while handling request body.");
                System.out.println(e.getMessage());
            }

        });

        server.createContext("/", exchange -> {
            InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(),"utf-8");
            System.out.println(exchange.getRequestURI());
            try {
                BufferedReader br = new BufferedReader(isr);
                StringBuilder valueStringBuilder = new StringBuilder();
                String line;
                while( (line = br.readLine()) != null) {
                    valueStringBuilder.append(line);
                    valueStringBuilder.append("\n");
                }

                String inputValue = valueStringBuilder.toString();
                System.out.println(inputValue);
                //JSONObject jsonObject = new JSONObject(inputValue);

            }
            catch (Exception e){
                System.out.println("[Error] Exception gotten while handling request body.");
            }
            exchange.close();
        });

        server.setExecutor(null); // creates a default executor
        server.start();

    }


}
