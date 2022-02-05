package com.example.imthon;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Contact> contacts;

    public RecyclerAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_view, parent, false);
        return new ContactViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        if (holder instanceof ContactViewHolder) {
            ImageView iv_profile = ((ContactViewHolder) holder).iv_profile;
            TextView tv_name = ((ContactViewHolder) holder).tv_name;
            TextView tv_phoneNumber = ((ContactViewHolder) holder).tv_phoneNumber;

            iv_profile.setImageResource(contact.profile);
            tv_name.setText(contact.name);
            tv_phoneNumber.setText(contact.phoneNumber);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = "tel:" + tv_phoneNumber.getText();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    context.startActivity(intent);
                }
            });

        }
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView iv_profile;
        public TextView tv_name;
        public TextView tv_phoneNumber;

        public ContactViewHolder(View v) {
            super(v);
            this.view = v;
            iv_profile = view.findViewById(R.id.iv_profile);
            tv_name = view.findViewById(R.id.tv_name);
            tv_phoneNumber = view.findViewById(R.id.tv_phoneNumber);
        }
    }
}
