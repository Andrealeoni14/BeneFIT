// data entity, representation of database table
package com.fit.benefit.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercises")

public class Exercise implements Parcelable {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "img")
    private String img;
    @ColumnInfo(name = "category")
    private int category;
    @ColumnInfo(name = "index")
    private int index;

    public Exercise(int id, String name, String description, String img, int category, int index){
        this.id = id;
        this.name = name;
        this.description =  description;
        this.img = img;
        this.category = category;
        this.index = index;
    }

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
    public int getCategory() { return this.category; }
    public void setCategory(int category) { this.category = category; }
    public int getIndex() { return this.index; }
    public void setIndex(int index) { this.index = index; }

    // parcelable
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.img);
        dest.writeString(Integer.toString(this.category));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Exercise(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.img = in.readString();
        this.category = Integer.parseInt(in.readString());
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
