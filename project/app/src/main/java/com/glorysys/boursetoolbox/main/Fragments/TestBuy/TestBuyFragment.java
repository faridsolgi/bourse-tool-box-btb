package com.glorysys.boursetoolbox.main.Fragments.TestBuy;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Adapter.TestBuyAdapter;
import com.glorysys.boursetoolbox.main.DataBase.DataBaseHelper;
import com.glorysys.boursetoolbox.main.Fragments.Namads.NamadsMainFragment;
import com.glorysys.boursetoolbox.main.Models.NamadsDataSample;
import com.glorysys.boursetoolbox.main.Models.TestBuyDataModel;
import com.glorysys.boursetoolbox.main.WebServer.NamadWebApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class TestBuyFragment extends Fragment implements NamadWebApi.getApiCallBack, TestBuyAdapter.TestBuyViewHolder.onclickListener {
    final static String NAMAD_NAME = "namadname";
    final static String NAMAD_COMPANY_NAME = "namad_C_name";
    final static String LAST_PRICE_AMOUNT = "LastPriceAmount";
    final static String PROFIT = "profit";
    final static String BUY_PRICE = "buy";
    final static String NUMBER = "number";
    final static String BUY_DATE = "buydate";
    private static final String TAG = "test";
    int ctn;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    TestBuyAdapter testBuyAdapter;
    List<TestBuyDataModel> testBuyDataModelList = new ArrayList<>();
    List<TestBuyDataModel> testBuyDataModelList1 = new ArrayList<>();
    DataBaseHelper dataBadeHelper;
    Button button_back, button_retry;
    private LottieAnimationView progressBar;
    FloatingActionButton button_buy;
    ConstraintLayout constraintLayout_noData;

    public TestBuyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_buy, container, false);
        // Inflate the layout for this fragment
        setupViews(view);
        displaydata();
        progressBar.setVisibility(View.VISIBLE);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack("TestBuyFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        /////////
        button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                NamadsMainFragment namadsMainFragment = new NamadsMainFragment();
                transaction.add(R.id.cl_main_main, namadsMainFragment);
                transaction.addToBackStack("namadslist");
                transaction.commit();
            }
        });
////////////////////////////////
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0) {
                    button_buy.show();
                } else if (dy > 0) {
                    button_buy.hide();
                }
            }
        });
/////////////////////
        button_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(button_retry, "rotation", 0f, 360f);
                animator.setRepeatCount(2);
                animator.setDuration(400);
                ctn = animator.getRepeatCount();
                animator.start();


                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        try {
                            FragmentManager manager = getFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            manager.popBackStack();
                            TestBuySellFragment testBuyFragment = new TestBuySellFragment();
                            transaction.replace(R.id.cl_main_main, testBuyFragment);
                            transaction.addToBackStack("TestBuyFragment");
                            transaction.commit();
                            Log.d(TAG, "onClick: ");
                            button_retry.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                        } catch (NullPointerException e) {
                            e.getStackTrace();
                        }


                    }


                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {


                    }

                });


            }
        });

