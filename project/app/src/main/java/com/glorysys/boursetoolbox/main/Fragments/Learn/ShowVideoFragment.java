package com.glorysys.boursetoolbox.main.Fragments.Learn;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.ShowVideoActivity;

import java.io.IOException;

public class ShowVideoFragment extends Fragment  {
private LottieAnimationView progressBar;
private ImageView imageView;
//private Button button_retry;
private WebView webView;
private String url;
private  int ctn;
    public ShowVideoFragment() {
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
        View view=inflater.inflate(R.layout.fragment_show_video, container, false);
    setupViews(view);


        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                try{
                    getActivity().finish();
                }catch (Exception e){
                    e.getStackTrace();
                }

            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);


        ////////
        Bundle bundle=this.getArguments();
        if(bundle!=null) {
            url = bundle.getString("url");
            /////
            progressBar.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        //    button_retry.setVisibility(View.INVISIBLE);
            webView.setVisibility(View.INVISIBLE);
            if (isOnline()) {
                if (savedInstanceState == null) {
                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            progressBar.setVisibility(View.INVISIBLE);
                            imageView.setVisibility(View.INVISIBLE);
                            webView.setVisibility(View.VISIBLE);
                        }
                    });
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                    webView.getSettings().setPluginState(WebSettings.PluginState.ON);
                    webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
                    webView.setWebChromeClient(new WebChromeClient());
                    webView.loadUrl(url);
                }
            } else {
             //   button_retry.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                try {
                    getActivity().finish();
                    Toast.makeText(getContext(), getResources().getString(R.string.errorConnection), Toast.LENGTH_SHORT).show();

                }catch (Exception e){e.getStackTrace();}


            }
        }else{

            try {
                getActivity().finish();
                Toast.makeText(getContext(), getResources().getString(R.string.errorConnection), Toast.LENGTH_SHORT).show();

            }catch (Exception e){e.getStackTrace();}


        }





/////retry/////
      /*  button_retry.setOnClickListener(new View.OnClickListener() {
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
                            FragmentManager manager=getFragmentManager();
                            FragmentTransaction transaction= manager.beginTransaction();
                            ShowVideoFragment showVideoFragment=new ShowVideoFragment();
                            manager.popBackStack("showVideoFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            transaction.add(R.id.cl_showvideo_clfragment,showVideoFragment);
                            transaction.addToBackStack("showVideoFragment");
                            transaction.commit();
                            button_retry.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
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
        });*/

                return view;
    }

    private void setupViews(View view) {
        progressBar=view.findViewById(R.id.progressbar_videoShow_progressBar);
        webView = view.findViewById(R.id.webview_show);
        imageView=view.findViewById(R.id.imageView_videoShow_logo);
      //  button_retry=view.findViewById(R.id.button_videoShow_retry);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        webView.saveState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        webView.saveState(savedInstanceState);
    }

    private boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }


}