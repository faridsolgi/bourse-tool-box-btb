package com.glorysys.boursetoolbox.main.Adapter;

import android.os.CountDownTimer;
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
import com.glorysys.boursetoolbox.main.Models.AparatDataModel;
import com.glorysys.boursetoolbox.main.Models.NamadsDataSample;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class AparatAdapter extends RecyclerView.Adapter<AparatAdapter.AparatViewHolder> {
    List<AparatDataModel> aparatDataModelList;
    private AparatViewHolder.setOnVideosClickListener setOnVideosClickListener;
    public AparatAdapter(List<AparatDataModel> aparatDataModelList,AparatViewHolder.setOnVideosClickListener setOnVideosClickListener) {
        this.setOnVideosClickListener=setOnVideosClickListener;
        this.aparatDataModelList = aparatDataModelList;
    }

    @NonNull
    @Override
    public AparatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.aparat_recyclerview_model,parent,false);
        return new AparatViewHolder(view,setOnVideosClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AparatViewHolder holder, int position) {
        holder.onBind(aparatDataModelList.get(position));
        holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_fade_transition));

    }

    @Override
    public int getItemCount() {
        return aparatDataModelList.size();
    }

    public void filter(List<AparatDataModel> filterlist) {
        aparatDataModelList = filterlist;
        this.notifyDataSetChanged();

    }


    public static class AparatViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private setOnVideosClickListener setOnVideosClickListener;
        private TextView textView_title,textView_date,textView_visit,textView_time;
        private ConstraintLayout constraintLayout;
        public AparatViewHolder(@NonNull View itemView,setOnVideosClickListener setOnVideosClickListener) {
            super(itemView);
            this.setOnVideosClickListener=setOnVideosClickListener;
            constraintLayout=itemView.findViewById(R.id.cl_aparatModel_cl);
            imageView=itemView.findViewById(R.id.imageView_aparatModel_imageview);
            textView_title=itemView.findViewById(R.id.textview_aparatModel_title);
            textView_time=itemView.findViewById(R.id.textview_aparatModel_time);
            textView_visit=itemView.findViewById(R.id.textview_aparatModel_visitCNT);
            textView_date=itemView.findViewById(R.id.textview_aparatModel_date);

        }

        private void onBind(final AparatDataModel aparatDataModel) {
            textView_title.setText(aparatDataModel.getTitle());
            textView_date.setText(aparatDataModel.getDate());
            visitSetting(aparatDataModel.getVisit_cnt());
            time(aparatDataModel.getDuration());
            Transformation transformation = new RoundedTransformationBuilder()
                    .oval(false)
                    .build();
        try{

                Picasso.get()
                        .load(aparatDataModel.getPoster())
                        .fit()
                        .transform(transformation)
                        .error(R.drawable.erorr404)
                        .into(imageView);

            }catch (Exception e){e.getStackTrace();}

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnVideosClickListener.OnVideoClick(aparatDataModel);
            }
        });


        }


        public interface setOnVideosClickListener{
            void OnVideoClick(AparatDataModel aparatDataModel);
        }

        private void visitSetting(int visit) {
            if (visit < 1000) {
                textView_visit.setText(String.valueOf(visit));
            } else if (visit < 1000000) {
                visit=visit/1000;
                textView_visit.setText(visit + "   هزار");


            } else if (visit > 1000000) {
                visit=visit/1000000;
                textView_visit.setText(visit + "   میلیون");
            }
        }


            private void time(long ftime) {
                        long secs =  ftime ;
                        long mins = secs / 60;
                        long hours = secs / 3600;

                        secs = secs % 60;
                        mins = mins % 60;
                        hours = hours % 24;

                        textView_time.setText(hours+":"+mins+":"+secs);








        }
    }


}
