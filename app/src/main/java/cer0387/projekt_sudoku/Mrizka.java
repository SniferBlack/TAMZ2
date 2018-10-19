package cer0387.projekt_sudoku;

import java.util.ArrayList;

public class Mrizka {
    private int[][] policka = new int[9][9];

    public Mrizka() {}

    public void setHodnota(int radek, int sloupec, int hodnota) {
        policka[radek][sloupec] = hodnota;
    }

    public int[][] getPolicka() {
        return policka;
    }


}
