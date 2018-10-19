package cer0387.projekt_sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class Klavesnice extends Dialog {

    private static final String TAG = "Klavesnice";

    private final View[] cisla = new View[9];
    private View klavesnice;
    private final int[] spatne_cisla;
    private final Mrizka mrizka;

    public Klavesnice(Context context, int[] spatne_cisla, Mrizka mrizka) {
        super(context);
        this.spatne_cisla = spatne_cisla;
        this.mrizka = mrizka;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.klavesnice);
        setContentView(R.layout.activity_klavesnice);
        findViews();
        for (int cislo : spatne_cisla) {
            if (cislo != 0) {
                cisla[cislo - 1].setVisibility(View.INVISIBLE);
            }
        }
        NastavButtony();
    }

    private void findViews() {
        klavesnice = findViewById(R.id.klavesnice);
        cisla[0] = findViewById(R.id.keypad_1);
        cisla[1] = findViewById(R.id.keypad_2);
        cisla[2] = findViewById(R.id.keypad_3);
        cisla[3] = findViewById(R.id.keypad_4);
        cisla[4] = findViewById(R.id.keypad_5);
        cisla[5] = findViewById(R.id.keypad_6);
        cisla[6] = findViewById(R.id.keypad_7);
        cisla[7] = findViewById(R.id.keypad_8);
        cisla[8] = findViewById(R.id.keypad_9);
    }


    private void NastavButtony() {
        for (int i = 0; i < cisla.length; i++) {
            final int hodnota = i + 1;
            cisla[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NastavHodnotu(hodnota);
                }
            });
        }

        klavesnice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NastavHodnotu(0);
            }
        });
    }

    private void NastavHodnotu(int i) {
        mrizka.setVybranaBunka(i);
        dismiss();
    }

    private boolean vhodneCislo(int bunka) {
        for(int i = 0; i < spatne_cisla.length; i++) {
            if(bunka == spatne_cisla[i])
                return false;
        }
        return true;
    }
}

