package com.fit.benefit.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercises")

public class Exercise implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int idRoom;

    private int id;
    private String name;
    private String description;

    public Exercise(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description =  description;
    }
    

    //getters e setters
    public int getIdRoom() { return idRoom; }
    public void setIdRoom(int idRoom) { this.idRoom = idRoom; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }


    //parcelable
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    protected Exercise(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
}
