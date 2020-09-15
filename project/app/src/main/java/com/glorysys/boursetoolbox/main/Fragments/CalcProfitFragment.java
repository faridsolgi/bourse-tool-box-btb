package com.glorysys.boursetoolbox.main.Fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.glorysys.boursetoolbox.R;


public class CalcProfitFragment extends Fragment {
    Spinner spinner;
    Button button_back;
    /////power price /////
    Button button_powerPrice_calc;
    TextView textView_powerPrice_result,textView_powerPrice_resultText;
    EditText editText_powerPrice_editText1,editText_powerPrice_editText2;
    ConstraintLayout cl_powerPrice_cl;
    long PowerPrice_result;
    /////Profit or  negative /////
    ConstraintLayout cl_profitOrNegative_cl;
    Button button_profitOrNegative_calcButton;
    TextView textView_profitOrNegative_result;
    EditText editText_profitOrNegative_number,editText_profitOrNegative_buyPrice,editText_profitOrNegative_soldPrice;
    long int_profitOrNegative_result,int_profitOrNegative_buyPrice,int_profitOrNegative_soldPrice,int_profitOrNegative_number;
    double double_profitOrNegative_resultpercent;
    /////Profit or  negative /////
    /////Profit or  negative /////
    public CalcProfitFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

String list[]={"قدرت خرید"};///"سود و زیان","خرید و فروش پله ای ","افزایش سرمایه"}
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item,list);
        View view = inflater.inflate(R.layout.fragment_calc_profit, container, false);
        setupViews(view);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    //////power price////////
                    case 0:
                     //   Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
                        cl_powerPrice_cl.setVisibility(View.VISIBLE);
                        cl_profitOrNegative_cl.setVisibility(View.GONE);
                        ////////////
                        button_powerPrice_calc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(editText_powerPrice_editText1.getText().toString().equals("") || editText_powerPrice_editText2.getText().toString().equals("")){
                                    Toast.makeText(getContext(), "لطفا هر دو فیلد مبلغ و قیمت هر سهم را پر کنید", Toast.LENGTH_SHORT).show();
                                }else{
                                    long num1=Long.parseLong(editText_powerPrice_editText1.getText().toString());
                                    long num2=Long.parseLong(editText_powerPrice_editText2.getText().toString());
                                    try{
                                          PowerPrice_result =num1/num2;
                                    }catch (ArithmeticException e){
                                        e.getStackTrace();
                                        String toast=getResources().getString(R.string.youCantBuy);
                                        Toast.makeText(getContext(),toast,Toast.LENGTH_SHORT).show();
                                    }

                                    if (PowerPrice_result <1){
                                        String toast=getResources().getString(R.string.youCantBuy);
                                        Toast.makeText(getContext(),toast,Toast.LENGTH_SHORT).show();
                                    }else {
                                        textView_powerPrice_resultText.setVisibility(View.VISIBLE);
                                        textView_powerPrice_result.setText(String.valueOf(PowerPrice_result));
                                    }
                                }
                            }
                        });





                        break;
                    case 1:
                      //  Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
                        cl_profitOrNegative_cl.setVisibility(View.VISIBLE);
                        cl_powerPrice_cl.setVisibility(View.GONE);
                        button_profitOrNegative_calcButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                boolean number=editText_profitOrNegative_number.getText().toString().equals("");
                                boolean Buyprice=editText_profitOrNegative_number.getText().toString().equals("");
                                boolean SoldPrice=editText_profitOrNegative_number.getText().toString().equals("");
                                if(number||Buyprice||SoldPrice){
                                  //  Toast.makeText(getContext(), "eroor", Toast.LENGTH_SHORT).show();
                                }else{
                                    int_profitOrNegative_number=Integer.parseInt(editText_profitOrNegative_number.getText().toString());
                                    int_profitOrNegative_number=Integer.parseInt(editText_profitOrNegative_buyPrice.getText().toString());
                                    int_profitOrNegative_number=Integer.parseInt(editText_profitOrNegative_soldPrice.getText().toString());
                                    if(int_profitOrNegative_number==0 ||int_profitOrNegative_buyPrice==0 ||int_profitOrNegative_soldPrice==0){

                                        try {
                                            int_profitOrNegative_result=(int_profitOrNegative_soldPrice-int_profitOrNegative_buyPrice)*int_profitOrNegative_number;
                                            double_profitOrNegative_resultpercent=(int_profitOrNegative_soldPrice-int_profitOrNegative_buyPrice)/int_profitOrNegative_buyPrice*100;
                                            long mablaghforosh=int_profitOrNegative_soldPrice*int_profitOrNegative_number;
                                            long mablaghkharid=int_profitOrNegative_buyPrice*int_profitOrNegative_number;
                                            String Str_mablaghBuy=getResources().getString(R.string.mablaghBuy);
                                            String Str_mablaghSold=getResources().getString(R.string.mablaghSold);
                                            String Str_sodYaZian=getResources().getString(R.string.profit);

                                            String str_final=Str_mablaghBuy+"          "+mablaghkharid+"\n"+
                                                    Str_mablaghSold+"          "+mablaghforosh+"\n"+
                                                    Str_sodYaZian+"          "+int_profitOrNegative_result+" ("+double_profitOrNegative_resultpercent+") ";
                                            textView_profitOrNegative_result.setText(str_final);
                                          //  Toast.makeText(getContext(), str_final, Toast.LENGTH_SHORT).show();
                                        }catch (ArithmeticException e){
                                            e.getStackTrace();
                                        }

                                    }else {

                                        Toast.makeText(getContext(), "عدد صفر را تغییر دهید", Toast.LENGTH_SHORT).show();
                                    }

                                }







                            }
                        });


                        break;
                    case 2:
                      //  Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
                        cl_powerPrice_cl.setVisibility(View.GONE);
                        cl_profitOrNegative_cl.setVisibility(View.GONE);



                        break;
                    case 3:
                    //    Toast.makeText(getContext(), "4", Toast.LENGTH_SHORT).show();
                        cl_powerPrice_cl.setVisibility(View.GONE);
                        cl_profitOrNegative_cl.setVisibility(View.GONE);


                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast.makeText(getContext(), "Nothing", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void setupViews(View view) {
        spinner = view.findViewById(R.id.spinner_clac_spinner);
        button_back=view.findViewById(R.id.button_calc_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        ///////power price//////
        cl_powerPrice_cl=view.findViewById(R.id.cl_calc_clPowerPrice);
        button_powerPrice_calc =view.findViewById(R.id.button__calc_PowerPrice_Button);
        textView_powerPrice_result=view.findViewById(R.id.textview_calc_PowerPrice_result);
        textView_powerPrice_resultText=view.findViewById(R.id.textview_calc_PowerPrice_resultText);
        editText_powerPrice_editText1=view.findViewById(R.id.editText_calc_PowerPrice_editext1);
        editText_powerPrice_editText2=view.findViewById(R.id.editText_calc_PowerPrice_editext2);
        ///////profit or Negative//////
        cl_profitOrNegative_cl=view.findViewById(R.id.cl_calc_clProfitOrNegative);
        button_profitOrNegative_calcButton=view.findViewById(R.id.button_calc_profitOrNegative_calcButton);
        textView_profitOrNegative_result=view.findViewById(R.id.textview_calc_profitOrNegative_result);
        editText_profitOrNegative_number=view.findViewById(R.id.edittext_calc_profitOrNegative_edittextnumber);
        editText_profitOrNegative_buyPrice=view.findViewById(R.id.edittext_calc_profitOrNegative_buyPrice);
        editText_profitOrNegative_soldPrice=view.findViewById(R.id.edittext_calc_profitOrNegative_soldPrice);
        ///////power price//////
        ///////power price//////

    }

}