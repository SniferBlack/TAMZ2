package cer0387.projekt_sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class Klavesnice extends Dialog {

    public Klavesnice(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.klavesnice);
        setContentView(R.layout.activity_klavesnice);

    }
}

