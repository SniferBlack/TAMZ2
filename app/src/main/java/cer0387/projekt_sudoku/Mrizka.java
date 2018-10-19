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

    public void kopieHodnoty(int[][] kopie) {
        for (int i = 0; i < kopie.length; i++) {
            for (int j = 0; j < kopie[i].length; j++) {
                policka[i][j] = kopie[i][j];
            }
        }
    }

    public boolean Vyplneno() {
        for (int i = 0; i < policka.length; i++) {
            for (int j = 0; j < policka[i].length; j++) {
                if (policka[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean KontrolaVyskytuVodoroveSvisle() {
        /* vodorovne */
        for (int i = 0; i < policka.length; i++) {
            ArrayList<Integer> cisla = new ArrayList<>();
            for (int j = 0; j < policka[i].length; j++) {
                int cislo = policka[i][j];
                if (cisla.contains(cislo)) {
                    return false;
                } else {
                    cisla.add(cislo);
                }
            }
        }

        /* svisle */
        for (int i = 0; i < policka.length; i++) {
            ArrayList<Integer> cisla = new ArrayList<>();
            for (int j = 0; j < policka[i].length; j++) {
                int cislo = policka[j][i];
                if (cisla.contains(cislo)) {
                    return false;
                } else {
                    cisla.add(cislo);
                }
            }
        }
        /* ctverce se kontroluji v Bunky.java*/
        return true;
    }

    public int getHodnota(int radek, int sloupec) {
        return policka[radek][sloupec];
    }

    
}
