package com.fit.benefit.data;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fit.benefit.R;
import com.fit.benefit.models.Exercise;
import com.fit.benefit.repositories.ExerciseRepository;
import com.fit.benefit.repositories.ResponseCallback;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListFragmentRecyclerView extends Fragment implements ResponseCallback {

    private static final String TAG = "ExerciseListFragRecView";

    private ExerciseRepository exerciseRepository;
    private List<Exercise> exerciseList;
    private ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_first, container, false);

        /*
        Permette al Fragment di personalizzare la barra degli strumenti
        con il file di menu specificato nel metodo onCreateOptionsMenu (Menu, MenuInflater)
         */
        setHasOptionsMenu(true);

        exerciseRepository = new ExerciseRepository(this, requireActivity().getApplication());

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //List<Exercise> exerciseListWithJsonReader = getExercisesWithJsonReader();
        //altre robe strane

        exerciseList = new ArrayList<>();
        exerciseRepository.fetchExercises();

        RecyclerView recyclerView = view.findViewById(R.id.exercises_list);
        exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter(exerciseList, new ExerciseRecyclerViewAdapter.OnExerciseClickListener() {
            @Override
            public void onExerciseClick(Exercise exercise) {

            }
        });
    }


    @Override
    public void onResponse(List<Exercise> exerciseList, long lastUpdate) {
        this.exerciseList.addAll(exerciseList);
        exerciseRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String message) {
        //mettere notifica a utente che si è verificato errore
    }
}
