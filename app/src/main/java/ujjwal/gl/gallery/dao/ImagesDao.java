package ujjwal.gl.gallery.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ujjwal.gl.gallery.entities.Images;

@Dao
public interface ImagesDao {

    @Insert
    public void insertAll(Images... images);

    @Query("Select * from images")
    public List<Images> getAllData();


}
