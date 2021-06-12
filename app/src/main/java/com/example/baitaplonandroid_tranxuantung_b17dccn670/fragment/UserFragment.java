package com.example.baitaplonandroid_tranxuantung_b17dccn670.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.LoginActivity;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.OrdersHistoryActivity;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.R;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.UpdateUserActivity;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_User;

import org.json.JSONException;
import org.json.JSONObject;


public class UserFragment extends Fragment {

    private TextView textViewHelloUser;
    private Button buttonOrderHistory, buttonUpdateUser, buttonLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        textViewHelloUser = view.findViewById(R.id.textViewHelloUser);
        buttonOrderHistory = view.findViewById(R.id.buttonOrderHistory);
        buttonUpdateUser = view.findViewById(R.id.buttonUpdateUser);
        buttonLogout = view.findViewById(R.id.buttonLogout);

        getUserInformation();

        buttonOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrdersHistoryActivity.class);
                startActivity(intent);
            }
        });

        buttonUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateUserActivity.class);
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getUserInformation() {
        Log.d("65", String.valueOf(Static_User.id));
        String url = "https://eprojectbytranxuantung.herokuapp.com/api/users/" + Static_User.id;
        AndroidNetworking.get(url).setPriority(Priority.HIGH).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject fullnameJson = null;
                        try {
                            fullnameJson = response.getJSONObject("fullname");
                            String firstName = fullnameJson.getString("firstName");
                            String middleName = fullnameJson.getString("middleName");
                            String lastName = fullnameJson.getString("lastName");
                            String fullname = firstName + " " + middleName + " " + lastName;
                            textViewHelloUser.setText("Hello, " + fullname);
                            Log.d("78", fullname);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}