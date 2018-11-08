package cer0387.projekt_sudoku;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDao playerDao();
    private static volatile AppDatabase INSTANCE;
    static AppDatabase getDatabase(final Context context){
        if(INSTANCE==null)
        {
            synchronized (AppDatabase.class){
                if(INSTANCE==null)
                {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"player_database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
