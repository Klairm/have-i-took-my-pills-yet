package com.klairm.haveitookmymedsyet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.klairm.haveitookmymedsyet.database.Med;
import com.klairm.haveitookmymedsyet.database.MedDAO;

import java.util.List;

public class MedViewModel extends ViewModel {
    public final LiveData<List<Med>> medList;
    public MedViewModel(MedDAO medDao){
        medList = medDao.getAll();
    }
}
