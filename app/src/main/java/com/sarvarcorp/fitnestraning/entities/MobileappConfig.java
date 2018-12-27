package com.sarvarcorp.fitnestraning.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MobileappConfig {
    @PrimaryKey
    @NonNull
    public int id;
    public long updatedDate;
    public String code;
    public String lang;
    public String value;
}
