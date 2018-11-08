package cer0387.projekt_sudoku;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

public class PlayerRepository {
    private PlayerDao mPlayerDao;
    private List<Player> mAllPlayers;

    PlayerRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
        mAllPlayers = mPlayerDao.getAll();
    }

    List<Player> getmAllPlayers()
    {
        return mAllPlayers;
    }
    public void insert(Player player)
    {
        //mPlayerDao.insert(player);
        new insertAssyncTask(mPlayerDao).execute(player);
    }
    public Player findOneById(int id)
    {
        return mPlayerDao.findOneById(id);
    }
    public void update(Player player)
    {
        mPlayerDao.update(player);
    }
    public void delete(Player player)
    {
        mPlayerDao.delete(player);
    }

    private static class insertAssyncTask extends AsyncTask<Player,Void,Void>
    {
        private PlayerDao mAsyncTaskDao;

        insertAssyncTask(PlayerDao dao)
        {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Player... players) {
            mAsyncTaskDao.insert(players[0]);
            return null;
        }
    }
}
