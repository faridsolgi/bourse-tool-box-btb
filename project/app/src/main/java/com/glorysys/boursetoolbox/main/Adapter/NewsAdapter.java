package com.glorysys.boursetoolbox.main.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Models.NewsDataModel;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    List<NewsDataModel> newsDataModelList =new ArrayList<>();
    private NewsViewHolder.onNewsClickListener onNewsClickListener;
    private static final String TAG = "NewsAdapter";
    public NewsAdapter(List<NewsDataModel> newsDataModelList,NewsViewHolder.onNewsClickListener onNewsClickListener) {
        this.newsDataModelList=newsDataModelList;
       this.onNewsClickListener=onNewsClickListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.news_recyclerview_model,parent,false);
        return new NewsViewHolder(view,onNewsClickListener) ;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
    holder.onBindNews(newsDataModelList.get(position));
    holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_fade_transition));

    }

    @Override
    public int getItemCount() {
        return newsDataModelList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout constraintLayout;
        private TextView TV_title;
        private TextView TV_description;
        private String TV_url;
        private ImageView IV_newsImageView;
        private onNewsClickListener onNewsClickListener;
        public NewsViewHolder(@NonNull View itemView,onNewsClickListener onNewsClickListener) {
            super(itemView);
            this.onNewsClickListener=onNewsClickListener;
            constraintLayout=itemView.findViewById(R.id.cl_NewsModel_cl);
            TV_title=itemView.findViewById(R.id.textView_NewsModel_title);
            TV_description=itemView.findViewById(R.id.textView_NewsModel_description);
            IV_newsImageView=itemView.findViewById(R.id.imageView_NewsModel_NewsImage);
        }
        public void onBindNews(final NewsDataModel newsDataModel){
            TV_title.setText(newsDataModel.getTitle());
            TV_description.setText(newsDataModel.getDescription());
            Transformation transformation = new RoundedTransformationBuilder()
                    .oval(false)
                    .build();
           // Picasso.get().load(newsDataModel.getImgurl()).into(IV_newsImageView);
           if(newsDataModel.getImgurl().equals("")){
                Picasso.get()
                        .load(R.drawable.erorr404)
                        .fit()
                        .transform(transformation)
                        .into(IV_newsImageView);
                Log.d(TAG, "onBindNews:");
            }else{
                Picasso.get()
                        .load(newsDataModel.getImgurl())
                        .fit()
                        .transform(transformation)
                        .error(R.drawable.erorr404)
                        .into(IV_newsImageView);
                Log.d(TAG, "onelse:");
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNewsClickListener.onNewsClick(newsDataModel);
                }
            });

        }
        public interface onNewsClickListener{
            void onNewsClick(NewsDataModel newsDataModel);
        }
    }


}
