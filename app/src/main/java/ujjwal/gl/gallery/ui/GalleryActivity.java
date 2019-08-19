package ujjwal.gl.gallery.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ujjwal.gl.gallery.R;
import ujjwal.gl.gallery.adapter.ImagesAdapter;
import ujjwal.gl.gallery.database.Database;
import ujjwal.gl.gallery.entities.Images;

import static ujjwal.gl.gallery.constants.Constants.PICKFILE_REQUEST_CODE;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton mOpenFile;
    RecyclerView mGrid;
    Database mDatabase;
    List<Images> mImages;
    ImagesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();

        initViews();

        mOpenFile.setOnClickListener(this);

        mGrid.setAdapter(mAdapter);
    }

    private void initViews() {
        mOpenFile = findViewById(R.id.open_file);
        mGrid = findViewById(R.id.image_grid);
        mDatabase = Database.getInstance(getApplicationContext());
        mImages = new ArrayList<>();
        mAdapter = new ImagesAdapter(mImages,getApplicationContext());

        mGrid.hasFixedSize();
        mGrid.setLayoutManager(new GridLayoutManager(this,3));
    }

    @Override
    public void onClick(View view) {
        if(view == mOpenFile){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpg");
            startActivityForResult(intent, PICKFILE_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri imageUri = data.getData();

            Images image = new Images("image",imageUri.toString());

            mImages.add(image);
            mDatabase.imagesDao().insertAll(image);

            Toast.makeText(getApplicationContext(),"Total "+String.valueOf(mImages.size()),Toast.LENGTH_LONG).show();
            mAdapter.notifyDataSetChanged();
        }
    }
}
