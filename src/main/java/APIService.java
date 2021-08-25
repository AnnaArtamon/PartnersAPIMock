import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class APIService {
    private ArrayList<String> orders = new ArrayList<>();

    public JSONObject acceptOrders(JSONObject ordersJson) {
        String orderId = ordersJson.getString("id");
        int orderNumber = ordersJson.getInt("number");
        JSONObject responseJson = new JSONObject();

        responseJson.put("accepted", new JSONArray());
        responseJson.put("rejected", new JSONArray());

        if(orderNumber%2 == 0){
            responseJson.getJSONArray("accepted").put(orderId);
        }
        else {
//            responseJson.getJSONArray("rejected").put(orderId);
            JSONObject rejectedOnes = new JSONObject();
            rejectedOnes.put("order_id", orderId);
            rejectedOnes.put("error", "Отсутствует на складе");

            responseJson.getJSONArray("rejected").put(rejectedOnes);
        }

        orders.add(orderId);

        return responseJson;
    }

    public JSONObject acceptOrdersStatus(JSONArray statusArray) {
        JSONObject responseJson = new JSONObject();

        responseJson.put("accepted", new JSONArray());
        responseJson.put("rejected", new JSONArray());

        statusArray.forEach(j -> {
            JSONObject orderJson = (JSONObject) j;
            String orderId = orderJson.getString("order_id");

            if(orders.contains(orderId)){
                responseJson.getJSONArray("accepted").put(orderId);
            }
            else {
                JSONObject rejectedOnes = new JSONObject();
                rejectedOnes.put("order_id", orderId);
                rejectedOnes.put("error", "Заказ не найден");

                responseJson.getJSONArray("rejected").put(rejectedOnes);
            }
        });

        return responseJson;
    }
    public JSONObject acceptInvoices(JSONObject invoicesObject) {
        JSONObject responseJson = new JSONObject();

        responseJson.put("accepted", new JSONArray());
        responseJson.put("rejected", new JSONArray());
        JSONArray invoicesArray = invoicesObject.getJSONArray("invoices");
        invoicesArray.forEach(j -> {
            JSONObject invoiceJson = (JSONObject) j;
            String invoiceId = invoiceJson.getString("id");
            String invoiceNumber = invoiceJson.getString("number");
            int invoiceLastFigure = Integer.parseInt(invoiceNumber.substring(invoiceNumber.length()-1));


            if(invoiceLastFigure%2 == 1){
                responseJson.getJSONArray("accepted").put(invoiceId);
            }
            else {
                JSONObject rejectedOnes = new JSONObject();
                rejectedOnes.put("id", invoiceId);
                rejectedOnes.put("error_code", "NOTFOUND");
                rejectedOnes.put("error_description", "Not found");

                responseJson.getJSONArray("rejected").put(rejectedOnes);
            }
        });

        return responseJson;
    }
}
