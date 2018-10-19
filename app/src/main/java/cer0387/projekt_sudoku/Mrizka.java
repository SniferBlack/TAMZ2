package cer0387.projekt_sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class Mrizka extends View {

    private float sirka;
    private float vyska;
    private int X;
    private int Y;
    private Rect VybranaBunka = new Rect();

    private static final String TAG = "Mrizka";
    private final Hra hra;

    private static final String ULOZIT_X = "X";
    private static final String ULOZIT_Y = "Y";
    private static final String AKTUALNI_STAV = "aktualnistav";
    private static final int ID = 77;

    public Mrizka(Context context) {
        super(context);
        this.hra = (Hra) context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        setId(ID);
    }

    /*
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable p = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putInt(ULOZIT_X, X);
        bundle.putInt(ULOZIT_Y, Y);
        bundle.putParcelable(AKTUALNI_STAV, p);
        Log.d(TAG, "X:" + X + ", Y:" + Y + " ULOZENO");
        return p;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        vyber(bundle.getInt(ULOZIT_X), bundle.getInt(ULOZIT_Y));
        Log.d(TAG,"X:" + bundle.getInt(ULOZIT_X) + ", Y:" + bundle.getInt(ULOZIT_Y) + " RESTORED");
        super.onRestoreInstanceState(bundle.getParcelable(AKTUALNI_STAV));
    }*/

    @Override
    protected void onSizeChanged(int sirka, int vyska, int stara_sirka, int stara_vyska) {
        super.onSizeChanged(sirka, vyska, stara_sirka, stara_vyska);
        this.sirka = sirka / 9f;
        this.vyska = vyska / 9f;
        ObarviVybranouBunku(X, Y, VybranaBunka);
        Log.d(TAG, "Zmena rozliseni sirka:" + sirka + " vyska:" + vyska);
    }

    private void ObarviVybranouBunku(int x, int y, Rect bunka) {
        bunka.set((int) (X * sirka), (int) (Y * vyska), (int) (X * sirka + sirka), (int) (Y * vyska + vyska));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint pozadi = new Paint();
        pozadi.setColor(getResources().getColor(R.color.mrizka_background));
        canvas.drawRect(0, 0, getWidth(), getHeight(), pozadi);

        Paint vnitrniMrizka = new Paint();
        vnitrniMrizka.setColor(getResources().getColor(R.color.vnitrniMrizka));

        Paint ctverceMrizka = new Paint();
        vnitrniMrizka.setColor(getResources().getColor(R.color.ctverceMrizka));


        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * vyska, getWidth(), i * vyska, vnitrniMrizka);
            canvas.drawLine(0, i * vyska + 1, getWidth(), i * vyska + 1, ctverceMrizka);

            canvas.drawLine(i * sirka, 0, i * sirka, getHeight(), vnitrniMrizka);
            canvas.drawLine(i * sirka + 1, 0, i * sirka + 1, getHeight(), ctverceMrizka);
        }

        for (int i = 0; i < 9; i++) {
            if (i % 3 != 0)
                continue;
            canvas.drawLine(0, i * vyska, getWidth(), i * vyska, ctverceMrizka);
            canvas.drawLine(0, i * vyska + 1, getWidth(), i * vyska + 1, ctverceMrizka);

            canvas.drawLine(i * sirka, 0, i * sirka, getHeight(), ctverceMrizka);
            canvas.drawLine(i * sirka + 1, 0, i * sirka + 1, getHeight(), ctverceMrizka);
        }

        Paint cisla = new Paint(Paint.ANTI_ALIAS_FLAG);

        cisla.setColor(getResources().getColor(R.color.mrizka_cisla));
        cisla.setStyle(Paint.Style.FILL);
        cisla.setTextSize(vyska * 0.75f);
        cisla.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics metrics = cisla.getFontMetrics();
        float x = sirka / 2;
        float y = (vyska - metrics.ascent - metrics.descent) / 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(hra.getBunkaString(i, j), i * sirka + x, j * vyska + y, cisla);
            }
        }

        Paint vybranaBunka = new Paint();
        vybranaBunka.setColor(getResources().getColor(R.color.mrizka_vybrane));
        vybranaBunka.setAlpha(127);
        canvas.drawRect(VybranaBunka, vybranaBunka);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown:" + keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                vyber(X, Y + 1);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                vyber(X - 1, Y);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                vyber(X + 1, Y);
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                vyber(X, Y - 1);
                break;
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                hra.VyhodKlavesnici(X, Y);
            default:
                return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);

        vyber((int) (event.getX() / sirka), (int) (event.getY() / vyska));

        hra.VyhodKlavesnici(X, Y);

        Log.d(TAG, "Uzivatel vybral mobilni souradnice : " + event.getX() + ", " + event.getY());

        return true;
    }

    private void vyber(int x, int y) {
        invalidate(VybranaBunka);

        X = (x + 9) % 9;
        Y = (y + 9) % 9;

        ObarviVybranouBunku(X, Y, VybranaBunka);
        invalidate(VybranaBunka);

        /* shared preference - vybrana X a Y*/

        Log.d(TAG, "vybral si : [" + X + "][" + Y+ "]");
    }

    public void setVybranaBunka(int bunka) {
        if (hra.KontrolaHodnotySetBunka(X, Y, bunka)) {
            invalidate();
            Log.d(TAG, "Nastavil si vybranou bunku : " + bunka);

        } else {
            Log.d(TAG, "Nemohl si nastavit : " + bunka);
        }
    }
}
