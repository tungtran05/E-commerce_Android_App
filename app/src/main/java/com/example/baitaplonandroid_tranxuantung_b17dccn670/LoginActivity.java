package com.example.baitaplonandroid_tranxuantung_b17dccn670;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_Fullname;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_User;


import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextData, editTextPassword;
    private Button buttonLogin, buttonSignUp;
    public boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextData = findViewById(R.id.editTextLoginData);
        editTextPassword = findViewById(R.id.editTextLoginPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "Click");
                String data = editTextData.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                login(data, password);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        Log.d("login", "login");

    }

    private void login(String data, String password) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("data", data);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://eprojectbytranxuantung.herokuapp.com/api/users/checkLogin";

        AndroidNetworking.post(url).addJSONObjectBody(jsonObject).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("user", response.toString());
                        try {
                            Log.d("userId", String.valueOf(response.getLong("id")));
                            Long userId = response.getLong("id");
                            JSONObject fullnameJson = response.getJSONObject("fullname");
                            Long fullnameId = fullnameJson.getLong("id");
                            if(userId == 0){
                                check = false;
                                Toast.makeText(LoginActivity.this, "Username or password wrong!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                check = true;
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                // truyền userId sang MainActivity
//                                intent.putExtra("userId", userId);
//                                intent.putExtra("fullnameId", fullnameId);

                                // gán userId vào User.id (static)
                                Static_User.id = userId;
                                // gán fullnameId vào Fullname.id (static)
                                Static_Fullname.id = fullnameId;
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", anError.getErrorDetail());
                    }
                });


        Log.d("check", String.valueOf(check));
    }
}