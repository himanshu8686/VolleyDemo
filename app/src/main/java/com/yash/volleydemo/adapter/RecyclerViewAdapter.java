package com.yash.volleydemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yash.volleydemo.activities.AnimeActivity;
import com.yash.volleydemo.model.Anime;
import com.yash.volleydemo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{

    private Context mContext;
    private List<Anime> mDataList;
      RequestOptions options;
    public RecyclerViewAdapter(Context mContext, List<Anime> mDataList)
    {
        this.mContext = mContext;
        this.mDataList = mDataList;

        // Request option for Glide
        options=new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.anime_row_item,parent,false);
        final MyViewHolder myViewHolder=new MyViewHolder(view);
        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, AnimeActivity.class);

                // sending data process
                intent.putExtra("anime_name",mDataList.get(myViewHolder.getAdapterPosition()).getName());
                intent.putExtra("anime_description",mDataList.get(myViewHolder.getAdapterPosition()).getDescription());
                intent.putExtra("anime_studio",mDataList.get(myViewHolder.getAdapterPosition()).getStudio());
                intent.putExtra("anime_category",mDataList.get(myViewHolder.getAdapterPosition()).getCategorie());
                intent.putExtra("anime_nb_episode",mDataList.get(myViewHolder.getAdapterPosition()).getNb_episode());
                intent.putExtra("anime_rating",mDataList.get(myViewHolder.getAdapterPosition()).getRating());
                intent.putExtra("anime_img",mDataList.get(myViewHolder.getAdapterPosition()).getImage_url());
                mContext.startActivity(intent);
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
            holder.anime_name.setText(mDataList.get(position).getName());
            holder.category.setText(mDataList.get(position).getCategorie());
            holder.rating.setText(mDataList.get(position).getRating());
            holder.studio.setText(mDataList.get(position).getStudio());

            // Load image from internet and set it into ImageView using Glide

        Glide.with(mContext).load(mDataList.get(position).getImage_url()).apply(options).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView anime_name, category  ,rating, studio;
        ImageView  thumbnail;
        LinearLayout container;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
               anime_name=itemView.findViewById(R.id.anime_name);
               category=itemView.findViewById(R.id.category);
               rating=itemView.findViewById(R.id.rating);
               studio=itemView.findViewById(R.id.studio);
               thumbnail=itemView.findViewById(R.id.thumbnail);
               container=itemView.findViewById(R.id.container);
        }
    }
}
