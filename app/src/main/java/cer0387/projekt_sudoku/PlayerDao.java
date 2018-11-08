package cer0387.projekt_sudoku;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PlayerDao {
    @Query("Select * From player")
    List<Player> getAll();
    @Query("Select * From player Where id=(:playerId)")
    Player findOneById(int playerId);
    @Insert
    void insert(Player player);
    @Update
    void update(Player player);
    @Delete
    void delete(Player player);
}
