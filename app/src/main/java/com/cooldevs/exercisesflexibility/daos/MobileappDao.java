package com.cooldevs.exercisesflexibility.daos;

import com.cooldevs.exercisesflexibility.entities.Mobileapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MobileappDao {
    @Insert(onConflict = REPLACE)
    void save(Mobileapp mobileapp);

    @Query("SELECT * FROM Mobileapp")
    LiveData<Mobileapp> get();

    @Query("DELETE FROM Mobileapp")
    void delete();

    @Query("SELECT (count(*)>0) as unfresh FROM Mobileapp WHERE updatedDate<:time")
    boolean isUnfresh(long time);

    @Query("SELECT max(updatedDate) as updatedDate FROM Mobileapp")
    int lastUpdatedDate();
}
