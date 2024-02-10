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


    private final MutableLiveData<String> medNameLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> searchLimitLiveData = new MutableLiveData<>();

    public LiveData<List<Med>> medList = Transformations.switchMap(medNameLiveData, s -> {
        Integer searchLimit = searchLimitLiveData.getValue();
        if(searchLimit == null  || searchLimit == 0  ){
            searchLimit = medDao.getMedicationCount();
        }
        if (s.length() == 0) {
            return medDao.getAll(searchLimit);
        } else {
            return medDao.getAllByName(s,searchLimit);
        }

    });

    public MedViewModel(MedDAO medDao) {

        this.medDao = medDao;

    }

    public void setMedList(String medName,Integer searchLimit) {
        searchLimitLiveData.setValue(searchLimit);
        medNameLiveData.setValue(medName);



    }



}
