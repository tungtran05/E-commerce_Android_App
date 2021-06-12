package com.example.baitaplonandroid_tranxuantung_b17dccn670.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.R;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter.Cart_ItemAdapter;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Cart_Item;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Item;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.ItemImage;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_Cart;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_User;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    private RecyclerView recyclerViewCartItem;
    private Cart_ItemAdapter adapter;
    private List<Cart_Item> cart_itemList;

    private EditText editTextRecipientName, editTextRecipientPhone, editTextRecipientEmail,
            editTextDetailedAddress, editTextWard, editTextDistrict, editTextCity, editTextNote;
    private Button buttonOrder;

    public boolean check = false;

    @Override
    public void onResume() {
        cart_itemList = new ArrayList<>();
        adapter.setData(cart_itemList);
        recyclerViewCartItem.setAdapter(adapter);
        super.onResume();
    }

    public void resetEditText() {
        editTextRecipientName.setText("");
        editTextRecipientPhone.setText("");
        editTextRecipientEmail.setText("");
        editTextDetailedAddress.setText("");
        editTextWard.setText("");
        editTextDistrict.setText("");
        editTextCity.setText("");
        editTextNote.setText("");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerViewCartItem = view.findViewById(R.id.recyclerViewCartItem);
        adapter = new Cart_ItemAdapter(getActivity());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerViewCartItem.setLayoutManager(layoutManager);

        editTextRecipientName = view.findViewById(R.id.editTextRecipientName);
        editTextRecipientPhone = view.findViewById(R.id.editTextRecipientPhone);
        editTextRecipientEmail = view.findViewById(R.id.editTextRecipientEmail);
        editTextDetailedAddress = view.findViewById(R.id.editTextDetailedAddress);
        editTextWard = view.findViewById(R.id.editTextWard);
        editTextDistrict = view.findViewById(R.id.editTextDistrict);
        editTextCity = view.findViewById(R.id.editTextCity);
        editTextNote = view.findViewById(R.id.editTextNote);
        buttonOrder = view.findViewById(R.id.buttonOrder);

        Log.d("CartFragment - cartId", String.valueOf(Static_Cart.id));

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Log.d("CartFragment - cartId", String.valueOf(Static_Cart.id));
                String recipientName = editTextRecipientName.getText().toString().trim();
                String recipientPhoneNumber = editTextRecipientPhone.getText().toString().trim();
                String recipientEmail = editTextRecipientEmail.getText().toString().trim();
                String detailedAddress = editTextDetailedAddress.getText().toString().trim();
                String ward = editTextWard.getText().toString().trim();
                String district = editTextWard.getText().toString().trim();
                String city = editTextCity.getText().toString().trim();
                String country = "Viet Nam";
                String note = editTextCity.getText().toString().trim();
                String status = "ToPay";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("recipientName", recipientName);
                    jsonBody.put("recipientPhoneNumber", recipientPhoneNumber);
                    jsonBody.put("recipientEmail", recipientEmail);
                    jsonBody.put("detailedAddress", detailedAddress);
                    jsonBody.put("ward", ward);
                    jsonBody.put("district", district);
                    jsonBody.put("city", city);
                    jsonBody.put("country", country);
                    jsonBody.put("note", note);
                    jsonBody.put("status", status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // POST Order
                AndroidNetworking.post("https://eprojectbytranxuantung.herokuapp.com/api/orders?cartId=" +
                        Static_Cart.id + "&shipmentId=1&paymentMethod=OnDelivery")
                        .addJSONObjectBody(jsonBody)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("CartFragment - Ordered", response.toString());
                                    Long orderId = response.getLong("id");
                                    if(orderId != 0) {
                                        // get new CartId
                                        AndroidNetworking.get("https://eprojectbytranxuantung.herokuapp.com/api/carts/getCartInUse?userId=" + Static_User.id)
                                                .setPriority(Priority.MEDIUM).build()
                                                .getAsJSONObject(new JSONObjectRequestListener() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
//                                                      Log.d("62", response.toString());
                                                        try {
                                                            Static_Cart.id = response.getLong("id");
                                                            Log.d("CartFragment - cartId", String.valueOf(Static_Cart.id));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(ANError anError) {
                                                        Log.d("Error", "Error when get cart in use of user");
                                                    }
                                                });
                                    }

                                    // reset cart_item view
                                    onResume();
                                    // reset all edittext
                                    resetEditText();

                                    Toast.makeText(getContext(), "Order succesfully!", Toast.LENGTH_SHORT);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("Order Error", anError.getErrorDetail());
                                Toast.makeText(getContext(), "Order Failed!", Toast.LENGTH_SHORT);
                            }
                        });
            }
        });

        String url = "https://eprojectbytranxuantung.herokuapp.com/api/carts/" + Static_Cart.id;
        cart_itemList = new ArrayList<>();
        AndroidNetworking.get(url).setPriority(Priority.MEDIUM).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray cartItemsJsonArray = response.getJSONArray("cart_Items");
                            Log.d("53", cartItemsJsonArray.toString());
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

        recyclerViewCartItem.setAdapter(adapter);

        return view;
    }
}