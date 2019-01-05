package com.cooldevs.exercisesflexibility.workers;

import com.cooldevs.exercisesflexibility.daos.MobileappConfigDao;
import com.cooldevs.exercisesflexibility.daos.MobileappDao;
import com.cooldevs.exercisesflexibility.daos.UniversalItemDao;
import com.cooldevs.exercisesflexibility.entities.Mobileapp;
import com.cooldevs.exercisesflexibility.entities.MobileappConfig;
import com.cooldevs.exercisesflexibility.entities.UniversalItem;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {
                UniversalItem.class,
                Mobileapp.class,
                MobileappConfig.class,
        },
        version = 3
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UniversalItemDao universalItemDao();
    public abstract MobileappDao mobileappDao();
    public abstract MobileappConfigDao mobileappConfigDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS UniversalItem");
            database.execSQL("CREATE TABLE IF NOT EXISTS `UniversalItem` (`id` INTEGER NOT NULL, `parentId` INTEGER NOT NULL, `updatedDate` INTEGER NOT NULL, `viewType` TEXT, `listViewType` TEXT, `name` TEXT, `image` TEXT, `smallImage` TEXT, `description` TEXT, `backgroundColor` TEXT, `listBackgroundColor` TEXT, `timerTime` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS UniversalItem");
            database.execSQL("CREATE TABLE IF NOT EXISTS `UniversalItem` (`id` INTEGER NOT NULL, `parentId` INTEGER NOT NULL, `updatedDate` INTEGER NOT NULL, `viewType` TEXT, `listViewType` TEXT, `name` TEXT, `image` TEXT, `smallImage` TEXT, `description` TEXT, `backgroundColor` TEXT, `listBackgroundColor` TEXT, `timerTime` INTEGER NOT NULL, `isBonus` INTEGER NOT NULL, `isAvailableBonus` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        }
    };
}

