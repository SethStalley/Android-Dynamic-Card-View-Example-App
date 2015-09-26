package com.paradisegardens.requi.paradisegardens;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Contact extends AppCompatActivity {
    Email emailClient = new Email(this);
    Toolbar toolbar;

    //Msg data
    EditText senderEmail;
    EditText msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        senderEmail = (EditText) findViewById(R.id.userEmail);
        msg = (EditText) findViewById(R.id.userComment);

        //Send button
        final Button button = (Button) findViewById(R.id.btn_sendComment);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                emailClient.sendEmail(senderEmail.getText().toString(),
                        msg.getText().toString());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
