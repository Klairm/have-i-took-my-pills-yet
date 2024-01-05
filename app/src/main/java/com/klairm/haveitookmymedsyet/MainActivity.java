package com.klairm.haveitookmymedsyet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.klairm.haveitookmymedsyet.database.Med;
import com.klairm.haveitookmymedsyet.database.MedDAO;
import com.klairm.haveitookmymedsyet.database.MedDatabase;
import com.klairm.haveitookmymedsyet.recyclerview.MedAdapter;

import java.util.Date;


public class MainActivity extends AppCompatActivity {
    // TODO: Don't use .allowMainThreadQueries() , instead async for the database
    // TODO: Better UI lmao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submitBtn = findViewById(R.id.button);
        EditText medNameEt = findViewById(R.id.editTextText);
        EditText timesTakenEt = findViewById(R.id.editTextNumber);
        EditText filterEt = findViewById(R.id.fentanilo);
        RecyclerView medicationList = findViewById(R.id.medicationList);

        Med medication = new Med();


        MedDatabase db = Room.databaseBuilder(getApplicationContext(), MedDatabase.class, "med").allowMainThreadQueries().build();
        MedDAO medDao = db.medDao();


        MedViewModel viewModel = new MedViewModel(medDao);


        MedAdapter adapter = new MedAdapter(new MedAdapter.UserDiff(), this, filterEt);

        viewModel.medList.observe(this, list -> adapter.submitList(list));


        medicationList.setAdapter(adapter);
        medicationList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        viewModel.setMedList(filterEt.getText().toString());

        filterEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setMedList(filterEt.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        submitBtn.setOnClickListener(v -> {
            if (medNameEt.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "QUE HACES PAYASO, PON ALGO ESPABILAO.", Toast.LENGTH_SHORT).show();
            } else {
                medication.medName = medNameEt.getText().toString();
                medication.timesTaken = timesTakenEt.getText().length() == 0 ? 1 : Integer.parseInt(timesTakenEt.getText().toString());
                medication.medDate = new Date();
                medDao.insertMed(medication);
            }


        });


    }


}

