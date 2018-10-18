package cer0387.projekt_sudoku;

import java.util.ArrayList;
import java.util.Random;

public class SudokuGenerator {
    private static SudokuGenerator instance;

    private ArrayList<ArrayList<Integer>> moznosti = new ArrayList<ArrayList<Integer>>();

    private Random rand = new Random();

    private SudokuGenerator() {}

    public static SudokuGenerator vratInstanci()
    {
        if(instance == null)
        {
            instance = new SudokuGenerator();
        }
        return instance;
    }

    public int[][] generujCisla()
    {
        int[][] Sudoku = new int[9][9];

        int aktualni_pozice = 0;

        clear(Sudoku);

        while(aktualni_pozice < 81)
        {
            if(moznosti.get(aktualni_pozice).size() != 0)
            {
                int i = rand.nextInt(moznosti.get(aktualni_pozice).size());
                int number = moznosti.get(aktualni_pozice).get(i);

                if(!KontrolaVyskyt(Sudoku,aktualni_pozice, number))
                {
                    int x_pozice = aktualni_pozice % 9;
                    int y_pozice = aktualni_pozice / 9;

                    Sudoku[x_pozice][y_pozice] = number;

                    moznosti.get(aktualni_pozice).remove(i);

                    aktualni_pozice++;
                }
                else
                {
                    moznosti.get(aktualni_pozice).remove(i);
                }
            }
            else
            {
                for(int i = 1; i <= 9; i++)
                {
                    moznosti.get(aktualni_pozice).add(i);
                }
                aktualni_pozice--;
            }
        }

        return Sudoku;
    }

    private void clear(int[][] Sudoku)
    {
        for(int y =0; y < 9; y++)
        {
            for(int x=0; x <9; x++)
            {
                Sudoku[x][y] = -1;
            }
        }
        for(int x = 0; x < 81; x++)
        {
            moznosti.add(new ArrayList<Integer>());
            for(int i = 1; i <= 9; i++)
            {
                moznosti.get(x).add(i);
            }
        }
    }

    private boolean KontrolaVyskyt(int[][] Sudoku, int aktualni_pozice, final int number)
    {
        int x_pozice = aktualni_pozice % 9;
        int y_pozice = aktualni_pozice / 9;

        if(KontrolaVodorovneVyskyt(Sudoku,x_pozice,y_pozice,number)||
                KontrolaSvisleVyskyt(Sudoku,x_pozice,y_pozice,number)||
                    KontrolaCtverecVyskyt(Sudoku,x_pozice,y_pozice,number))
            return true;

        return false;
    }
    /* Vrací true pokud se již vyskytuje stejné číslo */
    private boolean KontrolaVodorovneVyskyt(final int[][] Sudoku, final int x_pozice, final int y_pozice, final int number)
    {
        for(int x = x_pozice - 1; x >= 0; x--)
        {
            if(number == Sudoku[x][y_pozice])
            {
                return true;
            }
        }
        return false;
    }
    private boolean KontrolaSvisleVyskyt(final int[][] Sudoku, final int x_pozice, final int y_pozice, final int number)
    {
        for(int y = y_pozice - 1; y >= 0; y--)
        {
            if(number == Sudoku[x_pozice][y])
            {
                return true;
            }
        }
        return false;
    }
    private boolean KontrolaCtverecVyskyt(final int[][] Sudoku, final int x_pozice, final int y_pozice, final int number)
    {
        int x_Ctverec = x_pozice /3;
        int y_Ctverec = y_pozice /3;

        for(int x = x_Ctverec * 3; x < (x_Ctverec * 3) + 3; x++)
        {
            for(int y = y_Ctverec * 3; y < (y_Ctverec * 3) + 3; y++)
            {
                if((x != x_pozice || y != y_pozice) && number == Sudoku[x][y])
                {
                    return true;
                }
            }
        }

        return false;
    }
}
