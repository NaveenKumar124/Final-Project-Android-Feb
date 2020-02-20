package com.example.finalproject_feb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Note> notes;

    Adapter(Context context, List<Note> notes){
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_list_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder viewHolder, int i) {
        String  title    = notes.get(i).getTitle();
        String  date     = notes.get(i).getDate();
        String  time     = notes.get(i).getTime();
        long    id       = notes.get(i).getId();
        Log.d("date on ", "Date on: "+date);

        viewHolder.nTitle.setText(title);
        viewHolder.nDate.setText(date);
        viewHolder.nTime.setText(time);
        viewHolder.nID.setText(String.valueOf(notes.get(i).getId()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

     public class ViewHolder extends RecyclerView.ViewHolder{

         TextView nTitle,nDate,nTime;
         TextView nID;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             nTitle  = itemView.findViewById(R.id.nTitle);
             nDate   = itemView.findViewById(R.id.nDate);
             nTime   = itemView.findViewById(R.id.nTime);
             nID     = itemView.findViewById(R.id.listId);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(v.getContext(),"Item clicked. ",Toast.LENGTH_SHORT).show();
                 }
             });
         }
     }
}
