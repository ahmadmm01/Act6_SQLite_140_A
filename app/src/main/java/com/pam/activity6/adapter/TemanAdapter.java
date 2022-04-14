package com.pam.activity6.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pam.activity6.MainActivity;
import com.pam.activity6.database.DBController;
import com.pam.activity6.database.Teman;
import com.pam.activity6.R;
import com.pam.activity6.edit_teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder>
{
    private ArrayList<Teman> listData;
    private Context control;

    public TemanAdapter(ArrayList<Teman> listData)
    {
        this.listData = listData;
    }

    @Override
    public TemanAdapter.TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        control = parent.getContext();
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position)
    {
        String nma, tlp, id;

        id = listData.get(position).getId();
        nma = listData.get(position).getNama();
        tlp = listData.get(position).getTelpon();
        DBController db = new DBController(control);

        holder.namaTxt.setTextColor(Color.BLUE);
        holder.telponTxt.setTextSize(20);
        holder.namaTxt.setText(nma);
        holder.telponTxt.setText(tlp);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                PopupMenu menu = new PopupMenu(control, holder.cardku);
                menu.inflate(R.menu.menu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        switch (item.getItemId())
                        {
                            case R.id.mnEdit:
                                Intent i = new Intent(control, edit_teman.class);
                                i.putExtra("id", id);
                                control.startActivity(i);
                                break;
                            case R.id.mnHapus:
                                HashMap<String, String> values = new HashMap<>();
                                values.put("id", id);
                                db.DeleteData(values);
                                Intent j = new Intent(control, MainActivity.class);
                                control.startActivity(j);
                                break;
                        }
                        return true;
                    }
                });
                menu.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return (listData != null)?listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder
    {
        private CardView cardku;
        private TextView namaTxt, telponTxt;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt =(TextView) view.findViewById(R.id.textTelpon);
        }
    }
}