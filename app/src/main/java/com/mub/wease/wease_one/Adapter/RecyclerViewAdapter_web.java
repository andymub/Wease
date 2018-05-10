package com.mub.wease.wease_one.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mub.wease.wease_one.Data.Upload;
import com.mub.wease.wease_one.R;

import java.util.List;
/**
 * Created by Andymub on 04/03/2018.
 */
public class RecyclerViewAdapter_web extends RecyclerView.Adapter<RecyclerViewAdapter_web.ViewHolder> implements RecyclerView.OnItemTouchListener {


    Context context;
    List<Upload> MainImageUploadInfoList;

    private ScaleGestureDetector mScaleGestureDetector;

    private float mScaleFactor = 1.0f;
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

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            mScaleGestureDetector.onTouchEvent(e);

    }


    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView imageNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);

            imageNameTextView = itemView.findViewById(R.id.ImageNameTextView);

        }
        private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector){

                mScaleFactor *= scaleGestureDetector.getScaleFactor();

                mScaleFactor = Math.max(0.1f,

                        Math.min(mScaleFactor, 10.0f));

                imageView.setScaleX(mScaleFactor);

                imageView.setScaleY(mScaleFactor);

                return true;

            }
        }

    }


}