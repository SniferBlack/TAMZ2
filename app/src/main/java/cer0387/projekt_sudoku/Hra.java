package cer0387.projekt_sudoku;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class Hra extends Activity {

    private static final String TAG = "Hra";
    public static final int OBTIZNOST_LEHKA = 0;
    public static final int OBTIZNOST_NORMAL = 1;
    public static final int OBTIZNOST_TEZKA = 2;
    public static final int OBTIZNOST_VLASTNI = 3;

    private int[] VyplnenaCisla;
    private final int[][][] SpatneCisla = new int[9][9][];

    private Mrizka mrizka;

    private final String lehkaHra = "360000000004230800000004200"
            + "070460003820000014500013020" + "001900000007048300000000045";
    private final String normalHra = "650000070000506000014000005"
            + "007009000002314700000700800" + "500000630000201000030000097";
    private final String tezkaHra = "009000000080605020501078000"
            + "000000700706040102004000000" + "000720903090301080000000600";
    private final String vlastniHra = "534678912672195348198342560"
            + "859761423426853791713924856" + "961537284287419635345286179";


    private String ULOZENA_HRA = "";
    protected static final int OBTIZNOST_POKRACUJE = -1;
    /*
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "NACTENO");
    }
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "VYTVOREN LAYOUT");
        int obtiznost = getIntent().getIntExtra("obtiznost", OBTIZNOST_LEHKA);
        VyplnenaCisla = getPole(obtiznost);
        KontrolaHodnot();

        mrizka = new Mrizka(this);
        setContentView(mrizka);
        mrizka.requestFocus();

    }

    private int[] getPole(int obtiznost) {
        String string;
        switch (obtiznost) {
            case OBTIZNOST_POKRACUJE:
                string = getPreferences(MODE_PRIVATE).getString(ULOZENA_HRA,
                        lehkaHra);
                break;
            case OBTIZNOST_NORMAL:
                string = normalHra;
                break;
            case OBTIZNOST_TEZKA:
                string = tezkaHra;
                break;
            case OBTIZNOST_VLASTNI:
                string = vlastniHra;
                break;
            case OBTIZNOST_LEHKA:
            default:
                string = lehkaHra;
                break;
        }
        return StringDoPole(string);
    }

    private static String PoleDoStringu(int[] pole) {
        StringBuilder naplneni = new StringBuilder();
        for (int i = 0; i < pole.length; i++) {
            naplneni.append(pole[i]);
        }
        return naplneni.toString();
    }

    protected static int[] StringDoPole(String naplneni) {
        int[] pole = new int[naplneni.length()];
        int index = 0;
        for (char cislo : naplneni.toCharArray()) {
            pole[index++] = cislo - '0';
        }
        return pole;
    }

    public void VyhodKlavesnici(int X, int Y) {
        int[] bunky = getSpatneCisla(X, Y);
        if (bunky.length == 9) {
            Toast toast = Toast.makeText(this, R.string.slepa_ulice,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Dialog v = new Klavesnice(this, bunky, mrizka);
            v.show();
        }

    }

    public boolean KontrolaHodnotySetBunka(int X, int Y, int bunka) {
        int[] bunky = getSpatneCisla(X, Y);
        if (bunka != 0) {
            for (int i = 0; i < bunky.length; i++) {
                if (bunka == bunky[i])
                    return false;
            }
        }
        setBunkaHodnota(X, Y, bunka);
        KontrolaHodnot();

        /* ulozeni do preferenci stringu cisel*/

        return true;
    }

    public int[] getSpatneCisla(int x, int y) {
        return SpatneCisla[x][y];
    }

    private void KontrolaHodnot() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                SpatneCisla[x][y] = KontrolaHodnot(x, y);
                Log.d(TAG, "Bunka[" + x + "][" + y + "] nemuze byt => " + PoleDoStringu(SpatneCisla[x][y]));
            }
        }
    }

    private int[] KontrolaHodnot(int x, int y) {
        int pole[] = new int[9];
        /*vodorovne*/
        for (int i = 0; i < 9; i++) {
            if (i == x)
                continue;
            int hodnota = getBunkaHodnota(i, y);
            if (hodnota != 0)
                pole[hodnota - 1] = hodnota;
        }
        /*svisle*/
        for (int i = 0; i < 9; i++) {
            if (i == y)
                continue;
            int hodnota = getBunkaHodnota(x, i);
            if (hodnota != 0)
                pole[hodnota - 1] = hodnota;
        }

        int od_x = (x / 3) * 3;
        int od_y = (y / 3) * 3;
        for (int i = od_x; i < od_x + 3; i++) {
            for (int j = od_y; j < od_y + 3; j++) {
                if (i == x && j == y)
                    continue;
                int hodnota = getBunkaHodnota(i, j);
                if (hodnota != 0)
                    pole[hodnota - 1] = hodnota;
            }
        }

        int pocet = 0;
        for (int hodnota: pole) {
            if (hodnota != 0)
                pocet++;
        }
        int spatne[] = new int[pocet];
        pocet = 0;
        for (int t : pole) {
            if (t != 0)
                spatne[pocet++] = t;
        }
        return spatne;
    }

    private int getBunkaHodnota(int x, int y) {
        return VyplnenaCisla[y * 9 + x];
    }

    private void setBunkaHodnota(int x, int y, int bunka) {
        VyplnenaCisla[y * 9 + x] = bunka;
    }

    protected String getBunkaString(int x, int y) {
        int bunka = getBunkaHodnota(x, y);
        if (bunka == 0)
            return "";
        else
            return String.valueOf(bunka);
    }
}

