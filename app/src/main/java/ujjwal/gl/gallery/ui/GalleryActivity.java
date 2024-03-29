package ujjwal.gl.gallery.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
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

    ImageButton mOpenFile, mHistory;
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
        mHistory.setOnClickListener(this);

        mGrid.setAdapter(mAdapter);
    }

    private void initViews() {
        mOpenFile = findViewById(R.id.open_file);
        mHistory = findViewById(R.id.history);
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
        if(view == mHistory){
            AlertDialog.Builder builder = new AlertDialog.Builder(GalleryActivity.this);

            builder.setMessage(getData())
                    .setTitle("History");

            AlertDialog dialog = builder.create();

            dialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri imageUri = data.getData();

            String filename = imageUri.toString().substring(imageUri.toString().lastIndexOf("/")+1);

            Images image = new Images(filename,imageUri.toString());

            mImages.add(image);
            mDatabase.imagesDao().insertAll(image);

            Toast.makeText(getApplicationContext(),"Total "+String.valueOf(mImages.size()),Toast.LENGTH_LONG).show();
            mAdapter.notifyDataSetChanged();
        }
    }

    private String getData(){
        List<Images> data = mDatabase.imagesDao().getAllData();
        StringBuilder stringBuilder = new StringBuilder();
        for(Images i : data){
            stringBuilder.append(i.id).append(" : ").append(i.name).append("\n\n");
        }
        return stringBuilder.toString();
    }
}
