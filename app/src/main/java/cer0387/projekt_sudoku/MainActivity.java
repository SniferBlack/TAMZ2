package cer0387.projekt_sudoku;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[][] Sudoku = SudokuGenerator.vratInstanci().generujCisla();
        PrintConsoleSudoku(Sudoku);
    }

    private void PrintConsoleSudoku(int[][] Sudoku)
    {
        for(int y = 0; y <9; y++)
        {
            for(int x = 0; x <9; x++)
            {
                if(x!=8)
                    System.out.print(Sudoku[x][y] + "|");
                else
                    System.out.print(Sudoku[x][y]);
            }
            System.out.println();
        }
    }
}
