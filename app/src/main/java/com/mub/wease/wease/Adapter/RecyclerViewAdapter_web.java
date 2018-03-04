package com.mub.wease.wease.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mub.wease.wease.Data.Upload;
import com.mub.wease.wease.R;

import java.util.List;
/**
 * Created by Andymub on 04/03/2018.
 */
public class RecyclerViewAdapter_web extends RecyclerView.Adapter<RecyclerViewAdapter_web.ViewHolder> {

    Context context;
    List<Upload> MainImageUploadInfoList;

    public RecyclerViewAdapter_web(Context context, List<Upload> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_web_recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerViewAdapter_web.ViewHolder holder, int position) {
        Upload UploadInfo = MainImageUploadInfoList.get(position);

        holder.imageNameTextView.setText(UploadInfo.getName());

        //Loading image from Glide library.
        Glide.with(context).load(UploadInfo.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView imageNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            imageNameTextView = (TextView) itemView.findViewById(R.id.ImageNameTextView);
        }
    }
}