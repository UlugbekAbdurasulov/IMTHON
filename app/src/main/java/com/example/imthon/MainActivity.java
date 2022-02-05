package com.example.imthon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.view.View;
import android.widget.Toast;



import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    ArrayList<Contact> contacts = new ArrayList<>();
    ArrayList<String> pNumber = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    void initViews() {

        context = this;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));


        checkPermission();

        refreshAdapter(getContactList());

    }

    void refreshAdapter(ArrayList<Contact> contacts) {
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(context,contacts);
        recyclerView.setAdapter(recyclerAdapter);
    }



    private void checkPermission(){

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.READ_CONTACTS
            }, 100);
        } else{
            getContactList();
        }
    }

    private ArrayList<Contact> getContactList() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";
        Cursor cursor = getContentResolver().query(uri,null,null,null,sort);


        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?";

                Cursor phoneCursor = getContentResolver().query(uriPhone,null,selection,new String[]{id},null);

                if(phoneCursor.moveToNext()){
                    @SuppressLint("Range") String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contacts.add(new Contact(R.drawable.contact,name,number));
                    pNumber.add(number);
                    phoneCursor.close();
                }
            }

            cursor.close();

        }
        return contacts;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getContactList();
        }else {
            Toast.makeText(MainActivity.this,"PD",Toast.LENGTH_LONG).show();
            checkPermission();
        }
    }
}