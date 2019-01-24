package com.cooldevs.exercisesflexibility.daos;

import com.cooldevs.exercisesflexibility.entities.UniversalItem;
import com.cooldevs.exercisesflexibility.entities.UniversalItemExtraColumn;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UniversalItemExtraColumnDao {
    @Insert(onConflict = REPLACE)
    void save(UniversalItemExtraColumn universalItemExtraColumn);

    @Query("SELECT * FROM UniversalItemExtraColumn WHERE id = :id")
    LiveData<UniversalItemExtraColumn> get(int id);

    @Query("SELECT * FROM UniversalItemExtraColumn WHERE id = :id")
    UniversalItemExtraColumn getSync(int id);

    @Query("SELECT ec.* FROM UniversalItemExtraColumn as ec INNER JOIN UniversalItem as ui ON ui.id=ec.id WHERE ui.parentId = :parentId")
    LiveData<List<UniversalItemExtraColumn>> getList(int parentId);

    @Query("UPDATE UniversalItemExtraColumn SET isShown=1 WHERE id = :id")
    void setShown(int id);
}
