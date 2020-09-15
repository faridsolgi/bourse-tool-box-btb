package com.glorysys.boursetoolbox.main.Fragments.TestBuy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.DataBase.DataBaseHelper;

import java.text.DecimalFormat;

public class comitBuyFragment extends Fragment {
    Button button_cancel, button_buy,button_plusOne,button_negtiveOne;
    EditText editText_price, editText_amount;
    TextView textView_namadName, textView_upprice, textView_downprice;
    TextView textView_fullAmount ;
    final static String NAMAD_NAME = "namadname", LAST_PRICE_AMOUNT = "LastPriceAmount", DATE = "DATE", LOWEST_PRICE = "LowestPrice",
            HIGHEST_PRICE = "HighestPrice";
    String date;

    public comitBuyFragment() {
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
        View view = inflater.inflate(R.layout.fragment_comit_buy, container, false);
        onCreateFunction(view);


        return view;
    }


    private void onCreateFunction(View view) {
        setupViews(view);
        ///////////
        textView_fullAmount.setText("0");
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(getContext());
                try {
                    FragmentManager manager = getFragmentManager();
                    manager.popBackStack("comitFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });
        /////////////
        setTextbundle();
/////////
        button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(getContext());
                DataBaseHelper dataBadeHelper = new DataBaseHelper(getContext());
                String name = textView_namadName.getText().toString();
                if (editText_price.getText().length() != 0 && editText_amount.getText().length() != 0) {
                    long price = Long.parseLong(editText_price.getText().toString());
                    long number = Long.parseLong(editText_amount.getText().toString());
                    if (price<= 0) {
                        Toast.makeText(getContext(), "عددی ورودی در بازه قیمتی مجاز نیست", Toast.LENGTH_SHORT).show();
                    } else {
                            if(Long.parseLong(editText_amount.getText().toString())>0){
                                dataBadeHelper.DB_buy(name, price, number, date);
                                FragmentManager manager = getFragmentManager();
                                manager.popBackStack("comitFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }else{
                                Toast.makeText(getContext(), "لطفا تعداد سهام را بیشتر از صفر قرار دهید", Toast.LENGTH_SHORT).show();
                            }


                    }

                } else {
                    Toast.makeText(getContext(), "هر دو فیلد قیمت و تعداد باید پر شود", Toast.LENGTH_SHORT).show();
                }

            }


        });
        //////
        textView_upprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    editText_price.setText(textView_upprice.getText().toString());
                }catch (Exception e){
                    e.getStackTrace();
                }

            }
        });
        /////
        textView_downprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    editText_price.setText(textView_downprice.getText().toString());
                }catch (Exception e){
                    e.getStackTrace();
                }
            }
        });
        /////
        try {
            editText_price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(editText_price.getText().toString().length()!=0){
                        if(editText_amount.getText().toString().length()!=0){
                           long amount=Long.parseLong(editText_amount.getText().toString());
                            long price=Long.parseLong(editText_price.getText().toString());
                            long fullAmount=amount*price;
                            DecimalFormat decimalFormat=new DecimalFormat("#,###");
                            textView_fullAmount.setText(decimalFormat.format(fullAmount));
                        }else{
                            textView_fullAmount.setText("0");
                        }



                        if (Long.parseLong(textView_downprice.getText().toString()) > Long.parseLong(editText_price.getText().toString())|
                                Long.parseLong(textView_upprice.getText().toString()) < Long.parseLong(editText_price.getText().toString())) {
                            editText_price.setTextColor(getResources().getColor(R.color.red_500));
                        }else{
                            editText_price.setTextColor(getResources().getColor(R.color.black));
                        }


                    }

                }
            });
        }catch (Exception e){
            e.getStackTrace();
        }
///////////////
        editText_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editText_price.getText().toString().length()!=0) {
                    if (editText_amount.getText().toString().length() != 0) {
                        long amount = Long.parseLong(editText_amount.getText().toString());
                        long price = Long.parseLong(editText_price.getText().toString());
                        long fullAmount = amount * price;
                        DecimalFormat decimalFormat=new DecimalFormat("#,###");
                        textView_fullAmount.setText(decimalFormat.format(fullAmount));
                    } else {
                        textView_fullAmount.setText("0");
                    }
                }

            }
        });
        ////
        button_plusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText_price.getText().length()!=0){
                    long plus=Long.parseLong(editText_price.getText().toString());
                    plus++;
                    editText_price.setText(String.valueOf(plus));
                }else{
                    editText_price.setText("0");
                }

            }
        });
///////////////
        button_negtiveOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText_price.getText().length()!=0){
                    if(Long.parseLong(editText_price.getText().toString())==0){
                        editText_price.setText("0");
                    }else{
                        long plus=Long.parseLong(editText_price.getText().toString());
                        plus--;
                        editText_price.setText(String.valueOf(plus));
                    }

                }else{
                    editText_price.setText("0");
                }
            }
        });
    }

    private void setupViews(View view) {
        button_cancel = view.findViewById(R.id.button_comitBuy_cancel);
        button_buy = view.findViewById(R.id.button_comitBuy_buy);
        button_plusOne = view.findViewById(R.id.button_comitBuy_plusOne);
        button_negtiveOne = view.findViewById(R.id.button_comitBuy_negtivOne);
        editText_amount = view.findViewById(R.id.editText_comitBuy_Amount);
        editText_price = view.findViewById(R.id.editText_comitBuy_price);
        editText_price.setGravity(Gravity.RIGHT);
        editText_amount.setGravity(Gravity.RIGHT);
        textView_namadName = view.findViewById(R.id.textView_comitBuyFragment_namadName);
        textView_upprice = view.findViewById(R.id.textView_comitBuy_upprice);
        textView_downprice = view.findViewById(R.id.textView_comitBuy_downprice);
        textView_fullAmount=view.findViewById(R.id.textView_comitBuy_fullAmount);
    }

    private void setTextbundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String namadName = bundle.getString(NAMAD_NAME);
            String lastPrice = bundle.getString(LAST_PRICE_AMOUNT);
            date = bundle.getString(DATE);
            textView_namadName.setText(namadName);
            editText_price.setText(bundle.getString(HIGHEST_PRICE));
            textView_downprice.setText(bundle.getString(LOWEST_PRICE));
            textView_upprice.setText(bundle.getString(HIGHEST_PRICE));
        }
    }




    ///////////////////////////////hide keyboard/////////////////////////////////////
    public void hideSoftKeyboard(Context activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
/*        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);*/
            inputMethodManager.hideSoftInputFromWindow(editText_price.getWindowToken(), 0);

        }catch (Exception e){e.getStackTrace();}
    }

}