////////////////////
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                manager.popBackStack();
                TestBuySellFragment testBuyFragment = new TestBuySellFragment();
                transaction.replace(R.id.cl_main_main, testBuyFragment);
                transaction.addToBackStack("TestBuyFragment");
                transaction.commit();
                Log.d(TAG, "onClick: ");
                button_retry.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        return view;
    }

    private void setupViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_testBuy_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        constraintLayout_noData = view.findViewById(R.id.cl_testBuyFragment_clNoData);
        button_buy = view.findViewById(R.id.button_testbuyFragment_buy);
        button_back = view.findViewById(R.id.button_testBuyFragment_back);
        button_retry = view.findViewById(R.id.button_testBuyFragment_retry);
        progressBar = view.findViewById(R.id.progressbar_testBuyFragment_progressbar);
        swipeRefreshLayout =view.findViewById(R.id.swipelayout_testBuy_swipelayout);

    }

    public void displaydata() {
        DataBaseHelper dataBadeHelper = new DataBaseHelper(getContext());
        Cursor cursor = dataBadeHelper.ReadAllData_Table_Buy();
        if (cursor.getCount() == 0) {
            constraintLayout_noData.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            constraintLayout_noData.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                progressBar.setVisibility(View.INVISIBLE);
                TestBuyDataModel testBuyDataModel = new TestBuyDataModel();
                testBuyDataModel.setNamadName(cursor.getString(0));
                testBuyDataModel.setBuyprice(cursor.getInt(1));
                testBuyDataModel.setTedad(cursor.getInt(2));
                String ssss = cursor.getString(3);
                PersianDateFormat pdformater1 = new PersianDateFormat("dd-MM-yyyy");
                try {
                    PersianDate persianDate = pdformater1.parseGrg(ssss);
                    ssss = persianDate.getShDay() + "  " + persianDate.monthName() + "  " + persianDate.getShYear();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                testBuyDataModel.setDate(ssss);
                testBuyDataModelList.add(testBuyDataModel);

            }


        }
        NamadWebApi namadWebApi = new NamadWebApi(getContext());
        namadWebApi.getapi(this);
    }


    @Override
    public void Success(List<NamadsDataSample> namadsDataSampleList) {

        for (int i = 0; i < namadsDataSampleList.size(); i++) {
            for (int j = 0; j < testBuyDataModelList.size(); j++) {
                String B = namadsDataSampleList.get(i).getNamadName();
                String A = testBuyDataModelList.get(j).getNamadName();
                if (B.equals(A)) {
                    TestBuyDataModel testBuyDataModel = new TestBuyDataModel();
                    testBuyDataModel.setNamadName(testBuyDataModelList.get(j).getNamadName());
                    testBuyDataModel.setDate(testBuyDataModelList.get(j).getDate());
                    testBuyDataModel.setTedad(testBuyDataModelList.get(j).getTedad());
                    testBuyDataModel.setBuyprice(testBuyDataModelList.get(j).getBuyprice());
                    testBuyDataModel.setNamadCompanyName(namadsDataSampleList.get(i).getNamadCompanyName());
                    testBuyDataModel.setAmount(namadsDataSampleList.get(i).getAmount());
                    testBuyDataModel.setVolume(namadsDataSampleList.get(i).getVolume());
                    testBuyDataModel.setValue(namadsDataSampleList.get(i).getValue());
                    testBuyDataModel.setYesterday(namadsDataSampleList.get(i).getYesterday());
                    testBuyDataModel.setFirstPrice(namadsDataSampleList.get(i).getFirstPrice());
                    testBuyDataModel.setLastDealPriceAmount(namadsDataSampleList.get(i).getLastDealPriceAmount());
                    testBuyDataModel.setLastDealChangePrice(namadsDataSampleList.get(i).getLastDealChangePrice());
                    testBuyDataModel.setLastDealPercentage(namadsDataSampleList.get(i).getLastDealPercentage());
                    testBuyDataModel.setLastPriceAmount(namadsDataSampleList.get(i).getLastPriceAmount());
                    double x = namadsDataSampleList.get(i).getLastPriceAmount();
                    double y = testBuyDataModelList.get(j).getBuyprice();
                    double lastPercentageChange = (x - y) / y * 100;
                    testBuyDataModel.setProfit(lastPercentageChange);
                    testBuyDataModel.setLastPriceChange(namadsDataSampleList.get(i).getLastPriceChange());
                    testBuyDataModel.setLastPricePercentage(namadsDataSampleList.get(i).getLastPricePercentage());
                    testBuyDataModel.setLowestPrice(namadsDataSampleList.get(i).getLowestPrice());
                    testBuyDataModel.setHighestPrice(namadsDataSampleList.get(i).getHighestPrice());
                    testBuyDataModel.setEPS(namadsDataSampleList.get(i).getEPS());
                    testBuyDataModel.setPE(namadsDataSampleList.get(i).getPE());
                    testBuyDataModelList1.add(testBuyDataModel);
                    Log.d(TAG, "success: ");
                }

            }


        }
        testBuyAdapter = new TestBuyAdapter(testBuyDataModelList1, this);
        recyclerView.setAdapter(testBuyAdapter);
        progressBar.setVisibility(View.INVISIBLE);
        button_retry.setVisibility(View.INVISIBLE);
    }

    @Override
    public void Error() {
        progressBar.setVisibility(View.INVISIBLE);
        button_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(TestBuyDataModel testBuyDataModel) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TestBuyDetailFragment testBuyDetailFragment = new TestBuyDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NAMAD_NAME, testBuyDataModel.getNamadName());
        bundle.putString(NAMAD_COMPANY_NAME, testBuyDataModel.getNamadCompanyName());
        bundle.putInt(BUY_PRICE, testBuyDataModel.getBuyprice());
        bundle.putDouble(PROFIT, testBuyDataModel.getProfit());
        bundle.putInt(NUMBER, testBuyDataModel.getTedad());
        bundle.putLong(LAST_PRICE_AMOUNT, testBuyDataModel.getLastPriceAmount());
        bundle.putString(BUY_DATE, testBuyDataModel.getDate());
        testBuyDetailFragment.setArguments(bundle);
        transaction.add(R.id.cl_main_main, testBuyDetailFragment);
        transaction.addToBackStack("testBuyDetailFragment");
        transaction.commit();
    }
}