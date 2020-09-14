package com.glorysys.boursetoolbox.main.Fragments.Learn;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.glorysys.boursetoolbox.ShowVideoActivity;
import com.glorysys.boursetoolbox.main.Adapter.AparatAdapter;
import com.glorysys.boursetoolbox.main.Models.AparatDataModel;
import com.glorysys.boursetoolbox.main.Models.NamadsDataSample;
import com.glorysys.boursetoolbox.main.WebServer.AparatWepApi;

import java.util.ArrayList;
import java.util.List;


public class LearnFragment extends Fragment implements AparatWepApi.getVideoFromAparat, AparatAdapter.AparatViewHolder.setOnVideosClickListener {
    private static final String TAG = "LearnFragment";
    List<AparatDataModel> aparatDataModelList1;
    List<AparatDataModel> aparatDataModelList100;
    List<AparatDataModel> aparatDataModelList200;
    List<AparatDataModel> aparatDataModelList300;
    List<AparatDataModel> aparatDataModelList400;
    List<AparatDataModel> aparatDataModelListFinal;
    private Button button_back,button_retry,ButtonSearch,ButtonSearchClose;
    private RecyclerView recyclerView;
    int i=100;
    AparatAdapter aparatAdapter;
    private EditText editText_searchBox;
    TextView textView_title;
    private LottieAnimationView progressBar;
private int ctn;
    public LearnFragment() {
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
        View view = inflater.inflate(R.layout.fragment_learn, container, false);
        setupViews(view);
        this.aparatDataModelList1 = new ArrayList<>();
        this.aparatDataModelList100  = new ArrayList<>();
        this.aparatDataModelList200  = new ArrayList<>();
        this.aparatDataModelList300  = new ArrayList<>();
        this.aparatDataModelList400 = new ArrayList<>();
        this.aparatDataModelListFinal = new ArrayList<>();
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

                            AparatWepApi aparatWepApi = new AparatWepApi(getContext());
                            aparatWepApi.getVideos(1, LearnFragment.this);
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


        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        AparatWepApi aparatWepApi = new AparatWepApi(getContext());
        // for (int i = 0; i <= 400; i+=100) {
        aparatWepApi.getVideos(1, this);

        // }



        ////////////////search

        /////////
        ButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_title.setVisibility(View.INVISIBLE);
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
                textView_title.setVisibility(View.VISIBLE);
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

        return view;
    }

    private void setupViews(View view) {
        button_back = view.findViewById(R.id.button_learn_back);
        recyclerView = view.findViewById(R.id.rv_learn_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        progressBar = view.findViewById(R.id.progressbar_learn_progressbar);
        progressBar.setVisibility(View.VISIBLE);
        button_retry=view.findViewById(R.id.button_learn_retry);
        button_retry.setVisibility(View.INVISIBLE);
        ButtonSearch=view.findViewById(R.id.button_learn_search);
        ButtonSearchClose=view.findViewById(R.id.button_learn_searchClose);
        editText_searchBox=view.findViewById(R.id.editText_learn_searchBox);
        textView_title=view.findViewById(R.id.textview_learn_title);

    }

    @Override
    public void AparatVideo_success(List<AparatDataModel> aparatDataModelList,int page) {


        AparatWepApi aparatWepApi = new AparatWepApi(getContext());

        if(i==100){
            aparatWepApi.getVideos(100, this);
            i=200;
        }else if(i==200){

            aparatWepApi.getVideos(200, this);
            i=300;
        }else if(i==300){
            aparatWepApi.getVideos(300, this);
            i=400;
        }else if(i==400){
            aparatWepApi.getVideos(400, this);
            i=500;
        }
        if(page==1){
            aparatDataModelList1=aparatDataModelList;
        }else if(page==100){
            aparatDataModelList100=aparatDataModelList;
        }else if(page==200){
            aparatDataModelList200=aparatDataModelList;

        }else if(page==300){
            aparatDataModelList300=aparatDataModelList;

        }else if(page==400){
            aparatDataModelList400=aparatDataModelList;
            aparatDataModelList400.addAll(aparatDataModelList1);
            aparatDataModelList400.addAll(aparatDataModelList100);
            aparatDataModelList400.addAll(aparatDataModelList200);
            aparatDataModelList400.addAll(aparatDataModelList300);
            aparatAdapter = new AparatAdapter(aparatDataModelList400, this);
            recyclerView.setAdapter(aparatAdapter);
            progressBar.setVisibility(View.INVISIBLE);
            button_retry.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void AparatVideo_Error() {
        progressBar.setVisibility(View.INVISIBLE);
        button_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnVideoClick(AparatDataModel aparatDataModel) {
        Intent intent = new Intent(getActivity(), ShowVideoActivity.class);
        intent.putExtra("url", aparatDataModel.getUrl());
        startActivity(intent);



    }









    public void filter(String s) {
        List<AparatDataModel> aparatDataModelList1 = new ArrayList<>();
        for (AparatDataModel item : aparatDataModelList400) {
            if (item.getTitle().toLowerCase().contains(s.toLowerCase())) {
                aparatDataModelList1.add(item);
            }
        }
        aparatAdapter.filter(aparatDataModelList1);
        aparatAdapter.notifyDataSetChanged();
    }









    ///////////////////////////////hide keyboard/////////////////////////////////////
    public void hideSoftKeyboard(Context activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
/*        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);*/
        inputMethodManager.hideSoftInputFromWindow(editText_searchBox.getWindowToken(), 0);
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



}