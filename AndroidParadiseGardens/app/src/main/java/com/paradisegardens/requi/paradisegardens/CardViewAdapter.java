package com.paradisegardens.requi.paradisegardens;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static android.graphics.Matrix.*;

/**
 * A simple adapter that loads a CardView layout with one TextView and two Buttons, and
 * listens to clicks on the Buttons or on the CardView
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
    private List<String> cards;
    private List<String> imgs;


    public CardViewAdapter(List<String> cards, List<String> imgs) {
        this.cards = cards;
        this.imgs = imgs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.title.setText(cards.get(i));
        if(i < imgs.size())
            new DownloadImageTask(viewHolder.image,viewHolder.title).execute(imgs.get(i));
    }

    @Override
    public int getItemCount() {
        return cards == null ? 0 : cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.description);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }


    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        TextView textView;

        public DownloadImageTask(ImageView bmImage, TextView txtView) {
            this.bmImage = bmImage;
            textView = txtView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

            //measure the height using the img's draw matrix
            float[] f = new float[9];
            bmImage.getImageMatrix().getValues(f);

            // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
            final float scaleX = f[MSCALE_X];
            final float scaleY = f[MSCALE_Y];

            // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
            final Drawable d = bmImage.getDrawable();
            final int origH = d.getIntrinsicHeight();

            // Calculate the actual dimensions
            final int height = Math.round(origH * scaleY);


            //pad the text
            textView.setPadding(15, height, 15, 0);
        }
    }
}