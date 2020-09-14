package com.glorysys.boursetoolbox.main.Fragments.Namads;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Adapter.NamadsAdapter;
import com.glorysys.boursetoolbox.main.Fragments.Learn.LearnFragment;
import com.glorysys.boursetoolbox.main.WebServer.AparatWepApi;
import com.glorysys.boursetoolbox.main.WebServer.NamadWebApi;
import com.glorysys.boursetoolbox.main.Models.NamadsDataSample;

import java.util.ArrayList;
import java.util.List;

public class NamadsMainFragment extends Fragment implements NamadWebApi.getApiCallBack, NamadsAdapter.NamadViewHolder.onNamadsClick {
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

    RecyclerView recyclerView;
    LottieAnimationView progressBar;
    Button ButtonBack,button_retry;
    EditText editText_searchBox;
    Button ButtonSearch;
    Button ButtonSearchClose;
    TextView textView_NamadList_titleoffragment;
    List<NamadsDataSample> mnamadsDataSampleList;
    private NamadsAdapter namadsAdapter;
        int ctn;
    public NamadsMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NamadWebApi webApi = new NamadWebApi(getActivity());
        webApi.getapi(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_namads_main, container, false);
        onCreateViewFunction(view);

        return view;
    }


    public void onCreateViewFunction(View view) {
        setupViews(view);
        progressBar.setVisibility(View.VISIBLE);
        button_retry.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popSplashFrag();
            }
        });
        /////////////
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
                            NamadWebApi webApi = new NamadWebApi(getActivity());
                            webApi.getapi(NamadsMainFragment.this);
                            button_retry.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                        }catch (Exception e){
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
/////////
        ButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_NamadList_titleoffragment.setVisibility(View.INVISIBLE);
                editText_searchBox.setVisibility(View.VISIBLE);
                ButtonSearchClose.setVisibility(View.VISIBLE);
                editText_searchBox.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText_searchBox, InputMethodManager.SHOW_IMPLICIT);

            }
        });
/////keyboard hide////
        setupUI(view);
///////////////
        ButtonSearchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_searchBox.setText("");
                textView_NamadList_titleoffragment.setVisibility(View.VISIBLE);
                editText_searchBox.setVisibility(View.INVISIBLE);
                ButtonSearchClose.setVisibility(View.INVISIBLE);

            }
        });
////////


///////
        editText_searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }
        });
    }


    public void filter(String s) {
        List<NamadsDataSample> namadsDataSamplesfilter = new ArrayList<>();
        for (NamadsDataSample item : mnamadsDataSampleList) {
            if (item.getNamadName().toLowerCase().contains(s.toLowerCase())) {
                namadsDataSamplesfilter.add(item);
            }
        }
        namadsAdapter.filter(namadsDataSamplesfilter);
        namadsAdapter.notifyDataSetChanged();
    }


    public void setupViews(View view) {
        recyclerView = view.findViewById(R.id.RV_Namadlist_RV);
        progressBar = view.findViewById(R.id.progressbar_NamadList_progressbar);
        ButtonBack = view.findViewById(R.id.button_NamadList_back);
        button_retry = view.findViewById(R.id.button_NamadList_retry);
        ButtonSearch = view.findViewById(R.id.button_NamadList_search);
        ButtonSearchClose = view.findViewById(R.id.button_NamadList_searchClose);
        editText_searchBox = view.findViewById(R.id.editText_NamadList_searchBox);
        textView_NamadList_titleoffragment = view.findViewById(R.id.textView_NamadList_titleoffragment);
    }


    @Override
    public void Success(List<NamadsDataSample> namadsDataSampleList) {
        mnamadsDataSampleList = namadsDataSampleList;
        namadsAdapter = new NamadsAdapter(namadsDataSampleList, this);
        recyclerView.setAdapter(namadsAdapter);
        // Toast.makeText(getContext(), "finne", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        button_retry.setVisibility(View.INVISIBLE);
    }


    @Override
    public void Error() {
        progressBar.setVisibility(View.GONE);
        button_retry.setVisibility(View.VISIBLE);
    }


    public void popSplashFrag() {
        try {
            FragmentManager fm =getFragmentManager();
            fm.popBackStack("namadslist", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
            e.getStackTrace();


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
            inputMethodManager.hideSoftInputFromWindow(editText_searchBox.getWindowToken(), 0);

        }catch (Exception e){e.getStackTrace();}
      }


    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getContext());
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }


    @Override
    public void click(NamadsDataSample namadsDataSample) {
        hideSoftKeyboard(getContext());
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


        //  Toast.makeText(getContext(), "position " + namadsDataSample.getNamadName(), Toast.LENGTH_SHORT).show();
    }




}