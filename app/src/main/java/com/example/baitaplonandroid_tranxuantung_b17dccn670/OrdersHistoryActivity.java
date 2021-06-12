package com.example.baitaplonandroid_tranxuantung_b17dccn670;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter.OrderHistoryAdapter;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.OrderHistory;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_User;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrdersHistoryActivity extends AppCompatActivity {

    private Button buttonBack;
    private RecyclerView recyclerViewOrdersHistory;
    private List<OrderHistory> orderHistoryList;
    private OrderHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);

        recyclerViewOrdersHistory = findViewById(R.id.recyclerViewOrdersHistory);
        adapter = new OrderHistoryAdapter(getBaseContext());

        GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 1);
        recyclerViewOrdersHistory.setLayoutManager(layoutManager);

        buttonBack = findViewById(R.id.buttonOrdersHistoryBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        getOrdersHistoryList();
    }

    private void getOrdersHistoryList() {
        orderHistoryList = new ArrayList<>();
        String url = "https://eprojectbytranxuantung.herokuapp.com/api/orders/getOrderHistoryByUserId?userId=";
        AndroidNetworking.get(url + Static_User.id).setPriority(Priority.HIGH).build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++) {
                            try {
                                JSONObject orderHistoryJson = response.getJSONObject(i);
                                Log.d("order history", orderHistoryJson.toString());
                                long id = orderHistoryJson.getLong("id");
                                String recipientName = orderHistoryJson.getString("recipientName");
                                String recipientPhoneNumber = orderHistoryJson.getString("recipientPhoneNumber");
                                String createdAt = orderHistoryJson.getString("createdAt");
                                String status = orderHistoryJson.getString("status");
                                String paymentMethod = orderHistoryJson.getString("paymentMethod");
                                float totalPayment = Float.parseFloat(orderHistoryJson.getString("totalPayment"));
                                OrderHistory orderHistory = new OrderHistory(id, recipientName, recipientPhoneNumber, createdAt, status, paymentMethod, totalPayment);
                                Log.d("order history 2", orderHistory.toString());
                                orderHistoryList.add(orderHistory);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        Log.d("history", String.valueOf(orderHistoryList.size()));
                        adapter.setData(orderHistoryList);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        recyclerViewOrdersHistory.setAdapter(adapter);
    }
}