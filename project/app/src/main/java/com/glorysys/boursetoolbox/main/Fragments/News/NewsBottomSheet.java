package com.glorysys.boursetoolbox.main.Fragments.News;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Models.ObservableWebView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewsBottomSheet extends BottomSheetDialogFragment {
    private LottieAnimationView progressBar;
    private ObservableWebView wb;
    private AppBarLayout appBarLayout;
    private ConstraintLayout constraintLayout;
    private static final String TAG = "NewsBottomSheet";
    public NewsBottomSheet() {
    }

/*    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      final   BottomSheetDialog dialog= (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        final View view=View.inflate(getContext(),R.layout.news_bottom_sheet,null);
        dialog.setContentView(view);
        String s="https://google.com";
        final BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(bottomSheetBehavior.PEEK_HEIGHT_AUTO);
        constraintLayout=view.findViewById(R.id.bottomsheetfram);
  *//*      wb = view.findViewById(R.id.scrollablewebview);
        wb.setWebViewClient(new WebViewClient());
        wb.getSettings().setJavaScriptEnabled(true);
        Bundle bundle=this.getArguments();
     *//**//*   if(bundle!=null){
            s=bundle.getString("url");
            wb.loadUrl(s);
        }*//**//*
        wb.loadUrl("https://google.com");
        wb.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback(){
            public void onScroll(int l, int t){
                //Do stuff
                Log.d(TAG,"We Scrolled etc...");
            }
        });
*//*



        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(bottomSheetBehavior.STATE_EXPANDED==newState){

                }

                if (bottomSheetBehavior.STATE_COLLAPSED==newState){

                }
                if (bottomSheetBehavior.STATE_HIDDEN==newState){
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        return dialog;

    }
      */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_bottom_sheet,container,false);
        progressBar=view.findViewById(R.id.progressbar_newsbottomsheet_progressbar);
        String s="google.com";
        wb=view.findViewById(R.id.scorllableWebview);
        progressBar.setVisibility(View.VISIBLE);
        wb.setVisibility(View.INVISIBLE);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
                wb.setVisibility(View.VISIBLE);
            }
        });
        wb.getSettings().setJavaScriptEnabled(true);

        Bundle bundle=this.getArguments();
        if(bundle!=null){
            s=bundle.getString("url");
            wb.loadUrl(s);
        }
        wb.loadUrl(s);
        wb.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback(){
            public void onScroll(int l, int t){
                //Do stuff
            }
        });


        return view;
    }

}
