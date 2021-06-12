package com.example.baitaplonandroid_tranxuantung_b17dccn670;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextRetypePassword, editTextFirstname,
            editTextMiddlename, editTextLastname, editTextPhonenumber, editTextEmail;
    private DatePicker datePicker;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUsername = findViewById(R.id.editTextSignUpUsername);
        editTextPassword = findViewById(R.id.editTextSignUpPassword);
        editTextRetypePassword = findViewById(R.id.editTextSignUpRetypePassword);
        editTextFirstname = findViewById(R.id.editTextSignUpFirstName);
        editTextMiddlename = findViewById(R.id.editTextSignUpMiddleName);
        editTextLastname = findViewById(R.id.editTextSignUpLastname);
        editTextPhonenumber = findViewById(R.id.editTextSignUpPhonenumber);
        editTextEmail = findViewById(R.id.editTextSignUpEmail);
        datePicker = findViewById(R.id.datePicker);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String retypePassword = editTextRetypePassword.getText().toString().trim();
                String firstName = editTextFirstname.getText().toString().trim();
                String middleName = editTextMiddlename.getText().toString().trim();
                String lastName = editTextLastname.getText().toString().trim();
                String day = (datePicker.getDayOfMonth() >= 10) ? String.valueOf(datePicker.getDayOfMonth()) : ("0"+datePicker.getDayOfMonth());
                String month = (datePicker.getMonth()+1 < 10) ? ("0"+(datePicker.getMonth()+1)) : String.valueOf(datePicker.getMonth()+1);
                String year = String.valueOf(datePicker.getYear());
                String dob = year+"-"+month+"-"+day;
//                Log.d("dob", dob);
                String phoneNumber = editTextPhonenumber.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("username", username);
                    jsonBody.put("password", password);
                    jsonBody.put("retypePassword", retypePassword);
                    jsonBody.put("firstName", firstName);
                    jsonBody.put("middleName", middleName);
                    jsonBody.put("lastName", lastName);
                    jsonBody.put("dob", dob);
                    jsonBody.put("gender", "");
                    jsonBody.put("phoneNumber", phoneNumber);
                    jsonBody.put("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post("https://eprojectbytranxuantung.herokuapp.com/api/users/addCustomer2")
                        .addJSONObjectBody(jsonBody)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Long userId = response.getLong("id");
                                    if(userId != 0) {
                                        Toast.makeText(SignUpActivity.this, "Register successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getBaseContext(), "Register failed!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getBaseContext(), "Register failed!", Toast.LENGTH_SHORT);
                            }
                        });
            }
        });
    }
}