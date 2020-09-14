package com.glorysys.boursetoolbox.main.Fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Adapter.PortfolioAdapter;
import com.glorysys.boursetoolbox.main.DataBase.DataBaseHelper;
import com.glorysys.boursetoolbox.main.Fragments.Namads.NamadDetailFragment;
import com.glorysys.boursetoolbox.main.Models.NamadsDataSample;
import com.glorysys.boursetoolbox.main.Models.TestBuyDataModel;
import com.glorysys.boursetoolbox.main.WebServer.NamadWebApi;

import java.util.ArrayList;
import java.util.List;

public class PortfolioFragment extends Fragment implements ConfirmDialogFragment.interface_confirmDialog,NamadWebApi.getApiCallBack, PortfolioAdapter.PortfolioViewHolder.onClickListener {
    private List<NamadsDataSample> namadsDataSampleList;
    private List<NamadsDataSample> namadsDataSampleList1;
    RecyclerView recyclerView;
    DataBaseHelper dataBaseHelper;
    PortfolioAdapter portfolioAdapter;
    private Button button_back, button_retry;
    private LottieAnimationView progressBar;
    private ConstraintLayout constraintLayout_NoData;
    private static final String TAG = "PortfolioFragment";
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
     String str_namadname = "";
int ctn;
    public PortfolioFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);
        SetupViews(view);
        getDataFromDataBase();
        button_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator=  ObjectAnimator.ofFloat(button_retry, "rotation", 0f, 360f);
                animator.setRepeatCount(2);
                animator.setDuration(400);
                ctn=animator.getRepeatCount();
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
                            manager.popBackStack("portfolioFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            PortfolioFragment fragment = new PortfolioFragment();
                            transaction.add(R.id.cl_main_main, fragment);
                            transaction.addToBackStack("portfolioFragment");
                            transaction.commit();
                            button_retry.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                        }catch (NullPointerException e){
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
        //////////////
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager=getFragmentManager();
                manager.popBackStack("portfolioFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return view;
    }

    private void SetupViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_portfolio_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        progressBar = view.findViewById(R.id.progressbar_portfolio_progressbar);
        constraintLayout_NoData = view.findViewById(R.id.cl_portfolio_clNoData);
        button_back = view.findViewById(R.id.button_portfolio_back);
        button_retry = view.findViewById(R.id.button_portfolio_retry);
    }

    public void getDataFromDataBase() {
        dataBaseHelper = new DataBaseHelper(getContext());
        namadsDataSampleList = new ArrayList<>();
        Cursor cursor = dataBaseHelper.ReadAllData_Table_portfolio();
        if (cursor.getCount() == 0) {
            ///////empty portfolio//////
            button_retry.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            constraintLayout_NoData.setVisibility(View.VISIBLE);
        } else {
            constraintLayout_NoData.setVisibility(View.INVISIBLE);
            while (cursor.moveToNext()) {
                NamadsDataSample namadsDataSample = new NamadsDataSample();
                namadsDataSample.setNamadName(cursor.getString(0));
                namadsDataSampleList.add(namadsDataSample);
            }
            NamadWebApi namadWebApi = new NamadWebApi(getContext());
            namadWebApi.getapi(this);
        }

    }

    @Override
    public void Success(List<NamadsDataSample> namadsDataSampleList) {
        namadsDataSampleList1 = new ArrayList<>();
        for (int i = 0; i < namadsDataSampleList.size(); i++) {
            for (int j = 0; j < this.namadsDataSampleList.size(); j++) {
                String B = namadsDataSampleList.get(i).getNamadName();
                String A = this.namadsDataSampleList.get(j).getNamadName();
                if (B.equals(A)) {
                    NamadsDataSample namadsDataSample = new NamadsDataSample();
                    namadsDataSample.setNamadName(this.namadsDataSampleList.get(j).getNamadName());
                    namadsDataSample.setNamadCompanyName(namadsDataSampleList.get(i).getNamadCompanyName());
                    namadsDataSample.setAmount(namadsDataSampleList.get(i).getAmount());
                    namadsDataSample.setVolume(namadsDataSampleList.get(i).getVolume());
                    namadsDataSample.setValue(namadsDataSampleList.get(i).getValue());
                    namadsDataSample.setYesterday(namadsDataSampleList.get(i).getYesterday());
                    namadsDataSample.setFirstPrice(namadsDataSampleList.get(i).getFirstPrice());
                    namadsDataSample.setLastDealPriceAmount(namadsDataSampleList.get(i).getLastDealPriceAmount());
                    namadsDataSample.setLastDealChangePrice(namadsDataSampleList.get(i).getLastDealChangePrice());
                    namadsDataSample.setLastDealPercentage(namadsDataSampleList.get(i).getLastDealPercentage());
                    namadsDataSample.setLastPriceAmount(namadsDataSampleList.get(i).getLastPriceAmount());
                    namadsDataSample.setLastPriceChange(namadsDataSampleList.get(i).getLastPriceChange());
                    namadsDataSample.setLastPricePercentage(namadsDataSampleList.get(i).getLastPricePercentage());
                    namadsDataSample.setLowestPrice(namadsDataSampleList.get(i).getLowestPrice());
                    namadsDataSample.setHighestPrice(namadsDataSampleList.get(i).getHighestPrice());
                    namadsDataSample.setBuy(namadsDataSampleList.get(i).getBuy());
                    namadsDataSample.setSell(namadsDataSampleList.get(i).getSell());
                    namadsDataSample.setEPS(namadsDataSampleList.get(i).getEPS());
                    namadsDataSample.setPE(namadsDataSampleList.get(i).getPE());
                    namadsDataSampleList1.add(namadsDataSample);
                    Log.d(TAG, "Success: ");
                } else {
                    Log.d(TAG, "no succcess: ");

                }
            }

        }
        portfolioAdapter = new PortfolioAdapter(namadsDataSampleList1, this);
        recyclerView.setAdapter(portfolioAdapter);
        progressBar.setVisibility(View.INVISIBLE);
        button_retry.setVisibility(View.INVISIBLE);
    }

    @Override
    public void Error() {
        progressBar.setVisibility(View.INVISIBLE);
        button_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPortfolioItemClick(NamadsDataSample namadsDataSample) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        NamadDetailFragment namadDetailFragment = new NamadDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NAMAD_NAME, namadsDataSample.getNamadName()); // Put anything what you want
        bundle.putString(NAMAD_COMPANY_NAME, namadsDataSample.getNamadCompanyName()); // Put anything what you want
        bundle.putString(AMOUNT, String.valueOf(namadsDataSample.getAmount())); // Put anything what you want
        bundle.putString(VOLUME, String.valueOf(namadsDataSample.getVolume())); // Put anything what you want
        bundle.putString(VALUE, String.valueOf(namadsDataSample.getValue())); // Put anything what you want
        bundle.putString(YESTERDAY, String.valueOf(namadsDataSample.getYesterday())); // Put anything what you want
        bundle.putString(FIRST_PRICE, String.valueOf(namadsDataSample.getFirstPrice())); // Put anything what you want
        bundle.putString(LAST_DEAL_PRICE_AMOUNT, String.valueOf(namadsDataSample.getLastDealPriceAmount())); // Put anything what you want
        bundle.putString(LAST_DEAL_CHANGE_PRICE, String.valueOf(namadsDataSample.getLastDealChangePrice())); // Put anything what you want
        bundle.putString(LAST_DEAL_PERCENTAGE,String.valueOf(namadsDataSample.getLastDealPercentage())); // Put anything what you want
        bundle.putString(LAST_PRICE_AMOUNT, String.valueOf(namadsDataSample.getLastPriceAmount())); // Put anything what you want
        bundle.putString(LAST_PRICE_CHANGE, String.valueOf(namadsDataSample.getLastPriceChange())); // Put anything what you want
        bundle.putString(LAST_PRICE_PERCENTAGE, String.valueOf(namadsDataSample.getLastPricePercentage())); // Put anything what you want
        bundle.putString(LOWEST_PRICE, String.valueOf(namadsDataSample.getLowestPrice())); // Put anything what you want
        bundle.putString(HIGHEST_PRICE, String.valueOf(namadsDataSample.getHighestPrice())); // Put anything what you want
        bundle.putString(EPS, namadsDataSample.getEPS()); // Put anything what you want
        bundle.putString(PE, namadsDataSample.getPE()); // Put anything what you want
        bundle.putString(BUY, namadsDataSample.getBuy()); // Put anything what you want
        bundle.putString(SELL, namadsDataSample.getSell()); // Put anything what you want
        namadDetailFragment.setArguments(bundle);
        transaction.add(R.id.cl_main_main, namadDetailFragment);
        transaction.addToBackStack("namaddetail");
        transaction.commit();
    }
ConfirmDialogFragment confirmDialogFragment=new ConfirmDialogFragment(this);
    @Override
    public void onPortfolioFavButtonClick(NamadsDataSample namadsDataSample) {
    str_namadname=namadsDataSample.getNamadName();
        String name=str_namadname;
        String description=" آیا قصد حذف نماد "+name+" از پرتفوی خود را دارید؟ ";
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

    @Override
    public void ConfirmDialog() {

        dataBaseHelper.DB_Portfolio(str_namadname);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        manager.popBackStack("portfolioFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        PortfolioFragment fragment = new PortfolioFragment();
        transaction.add(R.id.cl_main_main, fragment);
        transaction.addToBackStack("portfolioFragment");
        transaction.commit();
    }

    @Override
    public void CancelDialog() {
    FragmentManager fragmentManager=getFragmentManager();
    fragmentManager.popBackStack("confirmDialogFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}