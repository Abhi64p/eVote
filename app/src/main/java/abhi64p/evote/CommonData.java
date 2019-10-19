package abhi64p.evote;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

public class CommonData
{
    @NonNull
    static AppDatabase OpenDatabase(Context context)
    {
        return Room.databaseBuilder(context, AppDatabase.class, "mobile_app")
                .build();
    }
}
