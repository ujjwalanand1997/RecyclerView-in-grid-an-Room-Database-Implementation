package ujjwal.gl.gallery.adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ujjwal.gl.gallery.R;
import ujjwal.gl.gallery.database.Database;
import ujjwal.gl.gallery.entities.Images;
import ujjwal.gl.gallery.ui.GalleryActivity;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    List<Images> images;
    Context context;

    public ImagesAdapter(List<Images> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.im_grid_layout,null);
        ImagesViewHolder imagesViewHolder = new ImagesViewHolder(view);
        return imagesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, final int position) {

        holder.imageView.setImageURI(null);
        holder.imageView.setImageURI(Uri.parse(images.get(position).getImageUri()));
        holder.name.setText(String.valueOf(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Image "+position,Toast.LENGTH_LONG).show();
//                Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.im_grid_layout);
//                dialog.setTitle("Title...");
//
//                // set the custom dialog components - text, image and button
//                TextView text = dialog.findViewById(R.id.name);
//                text.setText(String.valueOf(position));
//                ImageView image = dialog.findViewById(R.id.imported_image);
//                image.setImageURI(Uri.parse(images.get(position).getImageUri()));
//
//                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ImagesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imported_image);
            name = itemView.findViewById(R.id.name);
        }
    }
}
