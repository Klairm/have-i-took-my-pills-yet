package com.klairm.haveitookmymedsyet.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Med.class}, version = 1)

@TypeConverters({Converters.class})
public abstract class MedDatabase extends RoomDatabase {
    public abstract MedDAO medDao();
}
