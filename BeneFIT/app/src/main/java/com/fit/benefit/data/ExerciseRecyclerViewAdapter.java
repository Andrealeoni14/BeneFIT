package com.fit.benefit.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit.benefit.R;
import com.fit.benefit.models.Exercise;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ExercisesViewHolder> {

    private Context mContext;
    private ArrayList<Exercise> exerciseList;
    private OnItemClickListener listener;
    private OnExerciseClickListener eListener;

    public interface OnExerciseClickListener {
        void onExerciseClick(Exercise exercise);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnExerciseClickListener listener) {
        eListener = listener;
    }

    public ExerciseRecyclerViewAdapter(Context context, ArrayList<Exercise> exerciseList, OnExerciseClickListener eListener) {
        mContext = context;
        this.exerciseList = exerciseList;
        this.eListener = eListener;
    }

    @NonNull
    @Override
    public ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.exercise_cardview, parent, false);
        //view.setOnClickListener((View.OnClickListener) eListener);
        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesViewHolder holder, int position) {
        Exercise currentItem = exerciseList.get(position);
        //holder.bind(listOfExercises.get(position));
        int id = currentItem.getId();
        String name = currentItem.getName();
        String imageUrl = currentItem.getImg();
        //String description = currentItem.getDescription();
        holder.nameTextView.setText(name);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        if (exerciseList != null) {
            return exerciseList.size();
        }
        return 0;
    }

    public void addData(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public class ExercisesViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImage;
        public TextView nameTextView;

        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.exercise_image);
            nameTextView = itemView.findViewById(R.id.exercise_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                    /*int position = getAdapterPosition();
                    Exercise exercise = exerciseList.get(position);
                    Toast.makeText(mContext, (CharSequence) exercise, Toast.LENGTH_SHORT).show();*/
                }
            });
        }

        /*public void bind(Exercise exercise) {

            nameTextView.setText(exercise.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    eListener.onExerciseClick(exercise);
                }
            });
        }*/
    }
}