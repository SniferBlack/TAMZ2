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
    private int skore;
    TextView vypis_skore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapsat_skore);

        int getSkore = getIntent().getIntExtra("skore", -1);
        if(getSkore==-1)
        {
            Toast.makeText(getApplicationContext(), "Doslu k chybě při přenosu skore - kontaktujte developera", Toast.LENGTH_SHORT).show();
        }
        else
        {
            skore=getSkore;
            vypis_skore=findViewById(R.id.textView7);
            vypis_skore.setText("Skore: "+skore);
        }

    }

    public void onPokracovatButtonClicked(View view) {
        editTextViewNick=findViewById(R.id.editText);
        nick = editTextViewNick.getText().toString();
        Toast.makeText(getApplicationContext(), "Pokracujes do vysledku...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Vysledky.class);
        intent.putExtra("nick", nick);
        intent.putExtra("skore", skore);
        startActivity(intent);
    }
}
