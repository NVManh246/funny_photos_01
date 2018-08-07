package com.example.ginz.funnyphoto.screen.post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ginz.funnyphoto.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private static Context mContext;
    private static List<String> mPhotos;

    public PhotoAdapter(Context context, List<String> photos) {
        mContext = context;
        mPhotos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ViewHolder(inflater.inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mPhotos != null ? mPhotos.size() : 0;
    }

    public void showPhotos(List<String> photos){
        mPhotos = photos;
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImagePhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            mImagePhoto = itemView.findViewById(R.id.image_photo);
        }

        private void bindView(int position){
            String photoPath = mPhotos.get(position);
            Picasso.with(mContext).load(new File(photoPath)).into(mImagePhoto);
        }
    }
}
