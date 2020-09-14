package com.glorysys.boursetoolbox.main.Fragments.TestBuy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.DataBase.DataBaseHelper;
import com.glorysys.boursetoolbox.main.Fragments.ConfirmDialogFragment;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TestBuyDetailFragment extends Fragment implements ConfirmDialogFragment.interface_confirmDialog{
    TextView textView_namadname, textView_namadCoName, textView_profit, textView_sarbsar, textView_profitPercentage, textView_number,
            textView_totalPriceDay, textView_totalpricebuy, textView_averagebuyprice, textView_dayprice, textView_date;
    Button button_sell,button_close;
    final static String TABLE_BUY = "buy_table",NAMAD_NAME = "namadname", NAMAD_COMPANY_NAME = "namad_C_name", LAST_PRICE_AMOUNT = "LastPriceAmount",
            PROFIT = "profit", BUY_PRICE = "buy", NUMBER = "number", BUY_DATE = "buydate";
    int INT_PRICE,INT_SOLD_PRICE,INT_NUMBER;
    public TestBuyDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_buy_detail, container, false);

        onCreatefunction(view);
        return view;
    }

    private void onCreatefunction(View view) {
        SetupViews(view);
        /////////////////
        getArgumentFromParent();
        final ConfirmDialogFragment confirmDialogFragment=new ConfirmDialogFragment(this);
        ////////////////////
        button_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=textView_namadname.getText().toString();
                String description=" آیا قصد فروش "+name+" دارید؟ ";
                String title= " فروش "+name+" ؟ ";
                FragmentManager fragmentManager =getFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.add(R.id.cl_main_main,confirmDialogFragment);
                transaction.addToBackStack("confirmDialogFragment");
                Bundle bundle=new Bundle();
                bundle.putString("title",title);
                bundle.putString("description",description);
                confirmDialogFragment.setArguments(bundle);
                transaction.commit();
            }
        });
        ////////////////////
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.popBackStack("testBuyDetailFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
    }

    private void SetupViews(View view) {
        textView_namadname = view.findViewById(R.id.textview_testbuydetail_namadname);
        textView_namadCoName = view.findViewById(R.id.textview_testbuydetail_namadconame);
        textView_profit = view.findViewById(R.id.textview_testbuydetail_profit);
        textView_sarbsar = view.findViewById(R.id.textview_testbuydetail_sarbsarprice);
        textView_profitPercentage = view.findViewById(R.id.textview_testbuydetail_profitpercentage);
        textView_number = view.findViewById(R.id.textview_testbuydetail_number);
        textView_totalPriceDay = view.findViewById(R.id.textview_testbuydetail_totalpriceDay);
        textView_totalpricebuy = view.findViewById(R.id.textview_testbuydetail_totalpricebuy);
        textView_averagebuyprice = view.findViewById(R.id.textview_testbuydetail_averageprice);
        textView_dayprice = view.findViewById(R.id.textview_testbuydetail_priceday);
        button_sell = view.findViewById(R.id.button_testbuydetail_sell);
        button_close=view.findViewById(R.id.button_testbuydetail_close);
        textView_date = view.findViewById(R.id.textView_testbuydetail_date);
    }

    private void getArgumentFromParent() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            try {
                textView_namadname.setText(bundle.getString(NAMAD_NAME));
                textView_namadCoName.setText(bundle.getString(NAMAD_COMPANY_NAME));
                textView_averagebuyprice.setText(String.valueOf(bundle.getInt(BUY_PRICE)));
                textView_profitPercentage.setText(String.valueOf(bundle.getDouble(PROFIT)));
                textView_number.setText(String.valueOf(bundle.getInt(NUMBER)));
                textView_dayprice.setText(String.valueOf(bundle.getLong(LAST_PRICE_AMOUNT)));
                double a = Double.valueOf(bundle.getInt(BUY_PRICE)) * 0.01;
                double b = a + Double.valueOf(bundle.getInt(BUY_PRICE));
                textView_sarbsar.setText(String.valueOf(Math.round(b)));
                ///
                int total = bundle.getInt(NUMBER) * bundle.getInt(BUY_PRICE);
                textView_totalpricebuy.setText(String.valueOf(total));
                ////
                int totalDay = Integer.valueOf(textView_number.getText().toString()) * Integer.valueOf(textView_dayprice.getText().toString());
                textView_totalPriceDay.setText(String.valueOf(totalDay));
                ///
                int profit =totalDay- total;
                textView_profit.setText(String.valueOf(profit));
                ////
                textView_date.setText(bundle.getString(BUY_DATE));
                textsetting();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }


    private void textsetting() {
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat decimalFormat=new DecimalFormat("#,###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double d = Double.valueOf(textView_profitPercentage.getText().toString());

        if (d > 0) {
            String formated = df.format(d);
            textView_profitPercentage.setText(formated + " %");
            textView_profitPercentage.setTextColor(getResources().getColor(R.color.green_400));
        }
        if (d < 0) {

            String twoFirstCharacters = textView_profitPercentage.getText().toString().substring(0, 1);

            if (twoFirstCharacters.equals("-")) {
                String finaltext = textView_profitPercentage.getText().toString().substring(textView_profitPercentage.getText().toString().lastIndexOf("-") + 1);
                double f = Double.valueOf(finaltext);
                textView_profitPercentage.setTextColor(getResources().getColor(R.color.red_400));
                String formated = df.format(f);
                textView_profitPercentage.setText(formated + " %");
            }
        }
/////////////////profit///////////////////////////
        String twoFirstCharacters = textView_profit.getText().toString().substring(0, 1);

        if (twoFirstCharacters.equals("-")) {
            String finaltext = textView_profit.getText().toString().substring(textView_profit.getText().toString().lastIndexOf("-") + 1);
            textView_profit.setTextColor(getResources().getColor(R.color.red_400));
           int finaltext1=Integer.parseInt(finaltext);
            textView_profit.setText(decimalFormat.format(finaltext1));
        }else{
            textView_profit.setTextColor(getResources().getColor(R.color.green_400));

           int sss=Integer.parseInt(textView_profit.getText().toString());
            textView_profit.setText(decimalFormat.format(sss));
        }
/////////////////////////////////
       int dayprice= Integer.parseInt(textView_dayprice.getText().toString());
        INT_SOLD_PRICE=dayprice;
        textView_dayprice.setText(decimalFormat.format(dayprice));
///////////////////////////////
        int average= Integer.parseInt(textView_averagebuyprice.getText().toString());
        INT_PRICE=average;
        textView_averagebuyprice.setText(decimalFormat.format(average));
        ///////////////////////
        int totPD= Integer.parseInt(textView_totalPriceDay.getText().toString());
        textView_totalPriceDay.setText(decimalFormat.format(totPD));
        /////////////////////
        int totPB=Integer.parseInt(textView_totalpricebuy.getText().toString());
        textView_totalpricebuy.setText(decimalFormat.format(totPB));
        //////
        int number = Integer.parseInt(textView_number.getText().toString());
        INT_NUMBER=number;
        textView_number.setText(decimalFormat.format(number));
        //////////
        int sarbsar=Integer.parseInt(textView_sarbsar.getText().toString());
        textView_sarbsar.setText(decimalFormat.format(sarbsar));
    }



    @Override
    public void ConfirmDialog() {
        DataBaseHelper dataBadeHelper=new DataBaseHelper(getContext());
        try{

            String name=textView_namadname.getText().toString();
            String coName=textView_namadCoName.getText().toString();
            int price= INT_PRICE;
            int sold_price=INT_SOLD_PRICE;
            int number= INT_NUMBER;
            String date=textView_date.getText().toString();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            String sold_date=formattedDate;
            dataBadeHelper.DB_Sell(name,price,sold_price,number,date,sold_date,coName);
            dataBadeHelper.row_delete_buy(name);
            FragmentManager fragmentManager=getFragmentManager();
            FragmentTransaction transaction =fragmentManager.beginTransaction();
            TestBuySellFragment testBuySellFragment=new TestBuySellFragment();
            fragmentManager.popBackStack();
            transaction.replace(R.id.cl_pageviewer_cl,testBuySellFragment);
            transaction.commit();
            fragmentManager.popBackStack("testBuyDetailFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }catch (Exception e){e.getStackTrace();}
    }

    @Override
    public void CancelDialog() {
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.popBackStack("confirmDialogFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }
}