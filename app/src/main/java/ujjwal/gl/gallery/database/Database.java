package ujjwal.gl.gallery.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import ujjwal.gl.gallery.dao.ImagesDao;
import ujjwal.gl.gallery.entities.Images;

import static ujjwal.gl.gallery.constants.Constants.DATABASE_NAME;
import static ujjwal.gl.gallery.constants.Constants.DATABASE_VERSION;

@androidx.room.Database(entities = Images.class,version = DATABASE_VERSION)
public abstract class Database extends RoomDatabase {

    public static Database mInstance;

    public static Database getInstance(Context context){
        if(mInstance==null){
            mInstance = Room.databaseBuilder(context,Database.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return mInstance;
    }

    public abstract ImagesDao imagesDao();

}
