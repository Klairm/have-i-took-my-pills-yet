package com.klairm.haveitookmymedsyet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.klairm.haveitookmymedsyet.database.Med;
import com.klairm.haveitookmymedsyet.database.MedDAO;
import com.klairm.haveitookmymedsyet.database.MedDatabase;
import com.klairm.haveitookmymedsyet.databinding.ActivityMainBinding;
import com.klairm.haveitookmymedsyet.recyclerview.MedAdapter;

import java.util.Date;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // TODO: Don't use .allowMainThreadQueries() , instead async for the database
    // TODO: Better UI lmao

    private ActivityMainBinding binding;
    Integer searchLimit = 7;
    MedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Med medication = new Med();


        MedDatabase db = Room.databaseBuilder(getApplicationContext(), MedDatabase.class, "med").allowMainThreadQueries().build();
        MedDAO medDao = db.medDao();


         viewModel = new MedViewModel(medDao);


        MedAdapter adapter = new MedAdapter(new MedAdapter.UserDiff(), this, binding.filterSearch,searchLimit);

        viewModel.medList.observe(this, adapter::submitList);


        binding.medicationList.setAdapter(adapter);
        binding.medicationList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        viewModel.setMedList(binding.filterSearch.getText().toString(),searchLimit);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.searchLimitValues,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.searchLimit.setAdapter(spinnerAdapter);
        binding.searchLimit.setOnItemSelectedListener(this);



        binding.filterSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setMedList(binding.filterSearch.getText().toString(),searchLimit);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.btnAdd.setOnClickListener(v -> {
            if (binding.etMedication.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "QUE HACES PAYASO, PON ALGO ESPABILAO.", Toast.LENGTH_SHORT).show();
            } else {
                medication.medName = binding.etMedication.getText().toString();
                medication.timesTaken = binding.etNumber.getText().length() == 0 ? 1 : Integer.parseInt(binding.etNumber.getText().toString());
                medication.medDate = new Date();
                medDao.insertMed(medication);
            }


        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String searchLimit = parent.getItemAtPosition(position).toString();
            if(searchLimit.equals("All")){
                this.searchLimit = 0;
            }else{
                this.searchLimit = Integer.parseInt(searchLimit);
            }
            viewModel.setMedList(binding.filterSearch.getText().toString(),this.searchLimit);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

