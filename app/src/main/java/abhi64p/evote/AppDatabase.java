package abhi64p.evote;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

@Database(entities = {vote.class}, version = 1,exportSchema = false)
abstract class AppDatabase extends RoomDatabase
{
    public abstract UserDao userDao();
}

@Entity
class vote
{
    @PrimaryKey @NonNull
    String Aadhar = "";
    String Vote = "";
}


@Dao
interface UserDao
{
    @Insert
    void AddVote(vote v) ;

    @Query("SELECT * FROM vote WHERE Aadhar = :A")
    vote GetVote(String A);

    @Query("SELECT * FROM vote")
    List<vote> GetAllVote();
}


