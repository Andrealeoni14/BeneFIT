package com.fit.benefit.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit.benefit.R;
import com.fit.benefit.models.Exercise;

import java.util.List;

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ExercisesViewHolder> {

    private List<Exercise> listOfExercises;
    private OnExerciseClickListener listener;

    public interface OnExerciseClickListener {
        void onExerciseClick (Exercise exercise);
    }

    public ExerciseRecyclerViewAdapter(List<Exercise> exerciseList, OnExerciseClickListener onExerciseClickListener) {
        listOfExercises = exerciseList;
        listener = onExerciseClickListener;
    }

    @NonNull
    @Override
    public ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_scrolling, parent, false); */
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_cardview, parent, false);
        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesViewHolder holder, int position) {
        holder.bind(listOfExercises.get(position));
    }

    @Override
    public int getItemCount() {
        if (listOfExercises != null) {
            return listOfExercises.size();
        }
        return 0;
    }

    public void addData(List<Exercise> exerciseList) {
        listOfExercises = exerciseList;
    }

    public class ExercisesViewHolder extends RecyclerView.ViewHolder{

        private final TextView nameTextView;

        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exercise_name);
        }

        public void bind(Exercise exercise) {

            nameTextView.setText(exercise.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onExerciseClick(exercise);
                }
            });
        }
    }
}

