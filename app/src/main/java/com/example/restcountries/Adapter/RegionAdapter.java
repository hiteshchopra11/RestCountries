package com.example.restcountries.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restcountries.Models.Language;
import com.example.restcountries.Models.Region;
import com.example.restcountries.R;
import com.example.restcountries.RegionDetailsActivity;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Region> mRegion;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private Context context;
    public View v2;

    public RegionAdapter(Context context) {
        this.context = context;
        mRegion = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                v2 = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.no_results, parent, false);
                viewHolder = new NoResultVH(v3);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list, parent, false);
        viewHolder = new RegionViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Region result = mRegion.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final RegionViewHolder githubViewHolder = (RegionViewHolder) holder;
                githubViewHolder.mNameTextView.setText("" + result.getName());
                githubViewHolder.mCapitalTextView.setText("Capital-: " + result.getCapital());
                GlideToVectorYou.init().with(context).load(Uri.parse(mRegion.get(position).getFlag()), ((RegionViewHolder) holder).mImageView);
                githubViewHolder.lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = result.getName();
                        String capital = result.getCapital();
                        String region = result.getRegion();
                        String subRegion = result.getSubregion();
                        int population = result.getPopulation();
                        List<String> border = result.getBorders();
                        List<Language> languages = result.getLanguages();
                        ArrayList<String> lang = new ArrayList<>();
                        for (int i = 0; i < languages.size(); i++) {
                            lang.add(languages.get(i).getName());
                        }
                        Intent intent = new Intent(context, RegionDetailsActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("NAME", (Serializable) name);
                        args.putSerializable("CAPITAL", (Serializable) capital);
                        args.putSerializable("LANGUAGES", (Serializable) lang);
                        args.putSerializable("BORDERS", (Serializable) border);
                        args.putSerializable("POPULATION", (Serializable) population);
                        args.putSerializable("REGION", (Serializable) region);
                        args.putSerializable("SUBREGION", (Serializable) subRegion);
                        intent.putExtras(args);
                        intent.putExtra("BUNDLE", args);
                        context.startActivity(intent);
                    }
                });
                break;
            case LOADING:
        }
    }

    @Override
    public int getItemCount() {
        return mRegion.size();
    }


    public void add(Region g) {
        mRegion.add(g);
        notifyItemInserted(mRegion.size() - 1);
    }

    public void addAll(List<Region> listRegion) {
        for (Region result : listRegion) {
            add(result);
        }
    }

    public static class RegionViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mNameTextView;
        TextView mCapitalTextView;
        View lay;

        public RegionViewHolder(@NonNull View itemView) {
            super(itemView);
            lay = itemView.getRootView().findViewById(R.id.lay);
            mImageView = itemView.findViewById(R.id.image);
            mNameTextView = itemView.findViewById(R.id.name);
            mCapitalTextView = itemView.findViewById(R.id.capital);
        }
    }

    public class LoadingVH extends RecyclerView.ViewHolder {
        public LoadingVH(View itemView) {
            super(itemView);
            v2.setVisibility(View.GONE);
        }
    }

    protected static class NoResultVH extends RecyclerView.ViewHolder {
        public NoResultVH(View itemView) {
            super(itemView);
        }
    }
}
