// display the buttons to login or register in the app
package com.fit.benefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button register;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        home = (Button) findViewById(R.id.home);
        home.setVisibility(View.INVISIBLE);

        /* used for guest access
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });*/
    }

    public void login() {
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void register() {
        Intent intent = new Intent (this, RegisterActivity.class);
        startActivity(intent);
    }

    /* used for guest access
    public void home() {
        Intent intent = new Intent (this, CategoryActivity.class);
        startActivity(intent);
    }*/

    @Override
    public void onBackPressed() {
    }
}