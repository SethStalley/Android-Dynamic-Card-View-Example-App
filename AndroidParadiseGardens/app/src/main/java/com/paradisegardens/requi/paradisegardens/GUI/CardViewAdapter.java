package com.paradisegardens.requi.paradisegardens.GUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.support.v7.widget.RecyclerView;

import com.paradisegardens.requi.paradisegardens.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple adapter that loads a CardView layout with one TextView and two Buttons, and
 * listens to clicks on the Buttons or on the CardView
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
    private List<String> cards;
    private List<String> imgs;
    int position; //know which tab we are on\
    Main main; //instance of main activity

    public CardViewAdapter(Main main,List<String> cards, List<String> imgs, int position) {
        this.cards = cards;
        this.imgs = imgs;
        this.position = position;
        this.main = main;
    }

    //if we are just showing products
    public CardViewAdapter(ArrayList <String> cards){
        this.cards = cards;
        this.imgs = new ArrayList<>();
        this.position = 5;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        String description = cards.get(i);
        String[] msg = description.split("\\r?\\n");

        viewHolder.name.setText(msg[0].toString());
        String text = "";
        for(int j = 1 ; j < msg.length; j++) {
            if(msg[j] != "null\n")
                text += msg[j] + "\n";
        }
        viewHolder.description.setText(text);

        if(i < imgs.size())
            new DownloadImageTask(viewHolder).execute(imgs.get(i));
    }

    @Override
    public int getItemCount() {
        return cards == null ? 0 : cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView description;
        private ImageView image;
        private RelativeLayout loading, cardsView;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            image = (ImageView) itemView.findViewById(R.id.image);
            loading = (RelativeLayout) itemView.findViewById(R.id.loadingPanel);
            cardsView = (RelativeLayout) itemView.findViewById(R.id.cardsVew);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked
                    TextView text = (TextView) itemView.findViewById(R.id.name);
                    String name = text.getText().toString();
                    Log.i("Card Press", name);

                    //if we are a restaurant or store tab then we can show items
                    if(position == 2 || position == 3){
                        if(position==2)
                            position = 4;
                        else
                            position = 5;
                        Intent myIntent=new Intent(main, Products.class);
                        myIntent.putExtra("name", name);
                        myIntent.putExtra("position", position);
                        main.startActivity(myIntent);
                        main.finish();
                    }
                }
            });
        }
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ViewHolder viewHolder;

        public DownloadImageTask(ViewHolder viewHolder) {
            this.viewHolder = viewHolder;
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
            viewHolder.image.setImageBitmap(result);

            viewHolder.image.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            // Remove after the first run so it doesn't fire forever
                            viewHolder.image.getViewTreeObserver().removeOnPreDrawListener(this);
                            int height = viewHolder.image.getMeasuredHeight();

                            //Log.i("Image Height is:", Float.toString(height));

                            //pad the text
                            viewHolder.name.setPadding(15, height, 15, 0);
                            height += viewHolder.name.getHeight();
                            viewHolder.description.setPadding(15, height, 15, 0);
                            viewHolder.loading.setVisibility(View.GONE);
                            viewHolder.cardsView.setVisibility(View.VISIBLE);
                            return  true;
                        }
                    });
        }
    }
}