package cer0387.projekt_sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class VyberObtiznosti extends Activity {
    private int vybranaObtiznost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vyber_obtiznosti);
    }

    public void onZpetButtonClicked(View view) {
        finish();
    }

    public void onObtiznostRadioButtonsClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonLehka:
                if (checked) {
                    vybranaObtiznost = 0;
                    Toast.makeText(getApplicationContext(), "Lehká obtížnost", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioButtonNormal:
                if (checked) {
                    vybranaObtiznost = 1;
                    Toast.makeText(getApplicationContext(), "Normální obtížnost", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioButtonTezka:
                if (checked) {
                    vybranaObtiznost = 2;
                    Toast.makeText(getApplicationContext(), "Těžká obtížnost", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void onZacniHruButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Začínáš hru", Toast.LENGTH_SHORT).show();
    }
}
