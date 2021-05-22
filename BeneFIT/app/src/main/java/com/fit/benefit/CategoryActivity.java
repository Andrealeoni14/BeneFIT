package com.fit.benefit;

import android.content.Intent;
import android.os.Bundle;

import com.fit.benefit.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {

    // buttons for the categories
    private Button category8;
    private Button category9;
    private Button category10;
    private Button category11;
    private Button category12;
    private Button category13;
    private Button category14;
    private Button categorySaved; // user saved exercises


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        category8 = (Button) findViewById(R.id.bCaategory8);
        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category8();
            }
        });
        category9 = (Button) findViewById(R.id.bCaategory9);
        category9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category9();
            }
        });
    }

    public void category8() {
        Intent intent = new Intent (this, HomeActivity.class);
        intent.putExtra("category", 8);
        startActivity(intent);
    }
    public void category9() {
        Intent intent = new Intent (this, HomeActivity.class);
        intent.putExtra("category", 9);
        startActivity(intent);
    }
}