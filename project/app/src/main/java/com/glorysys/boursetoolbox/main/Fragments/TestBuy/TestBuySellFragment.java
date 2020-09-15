package com.glorysys.boursetoolbox.main.Fragments.TestBuy;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorysys.boursetoolbox.R;

public class TestBuySellFragment extends Fragment {
    ImageView button_buy,button_sell;
    TextView textView_buy,textView_sell;
    ConstraintLayout constraintLayout;
    ConstraintSet constraintSet;
    public TestBuySellFragment() {
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
        View view = inflater.inflate(R.layout.fragment_test_buy_sell, container, false);
        setupViews(view);
        pagechanger();
        return view;
    }

   private void setupViews(View view) {
        button_buy=view.findViewById(R.id.botton_pageviewer_buy);
        button_sell=view.findViewById(R.id.botton_pageviewer_sell);
        textView_buy=view.findViewById(R.id.textView_pageviewer_buy);
        textView_sell=view.findViewById(R.id.textView_pageviewer_sell);
        constraintLayout = view.findViewById(R.id.cl_pageviewer_clmain);
    }
    private void pagechanger(){
       constraintSet = new ConstraintSet();
        button_buy.setBackground(getResources().getDrawable(R.drawable.ic_baseline_shopping_cart_pink500_24));
        ////////
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.botton_pageviewer_sell,ConstraintSet.BOTTOM,R.id.viewbottom,ConstraintSet.BOTTOM,0);
        constraintSet.clear(R.id.botton_pageviewer_buy,ConstraintSet.BOTTOM);
        constraintSet.applyTo(constraintLayout);
        ////////
        textView_sell.setVisibility(View.GONE);
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        TestBuyFragment testBuyFragment=new TestBuyFragment();
        transaction.add(R.id.cl_pageviewer_cl,testBuyFragment);
        transaction.commit();
        button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_sell.setBackground(getResources().getDrawable(R.drawable.ic_baseline_remove_shopping_cart_24));
                button_buy.setBackground(getResources().getDrawable(R.drawable.ic_baseline_shopping_cart_pink500_24));
                textView_buy.setTextColor(getResources().getColor(R.color.pink_500));
                textView_sell.setTextColor(getResources().getColor(R.color.grey_700));
                textView_buy.setVisibility(View.VISIBLE);
                textView_sell.setVisibility(View.GONE);
                ////
                constraintSet.clone(constraintLayout);
                constraintSet.connect(R.id.botton_pageviewer_sell,ConstraintSet.BOTTOM,R.id.viewbottom,ConstraintSet.BOTTOM,0);
                constraintSet.clear(R.id.botton_pageviewer_buy,ConstraintSet.BOTTOM);
                constraintSet.applyTo(constraintLayout);
                /////
                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                TestBuyFragment testBuyFragment=new TestBuyFragment();
                transaction.add(R.id.cl_pageviewer_cl,testBuyFragment);
                transaction.commit();
            }
        });
        ////////////////
        button_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_buy.setVisibility(View.GONE);
                textView_sell.setVisibility(View.VISIBLE);
                ////
                constraintSet.clone(constraintLayout);
                constraintSet.connect(R.id.botton_pageviewer_buy,ConstraintSet.BOTTOM,R.id.viewbottom,ConstraintSet.BOTTOM,0);
                constraintSet.clear(R.id.botton_pageviewer_sell,ConstraintSet.BOTTOM);
                constraintSet.applyTo(constraintLayout);
                /////
                textView_sell.setTextColor(getResources().getColor(R.color.pink_500));
                button_buy.setBackground(getResources().getDrawable(R.drawable.ic_baseline_shopping_cart_24));
                button_sell.setBackground(getResources().getDrawable(R.drawable.ic_baseline_remove_shopping_cart_pink500_24));
                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                TestSellFragment testSellFragment=new TestSellFragment();
                transaction.add(R.id.cl_pageviewer_cl,testSellFragment);
                transaction.commit();
            }
        });

    }
}