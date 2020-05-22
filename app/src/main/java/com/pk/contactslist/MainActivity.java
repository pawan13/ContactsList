package com.pk.contactslist;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.OnItemSelectedListener{

    RecyclerView recyclerview;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutmanager;
    AppDatabase db;
    ArrayList<Contact> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);

            }
        });
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "database-name").allowMainThreadQueries().build();
        fetchContacts();
    }

    private void fetchContacts() {
        List<Contact> contacts = db.contactDAO().getAllContacts();
        contactsList = new ArrayList(contacts);

        setUpRecyclerView();
    }
    private void setUpRecyclerView(){
        recyclerview = findViewById(R.id.contacts_recycler_view);
        recyclerview.setHasFixedSize(true);

        layoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutmanager);

        adapter = new ContactsAdapter(contactsList, this);
        recyclerview.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(int position) {
        Contact selectedContact = contactsList.get(position);
        Intent intent = new Intent(this, AddContactActivity.class);

        intent.putExtra("FirstName", selectedContact.getFirstName());
        intent.putExtra("lastName", selectedContact.getLastName());
        intent.putExtra("phone", selectedContact.getPhone());
        startActivity(intent);

    }
}

