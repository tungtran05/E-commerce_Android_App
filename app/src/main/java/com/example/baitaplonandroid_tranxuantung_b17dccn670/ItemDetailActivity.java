package com.example.baitaplonandroid_tranxuantung_b17dccn670;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter.ItemImagesAdapter;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_Cart;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_Cart_Item;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ItemDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ItemImagesAdapter viewPagerAdapter;
    private TextView textViewItemName, textViewItemPrice, textViewItemDescription;
    private Button buttonAddToCart;

    Long cartId;
    Long userId;
    Long itemId;
    String itemName;
    float itemPrice;
    String[] itemImageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        
        viewPager = findViewById(R.id.viewPagerItemDetailImages);
        textViewItemName = findViewById(R.id.textViewItemDetailName);
        textViewItemPrice = findViewById(R.id.textViewItemDetailPrice);
        textViewItemDescription = findViewById(R.id.textViewItemDetailDescription);
        buttonAddToCart = findViewById(R.id.buttonAddToCart);

        getDataFromIntent();

        userId = Static_User.id;
        cartId = Static_Cart.id;
        Log.d("ItemDetail - userId", String.valueOf(userId));
        Log.d("ItemDetail - cartId", String.valueOf(cartId));
        Log.d("ItemDetail - itemId", String.valueOf(itemId));

        setData();

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get quantity by cartId and itemId
                HashMap<Object, Object> hashMap = getQuantityByCartIdAndItemId();
                Long cart_Item_Id = (Long) hashMap.get("cart_Item_Id");
                Integer quantity = (Integer) hashMap.get("quantity");
                Log.d("70", String.valueOf(cart_Item_Id));
                Log.d("71", String.valueOf(quantity));

                //
                if(cart_Item_Id == 0) {
                    addItemToCart();
                }
                else {
                    updateItemInCart();
                }
            }
        });


    }

    private void getDataFromIntent() {
        if(getIntent().hasExtra("itemId") && getIntent().hasExtra("itemName") &&
            getIntent().hasExtra("itemPrice") && getIntent().hasExtra("itemImageUrls")) {

            itemId = getIntent().getLongExtra("itemId", 0);
            itemName = getIntent().getStringExtra("itemName");
            itemPrice = getIntent().getFloatExtra("itemPrice", 0);
            itemImageUrls = getIntent().getStringArrayExtra("itemImageUrls");
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        viewPagerAdapter = new ItemImagesAdapter(this, itemImageUrls);
        viewPager.setAdapter(viewPagerAdapter);
        textViewItemName.setText(itemName);
        textViewItemPrice.setText(String.valueOf(itemPrice));

        HashMap<Object, Object> hashMap = getQuantityByCartIdAndItemId();
    }

    private HashMap<Object, Object> getQuantityByCartIdAndItemId() {
        AndroidNetworking.get("https://eprojectbytranxuantung.herokuapp.com/api/cart_items" +
                "/getCart_ItemByCartIdAndItemId?cartId=" + cartId + "&itemId=" + itemId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Static_Cart_Item.id = response.getLong("id");
                            Static_Cart_Item.quantity = response.getInt("quantity");
//                            Log.d("cart_item_id", String.valueOf(Cart_Item.id));
//                            Log.d("quantity", String.valueOf(Cart_Item.quantity));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error", anError.getErrorDetail());
                    }
                });

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("cart_Item_Id", Static_Cart_Item.id);
        hashMap.put("quantity", Static_Cart_Item.quantity);
//        Log.d("cart_item_id", String.valueOf(cart_Item_Id));
//        Log.d("quantity", String.valueOf(quantity));

        return hashMap;
    }

    private void addItemToCart() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("quantity", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post("https://eprojectbytranxuantung.herokuapp.com/api/cart_items" +
                "?cartId=" + Static_Cart.id + "&itemId=" + itemId)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Add item to cart", response.toString());
                        Toast.makeText(getBaseContext(), "Add item to cart successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error", anError.getErrorDetail());
                        Toast.makeText(getBaseContext(), "Add item to cart failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateItemInCart() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("quantity", Static_Cart_Item.quantity+1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.put("https://eprojectbytranxuantung.herokuapp.com/api/cart_items/" + Static_Cart_Item.id
                + "?cartId=" + Static_Cart.id + "&itemId=" + itemId)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Add already item to cart", response.toString());
                        Toast.makeText(ItemDetailActivity.this, "Add already item to cart successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error", anError.getErrorDetail());
                        Toast.makeText(ItemDetailActivity.this, "Add already item to cart failed!", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}