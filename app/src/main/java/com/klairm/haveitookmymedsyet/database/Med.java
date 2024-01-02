package com.klairm.haveitookmymedsyet.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Med {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "med_name")
    public String medName;

    @ColumnInfo( name = "times_taken")
    public int timesTaken;

    @ColumnInfo(name = "med_date")
    public Date medDate;

    public int getId() {
        return id;
    }
}
