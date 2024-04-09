package com.klairm.haveitookmymedsyet.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedDAO {

    @Query("SELECT * FROM Med ORDER BY med_date DESC LIMIT :searchLimit")
    LiveData<List<Med>> getAll(int searchLimit);

    @Query("SELECT * FROM Med WHERE med_name LIKE '%' || :name || '%' ORDER BY med_date DESC LIMIT :searchLimit")
    LiveData<List<Med>> getAllByName(String name,int searchLimit);


    @Query("SELECT count(*) FROM Med;")
    int getMedicationCount();

    @Insert
    void insertMed(Med med);

    @Delete
    void deleteMed(Med med);

    @Query("SELECT max(id) FROM Med")
    int getLastId();


}
