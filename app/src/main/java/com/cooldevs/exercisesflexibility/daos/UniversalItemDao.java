package com.cooldevs.exercisesflexibility.daos;

import com.cooldevs.exercisesflexibility.entities.UniversalItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UniversalItemDao {
    @Insert(onConflict = REPLACE)
    void save(UniversalItem universalItem);

    @Query("SELECT * FROM UniversalItem WHERE id = :id")
    LiveData<UniversalItem> get(int id);

    @Query("SELECT * FROM UniversalItem WHERE parentId = :parentId AND (isBonus<>1 OR isAvailableBonus=1)")
    LiveData<List<UniversalItem>> getList(int parentId);

    @Query("DELETE FROM UniversalItem WHERE parentId = :parentId")
    void delete(int parentId);

    @Query("DELETE FROM UniversalItem WHERE parentId = :parentId AND id not IN (:ids)")
    void deleteNotIds(int parentId, int[] ids);

    @Query("SELECT (count(*)) as unfresh FROM UniversalItem WHERE parentId = :parentId AND updatedDate<:time")
    int unfreshCount(int parentId, long time);

    @Query("SELECT count(*) as count FROM UniversalItem WHERE parentId = :parentId")
    int count(int parentId);

    @Query("SELECT (count(*)>0) as unfresh FROM UniversalItem WHERE id = :id AND updatedDate<:time")
    boolean isUnfresh(int id, long time);

    @Query("SELECT (count(*)>0) as exist FROM UniversalItem WHERE id = :id")
    boolean exist(int id);

    @Query("SELECT max(updatedDate) as updatedDate FROM UniversalItem WHERE parentId = :parentId")
    int lastListUpdatedDate(int parentId);

    @Query("SELECT max(updatedDate) as updatedDate FROM UniversalItem WHERE id = :id")
    int lastItemUpdatedDate(int id);

    @Query("SELECT image FROM UniversalItem WHERE image <> ''")
    LiveData<String> getFirstImage();

    @Query("UPDATE UniversalItem SET isAvailableBonus=1 WHERE parentId = :parentId")
    void setBonusAvailable(int parentId);
}
