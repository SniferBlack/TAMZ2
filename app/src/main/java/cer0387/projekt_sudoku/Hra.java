package cer0387.projekt_sudoku;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Random;


public class Hra extends Activity {

    private static final String TAG = "Hra";
    public static final int OBTIZNOST_LEHKA = 0;
    public static final int OBTIZNOST_NORMAL = 1;
    public static final int OBTIZNOST_TEZKA = 2;
    public static final int OBTIZNOST_VLASTNI = 3;

    private int[] VyplnenaCisla;
    private final int[][][] SpatneCisla = new int[9][9][];

    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private long pokracovani_cas=0;

    private Mrizka mrizka;

    private final String lehkaHra = "360000000004230800000004200"
            + "070460003820000014500013020" + "001900000007048300000000045";
    private final String normalHra = "650000070000506000014000005"
            + "007009000002314700000700800" + "500000630000201000030000097";
    private final String tezkaHra = "009000000080605020501078000"
            + "000000700706040102004000000" + "000720903090301080000000600";
    private final String vlastniHra = "534678912672195348198342560"
            + "859761423426853791713924856" + "961537284287419635345286170";

    static String easy[] =      {"690471058708000690004809000020000003087500000005600701069100805000030000053080200","106457900000200500040008030001000829798310000005080300610045200980000006000860003","521003000074682591000001007000900008040026900689010370002370400407008159890040700","507000018082145003000008590000006084000000000041387005700060841290000006006870000","709080300058420001006130908190856203083094600400001080970068400030002076600010000","805070064003506800006843920908020040430960170107030200589010406000050001601380090","431000050605043009000815306314629000800400102200058463103090005008004920972501600","006480090019506400400020160060014000090705084504009670620008509930007840040302700","000079065000003002005060093340050106000000000608020059950010600700600000820390000"};
    static String medium[] =    {"070030620320000007100072000040000000260014070000020400007000002030000000010067004","507000006300070000006005020105002960003000010000051000700000040200607058680010000","008300000030005000100600005086100209300002000000006050005000090003028060014000500","583000600000350080009010002800000006010093800004060000050004000100900008007080200","080100040300800790029007000857601420000000000004003080000910034030080000042000800","800003090000000073630048000200500960000010008076000000002005009504000007000800000","003400000001020000490300602532004000000070940900010500300009701180000000700100000","704102000090000030003007080000020900905000300206075000000000600009008003002709040","000001000409060001070030900008010020004020800700040009006000000003006000200083695"};
    static String hard[] =      {"060000030000090000000000000600010000000000200000000000000000000008000000000006000","000008000000000000020000007030000000000060100000000000000000000070000000000001000","000007000000000200050000000000000000800000000000090400000000000070004000000000000","600000008000500000000000000000000000080000070000020000000000000004000000000008000","001000000000090000000000080800000600000080000000000000000000000060000000000010000","000900500000000000400000000001000000000000000000001900003000000000300000000000000","000000000000000100100008000000000000060000050000007000000000000600000000000800000","000000000000000000006007003070080000000000000000000008000000000000003000600000000","002008050000040070480072000008000031600080005570000600000960048090020000030800900"};


    private boolean ROZEHRANA=false;
    protected static final int POKRACOVANI = -1;

    SharedPreferences mySharedHra1;
    SharedPreferences mySharedCisla1;
    SharedPreferences mySharedCas1;
    SharedPreferences.Editor mySharedEditor1;
    SharedPreferences.Editor mySharedEditor2;
    SharedPreferences.Editor mySharedEditor3;
    SharedPreferences.Editor mySharedEditor4;
    SharedPreferences.Editor mySharedEditor5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "VYTVOREN LAYOUT");
        int obtiznost = getIntent().getIntExtra("obtiznost", OBTIZNOST_LEHKA);

        mySharedHra1 = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        mySharedCisla1 = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        mySharedCas1 = getSharedPreferences("myPref", Context.MODE_PRIVATE);

        VyplnenaCisla = getPole(obtiznost);
        KontrolaHodnot();

        mrizka = new Mrizka(this);
        setContentView(mrizka);
        mrizka.requestFocus();

        if(ROZEHRANA)
        {
            Log.d(TAG, "Rozehrana: " + ROZEHRANA);
            if(updatedTime == -1) {
                Toast toast = Toast.makeText(this, "Nastala chyba s nacitanim casu, mas vyresetovane skore! Buď rád.",Toast.LENGTH_SHORT);
            }
        }
        mySharedEditor1 = mySharedHra1.edit();
        mySharedEditor1.putBoolean("hra",true);
        mySharedEditor1.apply();
        mySharedEditor2 = mySharedCisla1.edit();
        mySharedEditor2.putString("cisla",PoleDoStringu(VyplnenaCisla));
        mySharedEditor2.apply();

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    private int[] getPole(int obtiznost) {
        String string;
        Random rn = new Random();
        int random = rn.nextInt((9 - 1) + 1) + 1;
        random = random - 1;
        switch (obtiznost) {
            case POKRACOVANI:
                ROZEHRANA=true;
                pokracovani_cas = getIntent().getLongExtra("cas", -1);
                Log.d(TAG, "pokracovani-cas-vlozeni: " + pokracovani_cas);
                String cisla = getIntent().getStringExtra("cisla");
                string = cisla;
                break;
            case OBTIZNOST_NORMAL:
                string = medium[random];
                break;
            case OBTIZNOST_TEZKA:
                string = hard[random];
                break;
            case OBTIZNOST_VLASTNI:
                string = vlastniHra;
                break;
            case OBTIZNOST_LEHKA:
            default:
                string = easy[random];
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


        if(konecHry())
        {
            long cas = updatedTime + pokracovani_cas;
            Log.d(TAG, "konec-skore-cas: " + cas);

            mySharedEditor3 = mySharedHra1.edit();
            mySharedEditor3.putBoolean("hra",false);
            mySharedEditor3.apply();
            mySharedEditor4 = mySharedCas1.edit();
            mySharedEditor4.putLong("cas",0);
            mySharedEditor4.apply();
            mySharedEditor5 = mySharedCisla1.edit();
            mySharedEditor5.putString("cisla","");
            mySharedEditor5.apply();

            Intent intent = new Intent(this, ZapsatSkore.class);
            intent.putExtra("cas", cas);
            startActivity(intent);
        }
        else
        {
            /* ulozeni do preferenci stringu cisel*/
            mySharedEditor3 = mySharedCas1.edit();
            mySharedEditor3.putLong("cas",updatedTime+pokracovani_cas);
            Log.d(TAG, "ulozeni-cas: " + updatedTime+pokracovani_cas);
            mySharedEditor3.apply();
            mySharedEditor4 = mySharedCisla1.edit();
            mySharedEditor4.putString("cisla",PoleDoStringu(VyplnenaCisla));
            mySharedEditor4.apply();

        }
        if(updatedTime==10e12)
        {
            Toast.makeText(getApplicationContext(), "Prekrocil si stanoveny limit pro jednu hru!", Toast.LENGTH_SHORT).show();
            finish();
        }
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

    private boolean konecHry()
    {
        for(int i=0; i < VyplnenaCisla.length; i++)
        {
            if(VyplnenaCisla[i]==0)
            {
                return false;
            }
        }
        return true;
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            customHandler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(Prefs.getMusic(this))
            Music.play(this, R.raw.riddle);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Prefs.getMusic(this))
            Music.stop(this);
    }
}

