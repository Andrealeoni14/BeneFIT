package com.fit.benefit;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

public class CategoryActivity extends AppCompatActivity {

    // buttons for the categories
    private TableRow category8;
    private TableRow category9;
    private TableRow category10;
    private TableRow category11;
    private TableRow category12;
    private TableRow category13;
    private TableRow category14;
    private TableRow categorySaved; // user saved exercises


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        categorySaved = (TableRow) findViewById(R.id.CategorySaved);
        categorySaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySaved();
            }
        });
        category8 = (TableRow) findViewById(R.id.Category8);
        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category8();
            }
        });
        category9 = (TableRow) findViewById(R.id.Category9);
        category9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category9();
            }
        });
        category10 = (TableRow) findViewById(R.id.Category10);
        category10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category10();
            }
        });
        category11 = (TableRow) findViewById(R.id.Category11);
        category11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category11();
            }
        });
        category12 = (TableRow) findViewById(R.id.Category12);
        category12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category12();
            }
        });
        category13 = (TableRow) findViewById(R.id.Category13);
        category13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category13();
            }
        });
        category14 = (TableRow) findViewById(R.id.Category14);
        category14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category14();
            }
        });
    }

    public void categorySaved() {
        Intent intent = new Intent (this, ExerciseActivity.class);
        intent.putExtra("category", 0);
        startActivity(intent);
    }
    public void category8() {
        Intent intent = new Intent (this, ExerciseActivity.class);
        intent.putExtra("category", 8);
        startActivity(intent);
    }
    public void category9() {
        Intent intent = new Intent (this, ExerciseActivity.class);
        intent.putExtra("category", 9);
        startActivity(intent);
    }
    public void category10() {
        Intent intent = new Intent (this, ExerciseActivity.class);
        intent.putExtra("category", 10);
        startActivity(intent);
    }
    public void category11() {
        Intent intent = new Intent (this, ExerciseActivity.class);
        intent.putExtra("category", 11);
        startActivity(intent);
    }
    public void category12() {
        Intent intent = new Intent (this, ExerciseActivity.class);
        intent.putExtra("category", 12);
        startActivity(intent);
    }
    public void category13() {
        Intent intent = new Intent (this, ExerciseActivity.class);
        intent.putExtra("category", 13);
        startActivity(intent);
    }
    public void category14() {
        Intent intent = new Intent (this, ExerciseActivity.class);
        intent.putExtra("category", 14);
        startActivity(intent);
    }

    public void logoutClick(android.view.View view) {
        LogoutActivity logout = new LogoutActivity();
        logout.logoutFunction(CategoryActivity.this);
    }

    @Override
    public void onBackPressed() {

    }
}