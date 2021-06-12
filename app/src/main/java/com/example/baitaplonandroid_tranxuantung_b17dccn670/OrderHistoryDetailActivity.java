package com.example.baitaplonandroid_tranxuantung_b17dccn670;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter.Cart_ItemAdapter;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter.Cart_Item_InOrderHistoryAdapter;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Cart_Item;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Item;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.ItemImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDetailActivity extends AppCompatActivity {

    private TextView textViewRecipientName2, textViewRecipientPhoneNumber2, textViewCreatedAt2,
            textViewStatus2, textViewPaymentMethod2, textViewTotalPayment2;
    private Button buttonOrderHistoryDetailBack;

    private RecyclerView recyclerViewItemsInOrderHistory;
    private Cart_Item_InOrderHistoryAdapter adapter;
    private List<Cart_Item> cart_itemList;
    private long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_detail);

        textViewRecipientName2 = findViewById(R.id.textViewRecipientName2);
        textViewRecipientPhoneNumber2 = findViewById(R.id.textViewRecipientPhoneNumber2);
        textViewCreatedAt2 = findViewById(R.id.textViewCreatedAt2);
        textViewStatus2 = findViewById(R.id.textViewStatus2);
        textViewPaymentMethod2 = findViewById(R.id.textViewPaymentMethod2);
        textViewTotalPayment2 = findViewById(R.id.textViewTotalPayment2);
        buttonOrderHistoryDetailBack = findViewById(R.id.buttonOrderHistoryDetailBack);

        recyclerViewItemsInOrderHistory = findViewById(R.id.recyclerViewItemsInOrderHistory);
        adapter = new Cart_Item_InOrderHistoryAdapter(getBaseContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 1);
        recyclerViewItemsInOrderHistory.setLayoutManager(layoutManager);

        orderId = getIntent().getLongExtra("orderId", 0);
        Log.d("OrderDetailHistory", String.valueOf(orderId));

        getOrderHistory();

        buttonOrderHistoryDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), OrdersHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getOrderHistory() {
        cart_itemList = new ArrayList<>();
        String url = "https://eprojectbytranxuantung.herokuapp.com/api/orders/" + orderId;
        AndroidNetworking.get(url).setPriority(Priority.HIGH).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // get OrderInformation
                            textViewRecipientName2.setText("Recipient Name: " + response.getString("recipientName"));
                            textViewRecipientPhoneNumber2.setText("Recipient Phone Number: " + response.getString("recipientPhoneNumber"));
                            textViewCreatedAt2.setText("Order Time: " + response.getString("createdAt"));
                            textViewStatus2.setText("Order Status: " + response.getString("status"));
                            JSONObject paymentJson = response.getJSONObject("payment");
                            textViewPaymentMethod2.setText("Payment Method: " + paymentJson.getString("paymentMethod"));
                            textViewTotalPayment2.setText("Total Payment: " + paymentJson.getString("totalPayment"));

                            // get Cart_Items
                            JSONObject cartJson = response.getJSONObject("cart");
                            JSONArray cartItemsJsonArray = cartJson.getJSONArray("cart_Items");
                            for(int i=0; i<cartItemsJsonArray.length(); i++) {
                                JSONObject cartItemJsonObject = (JSONObject) cartItemsJsonArray.get(i);
                                Long cart_itemId = cartItemJsonObject.getLong("id");
                                Integer quantity = cartItemJsonObject.getInt("quantity");
                                JSONObject itemJsonObject = cartItemJsonObject.getJSONObject("item");
                                Log.d("59", itemJsonObject.toString());
                                JSONArray itemImagesJsonArray = itemJsonObject.getJSONArray("itemImages");
                                List<ItemImage> itemImageList = new ArrayList<>();
                                for(int j=0; j<itemImagesJsonArray.length(); j++) {
                                    JSONObject itemImageJsonObject = (JSONObject) itemImagesJsonArray.get(j);
                                    Log.d("63", itemImageJsonObject.toString());
                                    Long itemImageId = itemImageJsonObject.getLong("id");
                                    String itemImageURL = itemImageJsonObject.getString("url").replace("\\/", "//");
                                    Log.d("68", itemImageURL);
                                    itemImageList.add(new ItemImage(itemImageId, itemImageURL));
                                }

                                Item item = new Item();
                                item.setId(itemJsonObject.getLong("id"));
                                item.setName(itemJsonObject.getString("name"));
                                item.setSalePrice(Float.parseFloat(itemJsonObject.getString("salePrice")));
                                item.setItemImageList(itemImageList);

                                Cart_Item cart_item = new Cart_Item();
                                cart_item.setId(cart_itemId);
                                cart_item.setQuantity(quantity);
                                cart_item.setItem(item);

                                cart_itemList.add(cart_item);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.setData(cart_itemList);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        recyclerViewItemsInOrderHistory.setAdapter(adapter);
    }
}