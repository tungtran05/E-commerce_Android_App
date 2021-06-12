package com.example.baitaplonandroid_tranxuantung_b17dccn670.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.R;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter.ItemAdapter;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Item;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.ItemImage;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.sortOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class BagsFragment extends Fragment {

    private Spinner spinner;
    private RecyclerView recyclerViewItem;
    private ItemAdapter adapter;
    private static List<Item> itemList;
    private Long userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bags, container, false);

        spinner  = view.findViewById(R.id.spinnerBags);
        ArrayAdapter<sortOptions> adapterSpinner = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, sortOptions.values());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);

        recyclerViewItem = view.findViewById(R.id.recyclerViewBags);
        adapter = new ItemAdapter(getActivity());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewItem.setLayoutManager(layoutManager);

        getAllTshirts();

        //
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){

                }
                else if(position == 1){
                    itemList.clear();
                    sortByLowestToHighestPrice();
                }
                else if(position == 2){
                    itemList.clear();
                    sortByHighestToLowestPrice();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void getAllTshirts() {
        itemList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://eprojectbytranxuantung.herokuapp.com/api/items/getByProductGroupId?productGroupId=6";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++) {
                            try {
                                JSONObject itemJSON = response.getJSONObject(i);
                                long id = itemJSON.getLong("id");
                                String name = itemJSON.getString("name");
                                String color = itemJSON.getString("color");
                                String size = itemJSON.getString("size");
                                int quantity = Integer.parseInt(itemJSON.getString("quantity"));
                                float sellPrice = Float.parseFloat(itemJSON.getString("sellPrice"));
                                float salePrice = Float.parseFloat(itemJSON.getString("salePrice"));
                                boolean active = itemJSON.getBoolean("active");

                                List<ItemImage> itemImageList = new ArrayList<>();
                                JSONArray itemImagesJSON = (JSONArray) itemJSON.get("itemImages");
                                for(int j=0; j<itemImagesJSON.length(); j++) {
                                    JSONObject itemImageJSON = itemImagesJSON.getJSONObject(j);
                                    long itemImageId = itemImageJSON.getLong("id");
                                    String url = itemImageJSON.getString("url");
                                    ItemImage itemImage = new ItemImage(itemImageId, url);
                                    Log.d("84", itemImage.toString());
                                    itemImageList.add(itemImage);
                                }

                                Item item = new Item(id, name, color, size, sellPrice, salePrice, quantity, active, null, null, itemImageList);
                                Log.d("item", item.toString());
                                itemList.add(item);
                                Log.d("size of list", String.valueOf(itemList.size()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.d("last size of list", String.valueOf(itemList.size()));
                        adapter.setData(itemList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

        recyclerViewItem.setAdapter(adapter);
    }

    private void sortByHighestToLowestPrice() {
        itemList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://eprojectbytranxuantung.herokuapp.com/api/items/getByProductGroupId?productGroupId=6";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++) {
                            try {
                                JSONObject itemJSON = response.getJSONObject(i);
                                long id = itemJSON.getLong("id");
                                String name = itemJSON.getString("name");
                                String color = itemJSON.getString("color");
                                String size = itemJSON.getString("size");
                                int quantity = Integer.parseInt(itemJSON.getString("quantity"));
                                float sellPrice = Float.parseFloat(itemJSON.getString("sellPrice"));
                                float salePrice = Float.parseFloat(itemJSON.getString("salePrice"));
                                boolean active = itemJSON.getBoolean("active");

                                List<ItemImage> itemImageList = new ArrayList<>();
                                JSONArray itemImagesJSON = (JSONArray) itemJSON.get("itemImages");
                                for(int j=0; j<itemImagesJSON.length(); j++) {
                                    JSONObject itemImageJSON = itemImagesJSON.getJSONObject(j);
                                    long itemImageId = itemImageJSON.getLong("id");
                                    String url = itemImageJSON.getString("url");
                                    ItemImage itemImage = new ItemImage(itemImageId, url);
                                    Log.d("84", itemImage.toString());
                                    itemImageList.add(itemImage);
                                }

                                Item item = new Item(id, name, color, size, sellPrice, salePrice, quantity, active, null, null, itemImageList);
                                Log.d("item", item.toString());
                                itemList.add(item);
                                Log.d("size of list", String.valueOf(itemList.size()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.d("last size of list", String.valueOf(itemList.size()));
                        itemList.sort(new Comparator<Item>() {
                            @Override
                            public int compare(Item o1, Item o2) {
                                return o1.getSalePrice() < o2.getSalePrice() ? -1 : 1;
                            }
                        });
                        adapter.setData(itemList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

        recyclerViewItem.setAdapter(adapter);
    }

    private void sortByLowestToHighestPrice() {
        itemList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://eprojectbytranxuantung.herokuapp.com/api/items/getByProductGroupId?productGroupId=6";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++) {
                            try {
                                JSONObject itemJSON = response.getJSONObject(i);
                                long id = itemJSON.getLong("id");
                                String name = itemJSON.getString("name");
                                String color = itemJSON.getString("color");
                                String size = itemJSON.getString("size");
                                int quantity = Integer.parseInt(itemJSON.getString("quantity"));
                                float sellPrice = Float.parseFloat(itemJSON.getString("sellPrice"));
                                float salePrice = Float.parseFloat(itemJSON.getString("salePrice"));
                                boolean active = itemJSON.getBoolean("active");

                                List<ItemImage> itemImageList = new ArrayList<>();
                                JSONArray itemImagesJSON = (JSONArray) itemJSON.get("itemImages");
                                for(int j=0; j<itemImagesJSON.length(); j++) {
                                    JSONObject itemImageJSON = itemImagesJSON.getJSONObject(j);
                                    long itemImageId = itemImageJSON.getLong("id");
                                    String url = itemImageJSON.getString("url");
                                    ItemImage itemImage = new ItemImage(itemImageId, url);
                                    Log.d("84", itemImage.toString());
                                    itemImageList.add(itemImage);
                                }

                                Item item = new Item(id, name, color, size, sellPrice, salePrice, quantity, active, null, null, itemImageList);
                                Log.d("item", item.toString());
                                itemList.add(item);
                                Log.d("size of list", String.valueOf(itemList.size()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.d("last size of list", String.valueOf(itemList.size()));
                        itemList.sort(new Comparator<Item>() {
                            @Override
                            public int compare(Item o1, Item o2) {
                                return o1.getSalePrice() > o2.getSalePrice() ? -1 : 1;
                            }
                        });
                        adapter.setData(itemList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

        recyclerViewItem.setAdapter(adapter);
    }
}