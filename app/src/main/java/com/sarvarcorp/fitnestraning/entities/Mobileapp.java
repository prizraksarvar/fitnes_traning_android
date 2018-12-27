package com.sarvarcorp.fitnestraning.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Mobileapp {
    @PrimaryKey
    @NonNull
    public int id;
    public long updatedDate;
    public String viewType;
    public String name;
    public String image;
    public String description;
    public String backgroundColor;
}
