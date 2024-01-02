package com.klairm.haveitookmymedsyet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.klairm.haveitookmymedsyet.database.Med;
import com.klairm.haveitookmymedsyet.database.MedDAO;
import com.klairm.haveitookmymedsyet.database.MedDatabase;
import com.klairm.haveitookmymedsyet.recyclerview.MedAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.klairm.haveitookmymedsyet.recyclerview.MedAdapter;

public class MainActivity extends AppCompatActivity {
    // TODO: Don't use .allowMainThreadQueries() , instead async for the database
    // TODO: Allow filter by name, using fragments maybe?
    // TODO: Better UI lmao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submitBtn = findViewById(R.id.button);
        EditText medNameEt = findViewById(R.id.editTextText);
        EditText timesTakenEt = findViewById(R.id.editTextNumber);
        RecyclerView medicationList = findViewById(R.id.medicationList);

        Med medication = new Med();


        MedDatabase db = Room.databaseBuilder(getApplicationContext(), MedDatabase.class, "med").allowMainThreadQueries().build();
        MedDAO medDao = db.medDao();


        MedViewModel viewModel = new MedViewModel(medDao);
        MedAdapter adapter = new MedAdapter(new MedAdapter.UserDiff(), this);
        viewModel.medList.observe(this, list -> adapter.submitList(list));


        medicationList.setAdapter(adapter);
        medicationList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        submitBtn.setOnClickListener(v -> {

            medication.medName = medNameEt.getText().toString();
            medication.timesTaken = timesTakenEt.getText().length() == 0 ? 1 : Integer.parseInt(timesTakenEt.getText().toString());
            medication.medDate = new Date();
            medDao.insertMed(medication);


        });


    }


}

