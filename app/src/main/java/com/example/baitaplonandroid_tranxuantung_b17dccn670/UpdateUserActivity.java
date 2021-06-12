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
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.fragment.UserFragment;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_Fullname;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_User;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateUserActivity extends AppCompatActivity {

    private EditText editTextUpdateUserFirstname, editTextUpdateUserMiddlename, editTextUpdateUserLastname,
            editTextUpdateUserPhonenumber, editTextUpdateUserEmail, editTextUpdateUserPassword,
            editTextUpdateUserRetypePassword;
    private Button buttonUpdateUserUpdate, buttonUpdateUserCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        editTextUpdateUserFirstname = findViewById(R.id.editTextUpdateUserFirstname);
        editTextUpdateUserMiddlename = findViewById(R.id.editTextUpdateUserMiddlename);
        editTextUpdateUserLastname = findViewById(R.id.editTextUpdateUserLastname);
        editTextUpdateUserPhonenumber = findViewById(R.id.editTextUpdateUserPhonenumber);
        editTextUpdateUserEmail = findViewById(R.id.editTextUpdateUserEmail);
        buttonUpdateUserUpdate = findViewById(R.id.buttonUpdateUserUpdate);
        buttonUpdateUserCancel = findViewById(R.id.buttonUpdateUserCancel);

        getUserInformation();

        buttonUpdateUserUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update Fullname
                String firstName = editTextUpdateUserFirstname.getText().toString().trim();
                String middleName = editTextUpdateUserMiddlename.getText().toString().trim();
                String lastName = editTextUpdateUserLastname.getText().toString().trim();
                JSONObject fullnameJson = new JSONObject();
                try {
                    fullnameJson.put("firstName", firstName);
                    fullnameJson.put("middleName", middleName);
                    fullnameJson.put("lastName", lastName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.put("https://eprojectbytranxuantung.herokuapp.com/api/fullnames/" + Static_Fullname.id)
                        .setPriority(Priority.HIGH).addJSONObjectBody(fullnameJson).build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("update fullname", response.toString());
                                Toast.makeText(getBaseContext(), "Update fullname successfully!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });

                // Update User
                String phoneNumber = editTextUpdateUserPhonenumber.getText().toString().trim();
                String email = editTextUpdateUserEmail.getText().toString().trim();
                JSONObject userJson = new JSONObject();
                try {
                    userJson.put("phoneNumber", phoneNumber);
                    userJson.put("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.put("https://eprojectbytranxuantung.herokuapp.com/api/users/" + Static_User.id)
                        .setPriority(Priority.HIGH).addJSONObjectBody(userJson).build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("update user", response.toString());
                                Toast.makeText(getBaseContext(), "Update user successfully!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        });

        buttonUpdateUserCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getUserInformation() {
        String url = "https://eprojectbytranxuantung.herokuapp.com/api/users/" + Static_User.id;
        AndroidNetworking.get(url).setPriority(Priority.HIGH).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String phoneNumber = response.getString("phoneNumber");
                            String email = response.getString("email");
                            JSONObject fullnameJson = response.getJSONObject("fullname");
                            fullnameJson = response.getJSONObject("fullname");
                            String firstName = fullnameJson.getString("firstName");
                            String middleName = fullnameJson.getString("middleName");
                            String lastName = fullnameJson.getString("lastName");
                            editTextUpdateUserFirstname.setText(firstName);
                            editTextUpdateUserMiddlename.setText(middleName);
                            editTextUpdateUserLastname.setText(lastName);
                            editTextUpdateUserPhonenumber.setText(phoneNumber);
                            editTextUpdateUserEmail.setText(email);
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