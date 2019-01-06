package com.cooldevs.exercisesflexibility.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UniversalItemExtraColumn {
    @PrimaryKey
    @NonNull
    public int id;
    public boolean isAvailableBonus;
    public boolean isShown;
}
