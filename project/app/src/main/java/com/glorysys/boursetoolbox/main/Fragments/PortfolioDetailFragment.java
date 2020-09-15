package com.glorysys.boursetoolbox.main.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Fragments.TestBuy.comitBuyFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PortfolioDetailFragment extends Fragment {
    final static String NAMAD_NAME = "namadname";
    final static String NAMAD_COMPANY_NAME = "namad_C_name";
    final static String AMOUNT = "Amount";
    final static String VOLUME = "Volume";
    final static String VALUE = "Value";
    final static String YESTERDAY = "Yesterday";
    final static String FIRST_PRICE = "FirstPrice";
    final static String LAST_DEAL_PRICE_AMOUNT = "LastDealPriceAmount";
    final static String LAST_DEAL_CHANGE_PRICE = "LastDealChangePrice";
    final static String LAST_DEAL_PERCENTAGE = "LastDealPercentage";
    final static String LAST_PRICE_AMOUNT = "LastPriceAmount";
    final static String LAST_PRICE_CHANGE = "LastPriceChange";
    final static String LAST_PRICE_PERCENTAGE = "LastPricePercentage";
    final static String LOWEST_PRICE = "LowestPrice";
    final static String HIGHEST_PRICE = "HighestPrice";
    final static String EPS = "EPS";
    final static String PE = "PE";
    final static String BUY = "buy";
    final static String SELL = "sell";
    final static String DATE = "DATE";
    TextView TV_namadName, TV_namadCompanyName, TV_Amount, TV_Volume, TV_Value, TV_Yesterday, TV_FirstPrice, TV_LastDealPriceAmount, TV_LastDealChangePrice,
            TV_LastDealPercentage, TV_LastPriceAmount, TV_LastPriceChange, TV_LastPricePercentage, TV_LowestPrice, TV_HighestPrice, TV_EPS, TV_PE, TV_Buy, TV_Sell;
private Button button_testbuy,button_back;
    public PortfolioDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_namad_detail, container, false);
        SetupViews(view);
        settext();
        button_testbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                comitBuyFragment comitBuyFragment=new comitBuyFragment();
                Bundle bundle=new Bundle();
                bundle.putString(DATE,formattedDate);
                bundle.putString(NAMAD_NAME,TV_namadName.getText().toString());
                bundle.putString(LAST_PRICE_AMOUNT,TV_LastPriceAmount.getText().toString());
                bundle.putString(HIGHEST_PRICE,TV_HighestPrice.getText().toString());
                bundle.putString(LOWEST_PRICE,TV_LowestPrice.getText().toString());
                comitBuyFragment.setArguments(bundle);
                transaction.add(R.id.cl_main_main,comitBuyFragment);
                transaction.addToBackStack("comitFragment");
                transaction.commit();
            }
        });
        //////////
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.popBackStack("portfolioDetailFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return view;
    }

    private void SetupViews(View view) {
        TV_namadName = view.findViewById(R.id.textView_NamadDetail_NamadName);
        TV_namadCompanyName = view.findViewById(R.id.textView_NamadDetail_NamadCompanyName);
        TV_Amount = view.findViewById(R.id.textView_NamadDetail_numOfExchange);
        TV_Value = view.findViewById(R.id.textView_NamadDetail_valueOfExchange);
        TV_Volume = view.findViewById(R.id.textView_NamadDetail_volOfExchange);
        TV_Yesterday = view.findViewById(R.id.textView_NamadDetail_pastday);
        TV_FirstPrice = view.findViewById(R.id.textView_NamadDetail_firstPrice);
        TV_LastDealPriceAmount = view.findViewById(R.id.textView_NamadDetail_lastDeal);
        TV_LastDealChangePrice = view.findViewById(R.id.textView_NamadDetail_lastDealChange);
        TV_LastDealPercentage = view.findViewById(R.id.textView_NamadDetail_lastDealChangePercentage);
        TV_LastPriceAmount = view.findViewById(R.id.textView_NamadDetail_lastPrice);
        TV_LastPriceChange = view.findViewById(R.id.textView_NamadDetail_lastPriceChange);
        TV_LastPricePercentage = view.findViewById(R.id.textView_NamadDetail_lastPriceChangePercentage);
        TV_LowestPrice = view.findViewById(R.id.textView_NamadDetail_low);
        TV_HighestPrice = view.findViewById(R.id.textView_NamadDetail_high);
        TV_EPS = view.findViewById(R.id.textView_NamadDetail_EPS);
        TV_PE = view.findViewById(R.id.textView_NamadDetail_pe);
        TV_Buy = view.findViewById(R.id.textView_NamadDetail_buy);
        TV_Sell = view.findViewById(R.id.textView_NamadDetail_sell);
        button_testbuy = view.findViewById(R.id.button_namadDetail_testbuy);
        button_back = view.findViewById(R.id.button_NamadDetail_back);
    }

    private void settext() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            TV_namadName.setText(bundle.getString(NAMAD_NAME));
            TV_namadCompanyName.setText(bundle.getString(NAMAD_COMPANY_NAME));
            TV_Amount.setText(bundle.getString(AMOUNT));
            TV_Volume.setText(bundle.getString(VOLUME));
            TV_Value.setText(bundle.getString(VALUE));
            TV_Yesterday.setText(bundle.getString(YESTERDAY));
            TV_FirstPrice.setText(bundle.getString(FIRST_PRICE));
            TV_LastDealPriceAmount.setText(bundle.getString(LAST_DEAL_PRICE_AMOUNT));
            TV_LastDealChangePrice.setText("(" + bundle.getString(LAST_DEAL_CHANGE_PRICE) + ")");
            TV_LastDealPercentage.setText(bundle.getString(LAST_DEAL_PERCENTAGE) + " %");
            TV_LastPriceAmount.setText(bundle.getString(LAST_PRICE_AMOUNT));
            TV_LastPriceChange.setText("(" + bundle.getString(LAST_PRICE_CHANGE) + ")");
            TV_LastPricePercentage.setText(bundle.getString(LAST_PRICE_PERCENTAGE) + " %");
            TV_LowestPrice.setText(bundle.getString(LOWEST_PRICE));
            TV_HighestPrice.setText(bundle.getString(HIGHEST_PRICE));
            TV_EPS.setText(bundle.getString(EPS));
            TV_PE.setText(bundle.getString(PE));
            TV_Buy.setText(bundle.getString(BUY));
            TV_Sell.setText(bundle.getString(SELL));
            parsedata();

        }
    }


    private void parsedata() {
        if (TV_EPS.getText().toString() == "null") {
            TV_EPS.setText("نامشخص");
            TV_EPS.setTextColor(getResources().getColor(R.color.red_400));
            TV_EPS.setTextSize(14);
        } else {
            TV_EPS.setTextColor(getResources().getColor(R.color.white));
            TV_EPS.setTextSize(18);
        }
        if (TV_PE.getText().toString() == "null") {
            TV_PE.setText("نامشخص");
            TV_PE.setTextSize(14);
            TV_PE.setTextColor(getResources().getColor(R.color.red_400));
        } else {
            TV_PE.setTextSize(18);
            TV_PE.setTextColor(getResources().getColor(R.color.white));

        }

        String twoFirstCharacters = TV_LastPricePercentage.getText().toString().substring(0, 1);
        if (twoFirstCharacters.equals("-")) {
            String finaltext = TV_LastPricePercentage.getText().toString().substring(TV_LastPricePercentage.getText().toString().lastIndexOf("-") + 1);
            TV_LastPricePercentage.setTextColor(getResources().getColor(R.color.red_400));
            TV_LastPricePercentage.setText(finaltext);
        } else {
            TV_LastPricePercentage.setTextColor(getResources().getColor(R.color.green_400));
        }

        String twoFirstCharacters1 = TV_LastDealPercentage.getText().toString().substring(0, 1);
        if (twoFirstCharacters1.equals("-")) {
            String finaltext = TV_LastDealPercentage.getText().toString().substring(TV_LastDealPercentage.getText().toString().lastIndexOf("-") + 1);
            TV_LastDealPercentage.setTextColor(getResources().getColor(R.color.red_400));
            TV_LastDealPercentage.setText(finaltext);
        } else {
            TV_LastDealPercentage.setTextColor(getResources().getColor(R.color.green_400));
        }

        String twoFirstCharacters2 = TV_LastPriceChange.getText().toString().substring(0, 1);
        if (twoFirstCharacters.equals("-")) {
            String finaltext = TV_LastPriceChange.getText().toString().substring(TV_LastPriceChange.getText().toString().lastIndexOf("-") + 1);
            TV_LastPriceChange.setTextColor(getResources().getColor(R.color.red_400));
            TV_LastPriceChange.setText("(" + finaltext);
        } else {
            TV_LastPriceChange.setTextColor(getResources().getColor(R.color.green_400));
        }

        String twoFirstCharacters3 = TV_LastDealChangePrice.getText().toString().substring(0, 1);
        if (twoFirstCharacters1.equals("-")) {
            String finaltext = TV_LastDealChangePrice.getText().toString().substring(TV_LastDealChangePrice.getText().toString().lastIndexOf("-") + 1);
            TV_LastDealChangePrice.setTextColor(getResources().getColor(R.color.red_400));
            TV_LastDealChangePrice.setText("(" + finaltext);
        } else {
            TV_LastDealChangePrice.setTextColor(getResources().getColor(R.color.green_400));
        }


    }

}
