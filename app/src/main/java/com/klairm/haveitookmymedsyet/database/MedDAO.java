package com.klairm.haveitookmymedsyet.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface MedDAO {

    @Query("SELECT * FROM Med ORDER BY med_date DESC")
    LiveData<List<Med>> getAll();

    @Query("SELECT * FROM Med WHERE med_name LIKE '%' || :name || '%' ORDER BY med_date DESC" )
    LiveData<List<Med>> getAllByName(String name);



    @Query("DELETE FROM Med;")
    void panicDeleteAll();

    @Insert
    void insertMed(Med med);

    @Delete
    void deleteMed(Med med);


}
