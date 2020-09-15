package com.glorysys.boursetoolbox.main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.DataBase.DataBaseHelper;
import com.glorysys.boursetoolbox.main.Models.NamadsDataSample;

import java.text.DecimalFormat;
import java.util.List;

public class NamadsAdapter extends RecyclerView.Adapter<NamadsAdapter.NamadViewHolder> {
    private List<NamadsDataSample> namadsDataSampleList;
    int i = 0;
    private Context context;
    private NamadViewHolder.onNamadsClick onNamadsClick;
    public NamadsAdapter(List<NamadsDataSample> namadsDataSampleList1,NamadViewHolder.onNamadsClick onNamadsClick) {
        this.namadsDataSampleList = namadsDataSampleList1;
        this.onNamadsClick=onNamadsClick;
    }

    @NonNull
    @Override
    public NamadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.namads_simple_data_layout, parent, false);

        return new NamadViewHolder(view,onNamadsClick);
    }

    @Override
    public void onBindViewHolder(@NonNull final NamadViewHolder holder, final int position) {
        holder.bindNamads(namadsDataSampleList.get(position));
        holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_fade_transition));


    }



    @Override
    public int getItemCount() {
        return namadsDataSampleList.size();
    }

    public void filter(List<NamadsDataSample> filterlist) {
        namadsDataSampleList = filterlist;
        this.notifyDataSetChanged();

    }


    public static class NamadViewHolder extends RecyclerView.ViewHolder {
        private LottieAnimationView button_FAV;
        DataBaseHelper dataBaseHelper;
        private TextView TV_NamadName;
        private TextView TV_NamadCompanyName;
        private TextView TV_PE;
        private TextView TV_EPS;
        private TextView TV_LastDeal;
        private TextView TV_LastDealPercent;
        private TextView TV_LastPrice;
        private ConstraintLayout constraintLayout;
        private TextView TV_LastPricePercent;
        private onNamadsClick onNamadsClick;
        public NamadViewHolder(@NonNull View itemView,onNamadsClick onNamadsClick) {
            super(itemView);
            this.onNamadsClick=onNamadsClick;
            constraintLayout = itemView.findViewById(R.id.cl_namadsimple_clanim);
            TV_NamadName = itemView.findViewById(R.id.textView_namadsimple_namadname);
            TV_NamadCompanyName = itemView.findViewById(R.id.textView_namadsimple_companyName);
            TV_EPS = itemView.findViewById(R.id.textView_namadsimple_date);
            TV_PE = itemView.findViewById(R.id.textView_namadsimple_number);
            TV_LastDeal = itemView.findViewById(R.id.textView_namadsimple_currentprice);
            TV_LastDealPercent = itemView.findViewById(R.id.textView_namadsimple_Profit);
            TV_LastPrice = itemView.findViewById(R.id.textView_namadsimple_buyprice);
            TV_LastPricePercent = itemView.findViewById(R.id.textView_namadsimple_lastPricePercent);
            button_FAV = itemView.findViewById(R.id.button_namadSimple_FAV);


        }

        public void bindNamads(final NamadsDataSample namadsDataSample) {
             dataBaseHelper=new DataBaseHelper(itemView.getContext());
            TV_NamadName.setText(namadsDataSample.getNamadName());
            TV_NamadCompanyName.setText(namadsDataSample.getNamadCompanyName());
            TV_NamadCompanyName.setSelected(true);
            TV_LastPricePercent.setText(String.valueOf(namadsDataSample.getLastPricePercentage()) + " %");
            TV_LastPrice.setText(String.valueOf(namadsDataSample.getLastPriceAmount()));
            TV_LastDealPercent.setText(String.valueOf(namadsDataSample.getLastDealPercentage()) + " %");
            TV_LastDeal.setText(String.valueOf(namadsDataSample.getLastDealPriceAmount()));
            TV_PE.setText(String.valueOf(namadsDataSample.getPE()));
            TV_EPS.setText(String.valueOf(namadsDataSample.getEPS()));
            parsedata();
            if (dataBaseHelper.Exists_Portfolio(namadsDataSample.getNamadName())){
                button_FAV.playAnimation();
            }else{
                button_FAV.setFrame(0);
            }
            button_FAV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dataBaseHelper.Exists_Portfolio(TV_NamadName.getText().toString())){
                        button_FAV.setFrame(0);
                        dataBaseHelper.DB_Portfolio(TV_NamadName.getText().toString());
                    }else{
                        button_FAV.playAnimation();
                        dataBaseHelper.DB_Portfolio(TV_NamadName.getText().toString());
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNamadsClick.click(namadsDataSample);
                }
            });
        }

        public void parsedata() {
            if (TV_EPS.getText().toString() == "null") {
                TV_EPS.setText("نامشخص");
                TV_EPS.setTextColor(itemView.getResources().getColor(R.color.red_400));
                TV_EPS.setTextSize(14);
            } else {
                TV_EPS.setTextColor(itemView.getResources().getColor(R.color.white));
                TV_EPS.setTextSize(18);
            }
            if (TV_PE.getText().toString() == "null") {
                TV_PE.setText("نامشخص");
                TV_PE.setTextSize(14);
                TV_PE.setTextColor(itemView.getResources().getColor(R.color.red_400));
            } else {
                TV_PE.setTextSize(18);
                TV_PE.setTextColor(itemView.getResources().getColor(R.color.white));

            }

            String twoFirstCharacters = TV_LastPricePercent.getText().toString().substring(0, 1);
            if (twoFirstCharacters.equals("-")) {
                String finaltext = TV_LastPricePercent.getText().toString().substring(TV_LastPricePercent.getText().toString().lastIndexOf("-") + 1);
                TV_LastPricePercent.setTextColor(itemView.getResources().getColor(R.color.red_400));
                TV_LastPricePercent.setText(finaltext);
            } else {
                TV_LastPricePercent.setTextColor(itemView.getResources().getColor(R.color.green_400));
            }

            String twoFirstCharacters1 = TV_LastDealPercent.getText().toString().substring(0, 1);
            if (twoFirstCharacters1.equals("-")) {
                String finaltext = TV_LastDealPercent.getText().toString().substring(TV_LastDealPercent.getText().toString().lastIndexOf("-") + 1);
                TV_LastDealPercent.setTextColor(itemView.getResources().getColor(R.color.red_400));
                TV_LastDealPercent.setText(finaltext);
            } else {
                TV_LastDealPercent.setTextColor(itemView.getResources().getColor(R.color.green_400));
            }
////////////////////
            DecimalFormat decimalFormat=new DecimalFormat("#,###");
            long lastdeal=Long.parseLong(TV_LastDeal.getText().toString());
            TV_LastDeal.setText(decimalFormat.format(lastdeal));
            long lastprice=Long.parseLong(TV_LastPrice.getText().toString());
            TV_LastPrice.setText(decimalFormat.format(lastprice));

        }

        public interface onNamadsClick {
            void click(NamadsDataSample namadsDataSample);
        }


    }



}
