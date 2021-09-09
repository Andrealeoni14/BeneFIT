package com.fit.benefit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fit.benefit.repositories.EPreferencesRepository;
import com.fit.benefit.repositories.PreferencesRepository;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EPreferencesRepository preferencesRepository = new PreferencesRepository();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable(ARTICLE, mArticle);
    }
}
