package com.glorysys.boursetoolbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.glorysys.boursetoolbox.main.Fragments.Learn.ShowVideoFragment;

import java.io.IOException;

public class ShowVideoActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    private Button button_retry;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        Intent intent=getIntent();
        url=intent.getStringExtra("url");

        try {
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            ShowVideoFragment showVideoFragment=new ShowVideoFragment();
            Bundle bundle=new Bundle();
            bundle.putString("url",url);
            showVideoFragment.setArguments(bundle);
            transaction.add(R.id.cl_showvideo_clfragment,showVideoFragment);
            transaction.addToBackStack("showVideoFragment");
            transaction.commit();

        }catch (Exception e){
            e.getStackTrace();
        }

      /*  ////////
        progressBar=findViewById(R.id.progressbar_videoShow_progressBar);
        webView = findViewById(R.id.webview_show);
        button_retry=findViewById(R.id.button_videoShow_retry);
        /////
        progressBar.setVisibility(View.VISIBLE);
        button_retry.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.INVISIBLE);
        if(isOnline()){
            if (savedInstanceState == null)
            {

                webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        progressBar.setVisibility(View.INVISIBLE);
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
        }else{
            button_retry.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }


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
                        try{
                            Intent intent = getIntent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                            finish();
                            startActivity(intent);

                            progressBar.setVisibility(View.VISIBLE);
                            button_retry.setVisibility(View.INVISIBLE);
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
*/

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            webView.saveState(outState);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    public boolean isOnline() {

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
