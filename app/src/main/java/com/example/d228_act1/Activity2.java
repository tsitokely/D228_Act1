package com.example.d228_act1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        // Création d'un objet intent
        Intent fromMain = getIntent();
        // Récupération du message de l'activité 1
        String message = fromMain.getExtras().getString("messageFromOne");
        // Mise à jour du textView
        ((TextView) findViewById(R.id.act2TextView)).setText(message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    public void finish() {
        Intent resultFromTwo = new Intent();
        resultFromTwo.putExtra("messageFromTwo", "RAS de la part de l'activité 2");
        setResult(RESULT_OK, resultFromTwo);
        super.finish();
    }

}
