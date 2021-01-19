package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date date = new Date();
        TextView textView = (TextView) findViewById(R.id.textView3);
        //long time = date.getTime();
        //long newTime = date.getTime();
        Button button = (Button) findViewById(R.id.button3); //Button to change the time displayed on screen
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = date.getTime();
                textView.setText(Long.toString(time));
            }
        });
        //за ся до тук
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}