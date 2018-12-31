package com.sarvarcorp.fitnestraning.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UniversalItem {
    @PrimaryKey
    @NonNull
    public int id;
    public int parentId;
    public long updatedDate;
    public String viewType;
    public String listViewType;
    public String name;
    public String image;
    public String smallImage;
    public String description;
    public String backgroundColor;
    public String listBackgroundColor;
    public int timerTime;
}
