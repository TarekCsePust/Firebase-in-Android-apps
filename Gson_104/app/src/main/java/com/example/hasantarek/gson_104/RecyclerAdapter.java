package com.example.hasantarek.gson_104;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HASAN TAREK on 1/23/2018.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Contact>contacts = new ArrayList<>();
    private String image_path = "http:// 192.168.0.105/android_104/upload";
    private Context context;

    public RecyclerAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.Name.setText(contacts.get(position).getName());
        holder.Email.setText(contacts.get(position).getEmail());
        String path = image_path+contacts.get(position).getName() + ".jpg";
        Glide.with(context).load(path).into(holder.profile_pic);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView Name,Email;
        ImageView profile_pic;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name = (TextView)itemView.findViewById(R.id.item_name);
            Email = (TextView)itemView.findViewById(R.id.item_email);
            profile_pic = (ImageView)itemView.findViewById(R.id.item_img);
        }
    }
}
