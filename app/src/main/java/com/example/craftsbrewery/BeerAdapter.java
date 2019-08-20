package com.example.craftsbrewery;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder> {

    private Context mCtx;
    private List<Beer> beerList;

    public BeerAdapter(Context mCtx, List<Beer> beerList)
    {
        this.mCtx = mCtx;
        this.beerList = beerList;
    }

    @NonNull
    @Override
    public BeerAdapter.BeerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflator = LayoutInflater.from(mCtx);
        View view = inflator.inflate(R.layout.item_display, null);
        return new BeerViewHolder(view);
    }

    public void onBindViewHolder(BeerViewHolder holder, int position)
    {
        Beer beer = beerList.get(position);

        //binding the data with the viewholder views
        holder.textViewBeerName.setText(beer.getName());
        holder.textViewStyle.setText(beer.getStyle());
        holder.textViewAlcohol.setText(beer.getAbv());
    }

    public int getItemCount() {
        return beerList.size();
    }


    class BeerViewHolder extends RecyclerView.ViewHolder{
        TextView textViewBeerName, textViewStyle, textViewAlcohol;
        ImageView beerImage, addImage;

        public BeerViewHolder(View itemview)
        {
            super(itemview);

            textViewAlcohol = itemview.findViewById(R.id.textView_alcohol_content);
            textViewBeerName = itemview.findViewById(R.id.textView_BeerName);
            textViewStyle = itemview.findViewById(R.id.textView_style);
        }

    }
}
