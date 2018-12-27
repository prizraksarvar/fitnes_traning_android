package com.sarvarcorp.fitnestraning.workers;

import com.sarvarcorp.fitnestraning.daos.MobileappConfigDao;
import com.sarvarcorp.fitnestraning.daos.MobileappDao;
import com.sarvarcorp.fitnestraning.daos.UniversalItemDao;
import com.sarvarcorp.fitnestraning.entities.Mobileapp;
import com.sarvarcorp.fitnestraning.entities.MobileappConfig;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;

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
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UniversalItemDao universalItemDao();
    public abstract MobileappDao mobileappDao();
    public abstract MobileappConfigDao mobileappConfigDao();

    /*public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS ImageCache");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ImageCache` (`id` INTEGER NOT NULL, `url` TEXT, `created` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        }
    };*/
}

