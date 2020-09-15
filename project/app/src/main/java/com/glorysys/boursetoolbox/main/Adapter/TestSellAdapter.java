package com.glorysys.boursetoolbox.main.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Models.TestBuyDataModel;

import java.util.List;

public class TestSellAdapter extends RecyclerView.Adapter<TestSellAdapter.TestSellViewHolder> {
    List<TestBuyDataModel> dataModelList;
    private TestSellViewHolder.onClickListener onClickListener;
    public TestSellAdapter(List<TestBuyDataModel> dataModelList,TestSellViewHolder.onClickListener onClickListener) {
        this.dataModelList = dataModelList;
        this.onClickListener=onClickListener;
    }


    @NonNull
    @Override
    public TestSellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.testsell_recyclerview_model,parent,false);
        return new TestSellViewHolder(view,onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TestSellViewHolder holder, int position) {
    holder.onBind(dataModelList.get(position));
        holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_fade_transition));

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public static class TestSellViewHolder extends RecyclerView.ViewHolder{
        TextView name, coName, date, buyprice, soldPrice, tedad, profit,soldDate;
        private onClickListener onClickListener;
        private ConstraintLayout constraintLayout;
        public TestSellViewHolder(@NonNull View itemView, final onClickListener onClickListener) {
            super(itemView);
            this.onClickListener=onClickListener;
            constraintLayout=itemView.findViewById(R.id.cl_testsellmodel_clmaim);
            name=itemView.findViewById(R.id.textView_testsellmodel_namadname);
            coName=itemView.findViewById(R.id.textView_testsellmodel_companyName);
            date=itemView.findViewById(R.id.textView_testsellmodel_date);
            buyprice=itemView.findViewById(R.id.textView_testsellmodel_buyprice);
            soldPrice=itemView.findViewById(R.id.textView_testsellmodel_soldprice);
            soldDate=itemView.findViewById(R.id.textView_testsellmodel_soldDate);
            tedad=itemView.findViewById(R.id.textView_testsellmodel_number);
            profit=itemView.findViewById(R.id.textView_testsellmodel_Profit);

        }

        public void onBind(final TestBuyDataModel dataModel){
            name.setText(dataModel.getNamadName());
            coName.setText(dataModel.getNamadCompanyName());
            coName.setSelected(true);
            date.setText(dataModel.getDate());
            buyprice.setText(String.valueOf(dataModel.getBuyprice()));
            soldPrice.setText(String.valueOf(dataModel.getSoldPrice()));
            soldDate.setText(dataModel.getSoldDate());
            tedad.setText(String.valueOf(dataModel.getTedad()));
            profit.setText(String.valueOf(dataModel.getProfit()));
            textSetting();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(dataModel);
                }
            });
        }

        private void textSetting() {
            Double d = Double.valueOf(profit.getText().toString());

            if (d > 0) {
                profit.setText( profit.getText().toString() + " %");
                profit.setTextColor(itemView.getResources().getColor(R.color.green_400));
            }
            if (d < 0) {

                String twoFirstCharacters = profit.getText().toString().substring(0, 1);

                if (twoFirstCharacters.equals("-")) {
                    String finaltext = profit.getText().toString().substring(profit.getText().toString().lastIndexOf("-") + 1);
                    profit.setTextColor(itemView.getResources().getColor(R.color.red_400));

                    profit.setText(finaltext +" %");
                }
            }
        }

        public interface onClickListener{
            void onClick(TestBuyDataModel dataModel);
      }
    }

}
