package cer0387.projekt_sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private void openNewGameDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.vyber_obtiznost)
                .setItems(R.array.obtiznost,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int obtiznost) {
                                Toast.makeText(getApplicationContext(), "Začínáš novou hru", Toast.LENGTH_SHORT).show();
                                startGame(obtiznost);
                            }
                        }).show();
    }

    private void startGame(int obtiznost) {
        Intent intent = new Intent(this, Hra.class);
        intent.putExtra("obtiznost", obtiznost);
        startActivity(intent);
    }

    public void onZacitNovouHruButtonClicked(View view) {
        openNewGameDialog();
    }

    public void onPokracovatButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Pokračuješ ve hře", Toast.LENGTH_SHORT).show();
        startGame(-1);
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

    public void onKonecButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Konec", Toast.LENGTH_SHORT).show();
        finish();
    }
}
