// activity for viewing the workout details
package com.fit.benefit;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fit.benefit.ExerciseActivity.EXTRA_CAT;
import static com.fit.benefit.ExerciseActivity.EXTRA_DESC;
import static com.fit.benefit.ExerciseActivity.EXTRA_IMAGE;
import static com.fit.benefit.ExerciseActivity.EXTRA_INDEX;
import static com.fit.benefit.ExerciseActivity.EXTRA_NAME;
import static com.fit.benefit.LoginActivity.fCat;
import static com.fit.benefit.LoginActivity.favorites;
import static com.fit.benefit.LoginActivity.findCatIndex;

public class WorkoutActivity extends AppCompatActivity {

    //private TextView mTextViewResult;
    //private RequestQueue mQueue;
    private int category;
    private int index;

    private int i = 0;
    private boolean favorite = false;
    private int catIndex;
    private Button favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mTextViewResult = findViewById(R.id.text_view_result);
        //mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //int workout = intent.getIntExtra("workout", 0);
        String imageUrl = extras.getString(EXTRA_IMAGE);
        String name = extras.getString(EXTRA_NAME);
        String description = extras.getString(EXTRA_DESC);
        category = extras.getInt(EXTRA_CAT);
        index = extras.getInt(EXTRA_INDEX);

        ImageView im = findViewById(R.id.exe_image);
        TextView textName = findViewById(R.id.exe_name);
        TextView textDescription = findViewById(R.id.exe_description);

        // checks if the workout is already in favourites
        catIndex = category - fCat;
        while((i < favorites[catIndex].size())&&(!favorite)) {
            if(favorites[catIndex].get(i)==index) {
                favorite=true;
            }
            else {
                i++;
            }
        }
        favoriteButton = findViewById(R.id.button);
        if(favorite) {
            favoriteButton.setText(R.string.remSave);
        }

        textName.setText(name);
        textDescription.setText(description);
        if(imageUrl != null) {
            Picasso.with(this).load(imageUrl).fit().centerInside().into(im);
        }
        else {
            Picasso.with(this).load(imageUrl).fit().placeholder(R.drawable.img_default).into(im);
        }
    }


    // adds or remove the workout to the saved ones on firestore
    public void favorite(android.view.View View) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> update = new HashMap<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference collRef = database.collection(user.getUid());
        DocumentReference currentCat = collRef.document(Integer.toString(category));
        if (!favorite) {
            favorites[catIndex].add(index);
            update.put(Integer.toString(index), index);
            currentCat.set(update, SetOptions.merge());
            collRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                favorites = true;
                                changeSize(collRef, task, true);
                                favoriteButton.setText(R.string.remSave);
                            }
                        }
                    });

        }
        else {
            favorites[catIndex].remove(i);
            update.put(Integer.toString(index), FieldValue.delete());
            currentCat.update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    favorites = false;
                    favoriteButton.setText(R.string.save);
                }
            });
            collRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                changeSize(collRef, task, false);
                            }
                        }
                    });
        }
    }


    // modify the size field on firestore (the variable counts how many saved workouts there are)
    public void changeSize(CollectionReference collRef, Task<QuerySnapshot> task, boolean add) {
        QuerySnapshot collection = task.getResult();
        List<DocumentSnapshot> categories = collection.getDocuments();
        int catIndex = findCatIndex(categories, category - fCat);
        long size = (long) categories.get(catIndex).get("Size");
        if(add) {
            size++;
        }
        else {
            size--;
        }
        Map<String, Integer> nSize = new HashMap<>();
        nSize.put("Size", (int) size);
        collRef.document(Integer.toString(category))
                .set(nSize, SetOptions.merge());
    }


    public void logoutClick(android.view.View view) {
        LogoutActivity logout = new LogoutActivity();
        logout.logoutFunction(WorkoutActivity.this);
    }
}
