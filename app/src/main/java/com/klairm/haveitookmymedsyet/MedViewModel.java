package com.klairm.haveitookmymedsyet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.klairm.haveitookmymedsyet.database.Med;
import com.klairm.haveitookmymedsyet.database.MedDAO;

import java.util.List;

public class MedViewModel extends ViewModel {
    MedDAO medDao;

    private final MutableLiveData<String> mutacion = new MutableLiveData<>();

    public  LiveData<List<Med>> medList = Transformations.switchMap(mutacion, s -> {
        if(s.length() == 0){
            return medDao.getAll();
        }else{
            return medDao.getAllByName(s);
        }

    });
    public MedViewModel(MedDAO medDao){

        this.medDao = medDao;
    }

    public void setMedList(String medName){
      mutacion.setValue(medName);




    }
}
