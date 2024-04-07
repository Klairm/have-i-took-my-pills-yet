package com.klairm.haveitookmymedsyet.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.klairm.haveitookmymedsyet.MedViewModel;
import com.klairm.haveitookmymedsyet.R;
import com.klairm.haveitookmymedsyet.database.Med;
import com.klairm.haveitookmymedsyet.database.MedDAO;
import com.klairm.haveitookmymedsyet.database.MedDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MedAdapter extends ListAdapter<Med, MedAdapter.ViewHolder> {
    LifecycleOwner lifecycleOwner;
    EditText filterEt;
    Integer searchLimit;

    public MedAdapter(@NonNull DiffUtil.ItemCallback<Med> diffCallback, LifecycleOwner lifecycleOwner, EditText filterEt,Integer searchLimit) {
        super(diffCallback);
        this.lifecycleOwner = lifecycleOwner;
        this.filterEt = filterEt;
        this.searchLimit = searchLimit;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedAdapter.ViewHolder holder, final int position) {


        holder.getMedicationName().setText(getItem(position).medName);
        holder.getDateTaken().setText(formatDateText(getItem(position).medDate));
        holder.getTimesTaken().setText(String.valueOf(getItem(position).timesTaken));
        holder.getDeleteButton().setOnClickListener(v -> {
            MedDatabase db = Room.databaseBuilder(holder.deleteBtn.getContext(), MedDatabase.class, "med").allowMainThreadQueries().build();
            MedDAO medDao = db.medDao();
            MedViewModel viewModel = new MedViewModel(medDao);
            viewModel.medList.observe(this.lifecycleOwner, this::submitList);
            medDao.deleteMed(getItem(position));
            viewModel.setMedList(filterEt.getText().toString(),searchLimit);

        });
        holder.getReuseBtn().setOnClickListener(v -> {
            // TODO: There should be a favorites table in the DB where you can have favorite medication and add that instead of click from the same list
            MedDatabase db = Room.databaseBuilder(holder.reuseBtn.getContext(), MedDatabase.class, "med").allowMainThreadQueries().build();
            MedDAO medDao = db.medDao();
            MedViewModel viewModel = new MedViewModel(medDao);
            viewModel.medList.observe(this.lifecycleOwner, this::submitList);

            Med medication = getItem(position);
            medication.medDate = new Date();
            medication.id += 1;

            medDao.insertMed(medication);
            viewModel.setMedList(filterEt.getText().toString(),searchLimit);

        });

    }

    public String formatDateText(Date date) {
        SimpleDateFormat formatedDate = new SimpleDateFormat("d/M/y H:m", Locale.ENGLISH);
        return formatedDate.format(date);
    }

    public static class UserDiff extends DiffUtil.ItemCallback<Med> {

        @Override
        public boolean areItemsTheSame(@NonNull Med oldItem, @NonNull Med newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Med oldItem, @NonNull Med newItem) {
            return oldItem.equals(newItem);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView medicationName;
        private final TextView timesTaken;
        private final TextView dateTaken;

        private final ImageButton reuseBtn;
        private final ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            medicationName = (TextView) itemView.findViewById(R.id.medication);
            timesTaken = (TextView) itemView.findViewById(R.id.timesTaken);
            dateTaken = (TextView) itemView.findViewById(R.id.dateTaken);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.delete);
            reuseBtn = (ImageButton) itemView.findViewById(R.id.reuse);

        }


        public TextView getMedicationName() {
            return medicationName;
        }

        public TextView getTimesTaken() {
            return timesTaken;
        }

        public TextView getDateTaken() {
            return dateTaken;
        }

        public ImageButton getDeleteButton() {
            return deleteBtn;
        }
        public ImageButton getReuseBtn(){
            return reuseBtn;
        }
    }
}
