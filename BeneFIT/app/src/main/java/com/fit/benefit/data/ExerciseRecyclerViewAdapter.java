package com.fit.benefit.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit.benefit.R;
import com.fit.benefit.models.Exercise;

import java.util.List;

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ExercisesViewHolder> {

    public Context mContext;
    public List<Exercise> exerciseList;
    public OnExerciseClickListener listener;

    public interface OnExerciseClickListener {
        void onExerciseClick (Exercise exercise);
    }

    public ExerciseRecyclerViewAdapter(Context context, List<Exercise> exerciseList, OnExerciseClickListener onExerciseClickListener) {
        mContext = context;
        this.exerciseList = exerciseList;
        listener = onExerciseClickListener;
    }

    @NonNull
    @Override
    public ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.exercise_cardview, parent, false);
        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesViewHolder holder, int position) {
        Exercise currentItem = exerciseList.get(position);
        //holder.bind(listOfExercises.get(position));
        int id = currentItem.getId();
        String name = currentItem.getName();
        String description = currentItem.getDescription();
        holder.nameTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        if (exerciseList != null) {
            return exerciseList.size();
        }
        return 0;
    }

    public void addData(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public class ExercisesViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImage;
        private final TextView nameTextView;

        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.exercise_image);
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