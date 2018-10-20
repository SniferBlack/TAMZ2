package cer0387.projekt_sudoku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;


public class Vysledky extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vysledky);

        long cas = getIntent().getLongExtra("cas", -1);
        String nick = getIntent().getStringExtra("nick");
        if(cas!=-1)
        {
            Toast.makeText(getApplicationContext(), "Nick: "+nick+", skore: "+cas, Toast.LENGTH_LONG).show();
        }

        /*
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);


        VysledekAdapter itemsAdapter = new VysledekAdapter(this, R.layout.list_item_vysledek, items);
        ListView lv = (ListView)findViewById(R.id.listView1);
        lv.setAdapter(itemsAdapter);
        */
    }

    public void onZpetButtonClicked(View view)
    {
        Toast.makeText(getApplicationContext(), "Zpet do hlavniho menu", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
