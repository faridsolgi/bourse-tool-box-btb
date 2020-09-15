package com.glorysys.boursetoolbox.main.Fragments.TestBuy;

import android.database.Cursor;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Adapter.TestSellAdapter;
import com.glorysys.boursetoolbox.main.DataBase.DataBaseHelper;
import com.glorysys.boursetoolbox.main.Models.TestBuyDataModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class TestSellFragment extends Fragment implements TestSellAdapter.TestSellViewHolder.onClickListener {
    List<TestBuyDataModel> dataModelList;
ConstraintLayout constraintLayout;
RecyclerView recyclerView;
TestSellAdapter adapter;
    final static String PROFIT = "profit",NAMAD_NAME = "namadname",
            NAMAD_COMPANY_NAME = "namad_C_name", SOLD_PRICE = "soldPrice",
            SOLD_DATE = "soldDate", BUY_PRICE = "buy", NUMBER = "number", BUY_DATE = "buydate",ID="id";
Button button_back;


    public TestSellFragment() {
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
        View view=inflater.inflate(R.layout.fragment_test_sell, container, false);
    SetupViews(view);
    displaydata();
    adapter=new TestSellAdapter(dataModelList,this);
    recyclerView.setAdapter(adapter);
    button_back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager manager=getFragmentManager();
            manager.popBackStack("TestBuyFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    });

        return view;
    }



    public void displaydata() {
        DataBaseHelper dataBadeHelper = new DataBaseHelper(getContext());
        Cursor cursor = dataBadeHelper.ReadAllData_Table_sell();
        dataModelList=new ArrayList<>();
        if (cursor.getCount() == 0) {
            constraintLayout.setVisibility(View.VISIBLE);
        } else {
            constraintLayout.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                TestBuyDataModel testBuyDataModel = new TestBuyDataModel();
                testBuyDataModel.setID(cursor.getInt(0));
                testBuyDataModel.setNamadName(cursor.getString(1));
                testBuyDataModel.setBuyprice(cursor.getInt(2));
                testBuyDataModel.setSoldPrice(cursor.getInt(3));
                testBuyDataModel.setTedad(cursor.getInt(4));
                String ssss = cursor.getString(5);
                String ssss2 = cursor.getString(6);
                double buyprice=cursor.getInt(2);
                double soldprice=cursor.getInt(3);
                double profit=((soldprice-buyprice)/buyprice)*100;
                DecimalFormat df=new DecimalFormat("#.##");

                testBuyDataModel.setProfit(Double.parseDouble(df.format(profit)));
                testBuyDataModel.setNamadCompanyName(cursor.getString(7));
                PersianDateFormat pdformater1 = new PersianDateFormat("dd-MM-yyyy");
                try {
                     PersianDate persianDate = pdformater1.parseGrg(ssss2);
                    ssss2 = persianDate.getShDay() + "  " + persianDate.monthName() + "  " + persianDate.getShYear();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                testBuyDataModel.setDate(ssss);
                testBuyDataModel.setSoldDate(ssss2);
                dataModelList.add(testBuyDataModel);

            }


        }

    }







        private void SetupViews(View view) {
        constraintLayout=view.findViewById(R.id.cl_testSellFragment_clNoData);
        recyclerView=view.findViewById(R.id.recyclerView_testSellFragment_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        button_back=view.findViewById(R.id.button_testSellFragment_back);
    }


    @Override
    public void onClick(TestBuyDataModel dataModel) {
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        TestSellDetailFragment testSellDetailFragment=new TestSellDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(ID,dataModel.getID());
        bundle.putString(NAMAD_NAME,dataModel.getNamadName());
        bundle.putString(NAMAD_COMPANY_NAME,dataModel.getNamadCompanyName());
        bundle.putInt(BUY_PRICE,dataModel.getBuyprice());
        bundle.putInt(SOLD_PRICE,dataModel.getSoldPrice());
        bundle.putInt(NUMBER,dataModel.getTedad());
        bundle.putString(BUY_DATE,dataModel.getDate());
        bundle.putDouble(PROFIT,dataModel.getProfit());
        bundle.putString(SOLD_DATE,dataModel.getSoldDate());
        testSellDetailFragment.setArguments(bundle);
        transaction.add(R.id.cl_main_main,testSellDetailFragment);
        transaction.addToBackStack("testSellDetailFragment");
        transaction.commit();
    }
}