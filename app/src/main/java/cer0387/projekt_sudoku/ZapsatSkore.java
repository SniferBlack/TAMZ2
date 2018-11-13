package cer0387.projekt_sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ZapsatSkore extends Activity {

    EditText editTextViewNick;
    private String nick;
    private long cas;
    TextView vypis_skore;
    PlayerRepository playerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapsat_skore);
        playerRepository = new PlayerRepository(getApplication());
        long getCas = getIntent().getLongExtra("cas", -1);
        if(getCas==-1)
        {
            Toast.makeText(getApplicationContext(), "Doslu k chybě při přenosu skore - kontaktujte developera", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cas=getCas;
            vypis_skore=findViewById(R.id.textView7);
            vypis_skore.setText("Skore: "+(cas/1000)+" sekund");
        }

    }

    public void onPokracovatButtonClicked(View view) {
        editTextViewNick=findViewById(R.id.editText);
        nick = editTextViewNick.getText().toString();
        Toast.makeText(getApplicationContext(), "Pokracujes do vysledku...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Vysledky.class);
        /*intent.putExtra("nick", nick);
        intent.putExtra("cas", cas);*/
        Player novy = new Player();
        novy.setName(nick);
        novy.setTime((int)cas);

        playerRepository.insert(novy);
        startActivity(intent);
    }
}
