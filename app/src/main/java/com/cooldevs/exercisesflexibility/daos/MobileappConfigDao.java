package com.cooldevs.exercisesflexibility.daos;

import com.cooldevs.exercisesflexibility.entities.MobileappConfig;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MobileappConfigDao {
    @Insert(onConflict = REPLACE)
    void save(MobileappConfig mobileappConfig);

    @Query("SELECT * FROM MobileappConfig")
    LiveData<List<MobileappConfig>> getAll();

    @Query("SELECT * FROM MobileappConfig")
    List<MobileappConfig> getAllNotLive();

    @Query("SELECT * FROM MobileappConfig")
    LiveData<List<MobileappConfig>> getList();

    @Query("DELETE FROM MobileappConfig")
    void delete();

    @Query("DELETE FROM MobileappConfig WHERE id not IN (:ids)")
    void deleteNotIds(int[] ids);

    @Query("SELECT (count(*)) as unfresh FROM MobileappConfig WHERE updatedDate<:time")
    int unfreshCount(long time);

    @Query("SELECT count(*) as count FROM MobileappConfig")
    int count();

    @Query("SELECT (count(*)>0) as unfresh FROM MobileappConfig WHERE id = :id AND updatedDate<:time")
    boolean isUnfresh(int id, long time);

    @Query("SELECT (count(*)>0) as exist FROM MobileappConfig WHERE id = :id")
    boolean exist(int id);

    @Query("SELECT max(updatedDate) as updatedDate FROM MobileappConfig")
    int lastListUpdatedDate();

    @Query("SELECT max(updatedDate) as updatedDate FROM MobileappConfig WHERE id = :id")
    int lastItemUpdatedDate(int id);
}
