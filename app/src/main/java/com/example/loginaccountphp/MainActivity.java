package com.example.loginaccountphp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.applovin.mediation.MaxError;


import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Retrieve the email and password values from the extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String email = extras.getString("email");
            String password = extras.getString("password");

            // Save the email and password in SharedPreferences
            saveData(email, password);

            // Now, you can use 'email' and 'password' as needed in your MainActivity
            // For example, display them in TextViews or other UI elements
            TextView txtEmail = findViewById(R.id.gmailid); // Replace with your actual TextView ID
            TextView txtPassword = findViewById(R.id.passwordid); // Replace with your actual TextView ID

            txtEmail.setText("Email: " + email);
            txtPassword.setText("Password: " + password);
        }

        // Retrieve and display saved data
        retrieveData();


    }

    private void saveData(String email, String password) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("email", email);
        editor.putString("password", password);

        editor.apply();
    }

    private void retrieveData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String savedEmail = preferences.getString("email", "");
        String savedPassword = preferences.getString("password", "");

        // Use the retrieved email and password as needed
        // For example, display them in TextViews
        TextView txtEmail = findViewById(R.id.gmailid); // Replace with your actual TextView ID
        TextView txtPassword = findViewById(R.id.passwordid); // Replace with your actual TextView ID

        txtEmail.setText("Email: " + savedEmail);
        txtPassword.setText("Saved Password: " + savedPassword);
    }


}
