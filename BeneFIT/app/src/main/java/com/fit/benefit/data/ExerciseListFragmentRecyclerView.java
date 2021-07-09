package com.fit.benefit.data;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.fit.benefit.utils.Constants;
import com.fit.benefit.utils.ResponseCallback;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.getLong;

public class ExerciseListFragmentRecyclerView extends Fragment implements ResponseCallback {

    private static final String TAG = "ExerciseListFragRecView";

    private ExerciseRepository exerciseRepository;
    private List<Exercise> exerciseListWithAPI;
    private ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.exercises, container, false);

        exerciseRepository = new ExerciseRepository(this, requireActivity().getApplication());

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exerciseListWithAPI = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.exercises_list);
        exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter(this.getContext(),
                exerciseListWithAPI, new ExerciseRecyclerViewAdapter.OnExerciseClickListener() {
            @Override
            public void onExerciseClick(Exercise exercise) {
                Toast.makeText(getActivity(), (CharSequence) exercise, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(exerciseRecyclerViewAdapter);
        exerciseRepository.fetchExercises(getLastUpdate());
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onResponse(List<Exercise> exerciseList, long lastUpdate) {
        exerciseListWithAPI.addAll(exerciseList);
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                exerciseRecyclerViewAdapter.notifyDataSetChanged();
                //setLastUpdate (lastUpdate);
            }
        });
    }

    @Override
    public void onFailure(String message) {
        Log.d(TAG, "Web Service call failed: " + message);
        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT).show();
    }

    private long getLastUpdate() {
        return getLong(Constants.LAST_UPDATE, 0);
    }

    /*
    private void setLastUpdate(long lastUpdate) {
    }
     */


}
