// used for Gson
package com.fit.benefit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response implements Parcelable {

    @SerializedName(value = "results")
    private List<Exercise> exerciseList;


    public Response(String message, int i, List<Exercise> exerciseList, boolean b) {
        this.exerciseList = exerciseList;
    }

    // getter e setter
    public List<Exercise> getExerciseList() {
        return exerciseList;
    }
    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.exerciseList);
    }

    public void readFromParcel(Parcel source) {
        this.exerciseList = source.createTypedArrayList(Exercise.CREATOR);
    }

    protected Response(Parcel in) {
        this.exerciseList = in.createTypedArrayList(Exercise.CREATOR);
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel source) {
            return new Response(source);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };
}
