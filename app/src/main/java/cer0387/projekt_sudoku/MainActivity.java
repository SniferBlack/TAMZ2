package cer0387.projekt_sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;


public class MainActivity extends Activity {

    SharedPreferences mySharedHra;
    SharedPreferences mySharedCisla;
    SharedPreferences mySharedCas;
    SharedPreferences.Editor mySharedEditor;
    SharedPreferences.Editor mySharedEditor2;

    private String cisla;
    private long cas;
    private boolean Hra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySharedHra = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        Hra = mySharedHra.getBoolean("hra",false);

        mySharedCisla = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        mySharedCas = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        if(Hra)
        {
            Button pokracovat = findViewById(R.id.pokracovat);
            pokracovat.setVisibility(View.VISIBLE);
            cisla = mySharedCisla.getString("cisla","");
            cas = mySharedCas.getLong("cas",-1);
        }
        else
        {
            Button pokracovat = findViewById(R.id.pokracovat);
            pokracovat.setVisibility(View.INVISIBLE);
        }
    }

    private void vyberObtiznosti() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.vyber_obtiznost)
                .setItems(R.array.obtiznost,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int obtiznost) {
                                Toast.makeText(getApplicationContext(), "Začínáš novou hru", Toast.LENGTH_SHORT).show();
                                Hra=false;
                                startGame(obtiznost);
                            }
                        }).show();
    }

    private void startGame(int obtiznost) {
        Intent intent = new Intent(this, Hra.class);
        intent.putExtra("obtiznost", obtiznost);
        if(Hra)
        {
            intent.putExtra("cisla", cisla);
            intent.putExtra("cas", cas);
            Log.d("Main", "cas: " + cas);
        }
        else
        {
            mySharedEditor = mySharedHra.edit();
            mySharedEditor.putBoolean("hra",false);
            mySharedEditor.apply();
            mySharedEditor2 = mySharedCas.edit();
            mySharedEditor2.putLong("cas",0);
            mySharedEditor2.apply();
        }
        startActivity(intent);
    }

    public void onZacitNovouHruButtonClicked(View view) {
        vyberObtiznosti();
    }

    public void onPokracovatButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Pokračuješ ve hře", Toast.LENGTH_SHORT).show();
        startGame(-1);
    }

    public void onVysledkyButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Výsledky her", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Vysledky.class);
        startActivity(intent);
    }

    public void onNapovedaButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Herní nápověda", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Napoveda.class);
        startActivity(intent);
    }

    public void onKonecButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Konec", Toast.LENGTH_SHORT).show();
        finish();
    }

}
