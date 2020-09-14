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

import java.text.DecimalFormat;

public class TestSellDetailFragment extends Fragment implements ConfirmDialogFragment.interface_confirmDialog {
    TextView textView_namadName, textView_namadCoName, textView_buydate, textView_soldDate, textView_buyPrice,
            textView_soldPrice, textView_profit, textView_profitPercent, textView_number;
    Button button_close, button_delete;
    final static String PROFIT = "profit", NAMAD_NAME = "namadname",
            NAMAD_COMPANY_NAME = "namad_C_name", SOLD_PRICE = "soldPrice",
            SOLD_DATE = "soldDate", BUY_PRICE = "buy", NUMBER = "number", BUY_DATE = "buydate", ID = "id";
    int id;

    public TestSellDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_test_sell_detail, container, false);
        onCreatefunction(view);


        return view;
    }

    private void onCreatefunction(View view) {
        setupViews(view);
        getArgumentFromParent();
        //////////////close
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });
        /////////////////delete
        final ConfirmDialogFragment confirmDialogFragment=new ConfirmDialogFragment(this);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=textView_namadName.getText().toString();
                String description=" آیا قصد حذف  "+name+" دارید؟ ";
                String title= " حذف "+name+" ؟ ";
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
        ////////////////
    }

    //findview by id//
    private void setupViews(View view) {
        textView_namadName = view.findViewById(R.id.textview_sellDetail_namadName);
        textView_namadCoName = view.findViewById(R.id.textview_sellDetail_namadCoName);
        textView_buydate = view.findViewById(R.id.textview_sellDetail_buyDate);
        textView_soldDate = view.findViewById(R.id.textview_sellDetail_sellDate);
        textView_buyPrice = view.findViewById(R.id.textview_sellDetail_buyPrice);
        textView_soldPrice = view.findViewById(R.id.textview_sellDetail_sellPrice);
        textView_profit = view.findViewById(R.id.textview_sellDetail_profit);
        textView_profitPercent = view.findViewById(R.id.textview_sellDetail_profitPercent);
        textView_number = view.findViewById(R.id.textview_sellDetail_number);
        button_close = view.findViewById(R.id.button_sellDetail_close);
        button_delete = view.findViewById(R.id.button_sellDetail_delete);

    }

    private void getArgumentFromParent() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            try {
                id = bundle.getInt(ID);
                String namad = bundle.getString(NAMAD_NAME);
                String namadCo = bundle.getString(NAMAD_COMPANY_NAME);
                int buyPrice = bundle.getInt(BUY_PRICE);
                int soldPrice = bundle.getInt(SOLD_PRICE);
                int number = bundle.getInt(NUMBER);
                String buyDate = bundle.getString(BUY_DATE);
                double profit = bundle.getDouble(PROFIT);
                String soldDate = bundle.getString(SOLD_DATE);
                /////////////////// textView set text
                textView_namadName.setText(namad);
                textView_namadCoName.setText(namadCo);
                textView_buyPrice.setText(String.valueOf(buyPrice));
                textView_soldPrice.setText(String.valueOf(soldPrice));
                textView_number.setText(String.valueOf(number));
                textView_buydate.setText(buyDate);
                textView_profitPercent.setText(String.valueOf(profit));
                textView_soldDate.setText(soldDate);
                textSetting();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    private void textSetting() {
        Double d = Double.valueOf(textView_profitPercent.getText().toString());

        if (d > 0) {
            textView_profitPercent.setText(textView_profitPercent.getText().toString() + " %");
            textView_profitPercent.setTextColor(getResources().getColor(R.color.green_400));
        }
        if (d < 0) {

            String twoFirstCharacters = textView_profitPercent.getText().toString().substring(0, 1);

            if (twoFirstCharacters.equals("-")) {
                String finaltext = textView_profitPercent.getText().toString().substring(textView_profitPercent.getText().toString().lastIndexOf("-") + 1);
                textView_profitPercent.setTextColor(getResources().getColor(R.color.red_400));

                textView_profitPercent.setText(finaltext + " %");
            }
        }
        int x = Integer.parseInt(textView_soldPrice.getText().toString()) * Integer.parseInt(textView_number.getText().toString());
        int y = Integer.parseInt(textView_buyPrice.getText().toString()) * Integer.parseInt(textView_number.getText().toString());
        int profit = x - y;
        textView_profit.setText(String.valueOf(profit));

        String twoFirstCharacters = textView_profit.getText().toString().substring(0, 1);
        if (twoFirstCharacters.equals("-")) {
            String finaltext = textView_profit.getText().toString().substring(textView_profit.getText().toString().lastIndexOf("-") + 1);
            textView_profit.setTextColor(getResources().getColor(R.color.red_400));
            textView_profit.setText(finaltext);
        } else {
            textView_profit.setTextColor(getResources().getColor(R.color.green_400));
        }
        DecimalFormat decimalFormat=new DecimalFormat("#,###");
        int dayprice= Integer.parseInt(textView_buyPrice.getText().toString());
        textView_buyPrice.setText(decimalFormat.format(dayprice));
///////////////////////////////
        int average= Integer.parseInt(textView_soldPrice.getText().toString());

        textView_soldPrice.setText(decimalFormat.format(average));
        ///////////////////////
        int totPD= Integer.parseInt(textView_profit.getText().toString());
        textView_profit.setText(decimalFormat.format(totPD));
        /////////////////////
        int totPB=Integer.parseInt(textView_number.getText().toString());
        textView_number.setText(decimalFormat.format(totPB));
        //////

    }

    @Override
    public void ConfirmDialog() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        dataBaseHelper.row_delete_sell(String.valueOf(id));
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
        FragmentTransaction transaction = fm.beginTransaction();
        TestSellFragment testSellFragment = new TestSellFragment();
        transaction.replace(R.id.cl_pageviewer_cl, testSellFragment);
        transaction.commit();
        fm.popBackStack("testSellDetailFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    @Override
    public void CancelDialog() {
        FragmentManager manager = getFragmentManager();
        manager.popBackStack("confirmDialogFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}