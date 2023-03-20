package com.example.d228_act1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class Activity2 extends AppCompatActivity {

    // Déclaration de quelques variables utiles
    // Déclaration du spinner
    Spinner liste;
    // Déclaration de l'adapter du Spinner
    ArrayAdapter listeAdapter;
    // Déclaration d'une collection qui contiendra les données
    Collection<String> dataFromWeb;
    // Déclaration d'une variable qui contiendra les données de manière individuelle
    String inputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        // Création du bouton flottant de mise à jour du spinner
        FloatingActionButton fabUpdate = findViewById(R.id.fabUpdate);
        // Création d'un listener au click de bouton
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromWeb();
            }
        });

        // Déclaration du spinner
        liste = findViewById(R.id.spinner);
        // Déclaration de son adapter et liaison du spinner à son adapter
        listeAdapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item);
        liste.setAdapter(listeAdapter);

        // Création d'un objet intent
        Intent fromMain = getIntent();
        // Récupération du message de l'activité 1
        String message = fromMain.getExtras().getString("messageFromOne");
        // Mise à jour du textView
        ((TextView) findViewById(R.id.act2TextView)).setText(message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void getDataFromWeb() {
        // Création d'un thread qui permettra de récupèrer les données du GIST et de faire la MàJ du Spinner
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Try block
                try {
                    // définition de l'URL
                    URL url = new URL("https://gist.githubusercontent.com/tsitokely/f4d16284e400c17e56c39f8970334d19/raw/f55c21a47415c3d95d834b5fd4efa55030955464/simple_list");
                    // connection
                    URLConnection urlC = url.openConnection();
                    // récupération du flux provenant de l'ouverture de connection à partir d'un BufferedReader
                    BufferedReader inBuffer = new BufferedReader(new InputStreamReader(urlC.getInputStream(), StandardCharsets.ISO_8859_1));
                    // Assignation de la variable de collection qui contiendra les données à un Array de type string
                    dataFromWeb = new ArrayList<String>();
                    // Boucle sur le BufferedReader inBuffer jusqu'à ce que la méthode readLine() retourne une valeur nulle
                    while ((inputData = inBuffer.readLine()) != null) {
                        // Rajout des données du buffer lit par ligne dans la collection
                        dataFromWeb.add(inputData);
                    }
                    // Mise à jour du spinner liste et de son adapter avec les données de dataFromWeb en post(Runnable) sur l'UIThread
                    liste.post(new Runnable() {
                        @Override
                        public void run() {
                            listeAdapter.addAll(dataFromWeb);
                        }
                    });
                }
                // L'excéption n'est pas gérée dans ce projet
                catch (Exception e) {
                }
            }
        }).start();
    }


    public void finish() {
        Intent resultFromTwo = new Intent();
        resultFromTwo.putExtra("messageFromTwo", "RAS de la part de l'activité 2");
        setResult(RESULT_OK, resultFromTwo);
        super.finish();
    }

}
