package com.pk.contactslist;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {
    EditText FirstNameEdittext, LastNameEdittext, numberEditText;
    String OriginalfirstName,OriginallastName,OriginalphoneNumber;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("New Contact");

        FirstNameEdittext = findViewById(R.id.editText_firstName);
        LastNameEdittext = findViewById(R.id.editText_lastName);
        numberEditText = findViewById(R.id.editText_Phone);

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "database-name").allowMainThreadQueries().build();

        populateFieldsFromIntent();
    }
    private  void populateFieldsFromIntent(){
        OriginalfirstName = getIntent().getStringExtra("FirstName");
        OriginallastName = getIntent().getStringExtra(("lastName"));
        OriginalphoneNumber = getIntent().getStringExtra("phone");

        if(OriginalfirstName == null) {return;}
            FirstNameEdittext.setText(OriginalfirstName);
            LastNameEdittext.setText(OriginallastName);
            numberEditText.setText(OriginalphoneNumber);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_delete_contact) {
            deleteContact();
            return true;
        } else if (id == R.id.action_save_contact) {
            saveContact();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void saveContact(){
        String firstName = FirstNameEdittext.getText().toString();
        String lastName = LastNameEdittext.getText().toString();
        String Phone = numberEditText.getText().toString();

        if(firstName.isEmpty() || lastName.isEmpty() || Phone.isEmpty()){return;}

        if(OriginalfirstName == null){
            Contact newContact = new Contact(firstName, lastName, Phone);
            db.contactDAO().insert(newContact);
        }else{
            Contact oldContact = db.contactDAO().findAContact(OriginalfirstName,OriginallastName,OriginalphoneNumber);
            oldContact.setFirstName(firstName);
            oldContact.setLastName(lastName);
            oldContact.setPhone(Phone);
            db.contactDAO().updateContact(oldContact);
        }


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void deleteContact(){
        String firstName = FirstNameEdittext.getText().toString();
        String lastName = LastNameEdittext.getText().toString();
        String Phone = numberEditText.getText().toString();

        Contact contact = db.contactDAO().findAContact(firstName, lastName, Phone);
        db.contactDAO().delete(contact);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
