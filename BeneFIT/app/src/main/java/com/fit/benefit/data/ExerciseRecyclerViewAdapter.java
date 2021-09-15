// create a binding between app-data set and the exercises in the recyclerView
package com.fit.benefit.data;

import android.content.Context;
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

    private Context mContext;
    private List<Exercise> exerciseList;
    private OnExerciseClickListener listener;

    public interface OnExerciseClickListener {
        void onExerciseClick(Exercise exercise );
    }

    public void setOnExerciseClickListener(OnExerciseClickListener listener) { this.listener = listener; }

    public ExerciseRecyclerViewAdapter(Context context, List<Exercise> exerciseList, OnExerciseClickListener listener) {
        mContext = context;
        this.exerciseList = exerciseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_cardview, parent, false);
        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesViewHolder holder, int position) {
        Exercise currentItem = exerciseList.get(position);
        String name = currentItem.getName();
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

    public class ExercisesViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;

        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exercise_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onExerciseClick(exerciseList.get(position));
                        }
                    }
                }
            });
        }
    }
}