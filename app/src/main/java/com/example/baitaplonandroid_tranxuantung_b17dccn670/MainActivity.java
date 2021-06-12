package com.example.baitaplonandroid_tranxuantung_b17dccn670;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.fragment.NavigationFragmentAdapter;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_Cart;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Item;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.ItemImage;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_Fullname;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView navigationView;
    private NavigationFragmentAdapter adapter;

    private List<Item> tshirtList;
    private Long userId;
    private Long cartId;
    private Long fullnameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get userId từ LoginActivity
//        userId = getIntent().getLongExtra("userId", 0);
//        Log.d("MainActivity - userId", String.valueOf(userId));
//        fullnameId = getIntent().getLongExtra("fullnameId", 0);
//        Log.d("Main - fullnameId", String.valueOf(fullnameId));

//        // gán userId vào User.id (static)
//        Static_User.id = userId;
//        // gán fullnameId vào Fullname.id (static)
//        Static_Fullname.id = fullnameId;

        // get cart in use of user
        AndroidNetworking.get("https://eprojectbytranxuantung.herokuapp.com/api/carts/getCartInUse?userId=" + Static_User.id)
                .setPriority(Priority.MEDIUM).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d("62", response.toString());
                        try {
                            Static_Cart.id = response.getLong("id");
                            Log.d("MainActivity - cartId", String.valueOf(Static_Cart.id));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error", "Error when get cart in use of user");
                    }
                });

        viewPager = findViewById(R.id.viewPager);
        navigationView = findViewById(R.id.bottom_navigation_view);
        adapter = new NavigationFragmentAdapter(getSupportFragmentManager(),
                NavigationFragmentAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        navigationView.getMenu().findItem(R.id.mTshirts).setChecked(true);
                        break;
                    }
                    case 1: {
                        navigationView.getMenu().findItem(R.id.mPants).setChecked(true);
                        break;
                    }
                    case 2: {
                        navigationView.getMenu().findItem(R.id.mBags).setChecked(true);
                        break;
                    }
                    case 3: {
                        navigationView.getMenu().findItem(R.id.mCart).setChecked(true);
                        break;
                    }
                    case 4: {
                        navigationView.getMenu().findItem(R.id.mUser).setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mTshirts: {
                    viewPager.setCurrentItem(0);
                    break;
                }
                case R.id.mPants: {
                    viewPager.setCurrentItem(1);
                    break;
                }
                case R.id.mBags: {
                    viewPager.setCurrentItem(2);
                    break;
                }
                case R.id.mCart: {
                    viewPager.setCurrentItem(3);
                    break;
                }
                case R.id.mUser: {
                    viewPager.setCurrentItem(4);
                    break;
                }
            }
            return true;
        });



    }

//    private List<Item> getItemsFromAPI(String url) {
//        List<Item> itemList = new ArrayList<>();
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        for(int i=0; i<response.length(); i++) {
//                            try {
//                                JSONObject itemJSON = response.getJSONObject(i);
//                                long id = itemJSON.getLong("id");
//                                String name = itemJSON.getString("name");
//                                String color = itemJSON.getString("color");
//                                String size = itemJSON.getString("size");
//                                int quantity = Integer.parseInt(itemJSON.getString("quantity"));
//                                float sellPrice = Float.parseFloat(itemJSON.getString("sellPrice"));
//                                float salePrice = Float.parseFloat(itemJSON.getString("salePrice"));
//                                boolean active = itemJSON.getBoolean("active");
//
//                                List<ItemImage> itemImageList = new ArrayList<>();
//                                JSONArray itemImagesJSON = (JSONArray) itemJSON.get("itemImages");
//                                for(int j=0; j<itemImagesJSON.length(); j++) {
//                                    JSONObject itemImageJSON = itemImagesJSON.getJSONObject(j);
//                                    long itemImageId = itemImageJSON.getLong("id");
//                                    String url = itemImageJSON.getString("url");
//                                    ItemImage itemImage = new ItemImage(itemImageId, url);
//                                    Log.d("135", itemImage.toString());
//                                    itemImageList.add(itemImage);
//                                }
//
//
//                                Item item = new Item(id, name, color, size, sellPrice, salePrice, quantity, active, null, null, itemImageList);
////                                Log.d("141", item.toString());
//                                Toast.makeText(MainActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
//                                itemList.add(item);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
//                    }
//                });
//        requestQueue.add(jsonArrayRequest);
//
//        return itemList;
//    }
}