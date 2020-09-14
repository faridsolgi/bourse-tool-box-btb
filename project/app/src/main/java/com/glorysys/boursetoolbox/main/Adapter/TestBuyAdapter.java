package com.glorysys.boursetoolbox.main.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Models.TestBuyDataModel;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class TestBuyAdapter extends RecyclerView.Adapter<TestBuyAdapter.TestBuyViewHolder> {
    List<TestBuyDataModel> testBuyDataModelList;
    private TestBuyViewHolder.onclickListener onclickListener;
    private static final String TAG = "TestBuyAdapter";
    public TestBuyAdapter(List<TestBuyDataModel> testBuyDataModelList, TestBuyViewHolder.onclickListener onclickListener) {
        this.testBuyDataModelList = testBuyDataModelList;
        this.onclickListener = onclickListener;
    }


    @NonNull
    @Override
    public TestBuyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.testbuy_recyvlerview_model, parent, false);
        return new TestBuyViewHolder(view, onclickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull TestBuyViewHolder holder, int position) {
        holder.onbind(testBuyDataModelList.get(position));
        holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_fade_transition));

    }

    @Override
    public int getItemCount() {
        return testBuyDataModelList.size();
    }

    public static class TestBuyViewHolder extends RecyclerView.ViewHolder {
        TextView name, coName, date, buyprice, currentPrice, tedad, profit;
        private onclickListener onclickListener;
        private ConstraintLayout constraintLayout;

        public TestBuyViewHolder(@NonNull View itemView, onclickListener onclickListener) {
            super(itemView);
            this.onclickListener = onclickListener;
            constraintLayout=itemView.findViewById(R.id.cl_buytestmodel_clanim);
            name = itemView.findViewById(R.id.textView_buytestmodel_namadname);
            coName = itemView.findViewById(R.id.textView_buytestmodel_companyName);
            date = itemView.findViewById(R.id.textView_buytestmodel_date);
            buyprice = itemView.findViewById(R.id.textView_buytestmodel_buyprice);
            currentPrice = itemView.findViewById(R.id.textView_buytestmodel_currentprice);
            tedad = itemView.findViewById(R.id.textView_buytestmodel_number);
            profit = itemView.findViewById(R.id.textView_buytestmodel_Profit);
        }


        public void onbind(final TestBuyDataModel testBuyDataModel) {
            name.setText(testBuyDataModel.getNamadName());
            coName.setText(testBuyDataModel.getNamadCompanyName());
            coName.setSelected(true);
            date.setText(testBuyDataModel.getDate());
            buyprice.setText(String.valueOf(testBuyDataModel.getBuyprice()));
            currentPrice.setText(String.valueOf(testBuyDataModel.getLastPriceAmount()));
            tedad.setText(String.valueOf(testBuyDataModel.getTedad()));
            profit.setText(String.valueOf(testBuyDataModel.getProfit()));
            textsetting();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclickListener.onClick(testBuyDataModel);
                }
            });
        }

        private void textsetting() {
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            Double d = Double.valueOf(profit.getText().toString());

            if (d > 0) {
                String formated=df.format(d);
                profit.setText(formated + " %");
                profit.setTextColor(itemView.getResources().getColor(R.color.green_400));
            }
            if (d < 0) {

                String twoFirstCharacters = profit.getText().toString().substring(0, 1);

                if (twoFirstCharacters.equals("-")) {
                    String finaltext = profit.getText().toString().substring(profit.getText().toString().lastIndexOf("-") + 1);
                    double f=Double.valueOf(finaltext);
                    profit.setTextColor(itemView.getResources().getColor(R.color.red_400));
                    String formated=df.format(f);
                    profit.setText(formated +" %");
                }
            }





        }

        public interface onclickListener {
            void onClick(TestBuyDataModel testBuyDataModel);

        }
    }
}
