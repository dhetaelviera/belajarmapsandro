package com.example.testinggps.Adapter;

import android.content.Context;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testinggps.Model.ModelData;
import com.example.testinggps.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.HolderData> {
    private List<ModelData> mItems;
    private Context mContext;

    public Adapter(Context mContext, List<ModelData> items){
        this.mItems=items;
        this.mContext=mContext;
    }

    @Override
    public HolderData onCreateViewHolder( ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_maps,parent,false);
        HolderData holderData=new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int i) {
        ModelData md=mItems.get(i);
        holder.tvusername.setText(md.getNorek());

        holder.md=md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder{

        TextView tvusername, tvnama;
        ModelData md;

        //nyimpen data
        public HolderData( View view) {
            super(view);
//
  //     tvusername=(TextView) view.findViewById(R.id.username);
//
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent update=new Intent(context, UpdateDelete.class);
//                    update.putExtra("update",0);
//                    update.putExtra("username", md.getUsername());
//                    update.putExtra("nama", md.getNama());
//                    update.putExtra("email", md.getEmail());
//                    update.putExtra("password", md.getPassword());
//                    context.startActivity(update);
//                }
//            });

        }
    }
}
