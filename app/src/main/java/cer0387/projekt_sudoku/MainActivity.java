package cer0387.projekt_sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.content.SharedPreferences;


public class MainActivity extends Activity {

    SharedPreferences Hra;
    SharedPreferences.Editor mySharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onZacitNovouHruButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Začínáš novou hru", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, VyberObtiznosti.class);
        startActivity(intent);
    }

    public void onPokracovatButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Pokračuješ ve hře", Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(this,Hra.class);
        startActivity(intent);*/
    }

    public void onVysledkyButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Výsledky her", Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(this,Vysledky.class);
        startActivity(intent);*/
    }

    public void onNapovedaButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Herní nápověda", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Napoveda.class);
        startActivity(intent);
    }
}
