package com.example.loginaccountphp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText ed_email, ed_password;

    String str_email, str_password;
    String url = "https://allbin.site/hrtsoft/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);

        // Check the login state
        if (isUserLoggedIn()) {
            // User is already logged in, navigate to ProfileActivity
            navigateToProfile();
        }
    }

    // Method to check if the user is logged in (e.g., based on a saved token or flag)
    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    // Method to navigate to the ProfileActivity
    private void navigateToProfile() {
        Intent profileIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(profileIntent);
        finish(); // Optional: Finish LoginActivity to prevent going back to it
    }

    public void Login(View view) {
        if (ed_email.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (ed_password.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();

            str_email = ed_email.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("logged in successfully")) {
                        // Clear input fields
                        ed_email.setText("");
                        ed_password.setText("");

                        // Save the login state
                        saveLoginState(true);

                        // Create an Intent to start the MainActivity
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);

                        // Pass the email and password as extras to the MainActivity
                        mainIntent.putExtra("email", str_email);
                        mainIntent.putExtra("password", str_password);

                        // Start the MainActivity
                        startActivity(mainIntent);

                        // Show a success toast message
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", str_email);
                    params.put("password", str_password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(request);
        }
    }

    // Method to save the login state
    private void saveLoginState(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    public void moveToRegistration(View view) {
        startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
        finish();
    }
}
