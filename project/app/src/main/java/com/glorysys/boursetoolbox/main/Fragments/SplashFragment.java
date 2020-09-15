package com.glorysys.boursetoolbox.main.Fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.farsitel.bazaar.IUpdateCheckService;
import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Fragments.Learn.LearnFragment;
import com.glorysys.boursetoolbox.main.MainActivity;
import com.glorysys.boursetoolbox.main.Models.TseDataSimple;
import com.glorysys.boursetoolbox.main.WebServer.AparatWepApi;
import com.glorysys.boursetoolbox.main.WebServer.TseWebApi;

import java.io.IOException;
import java.util.List;


public class SplashFragment extends Fragment implements TseWebApi.getApiCallBack {
    Button button_retry,button_close;
    private interface_splash test;
    LottieAnimationView progressBar;
    public long long_versioncode;
int ctn;
long vCode;


    public SplashFragment(interface_splash test) {
        // Required empty public constructor
        this.test = test;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        button_retry = view.findViewById(R.id.button_splash_retry_fragment);
        button_close = view.findViewById(R.id.button_splash_close);
        progressBar = view.findViewById(R.id.progressBar_splash_proggrasbar);
        progressBar.setVisibility(View.VISIBLE);
        button_close.setVisibility(View.INVISIBLE);
        button_retry.setVisibility(View.INVISIBLE);
        TseWebApi tseWebApi = new TseWebApi(getContext());
        tseWebApi.getapi(this);
        initService();

            OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {

                }
            };

            requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);



        return view;
    }


    public void click() {
        //  Toast.makeText(getActivity(), "You aren't connected to the internet.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void Success(List<TseDataSimple> dataBazarmainList) {
        test.splash_Success(dataBazarmainList, "success test",vCode);
        progressBar.setVisibility(View.INVISIBLE);
        button_close.setVisibility(View.INVISIBLE);

    }

    @Override
    public void Error() {
        test.splash_Error("unsuccess test");
        retry();

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

    public void retry() {
        progressBar.setVisibility(View.INVISIBLE);
        button_close.setVisibility(View.VISIBLE);
        button_retry.setVisibility(View.VISIBLE);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        //////
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
                            SplashFragment splashFragment = new SplashFragment(test);
                            manager.popBackStack("splash", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            transaction.replace(R.id.cl_main_main, splashFragment);
                            transaction.addToBackStack("splash");
                            transaction.commit();
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
    }


    public interface interface_splash {
        void splash_Success(List<TseDataSimple> tseDataSimples, String s,long Vcode);

        void splash_Error(String s);
    }






    IUpdateCheckService service;
    UpdateServiceConnection connection;
    private static final String TAG = "UpdateCheck";

    class UpdateServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IUpdateCheckService.Stub
                    .asInterface((IBinder) boundService);
            try {
                String pkgName=getActivity().getPackageName();
                 vCode =    service.getVersionCode(pkgName);

                //Toast.makeText(getContext(), "Version Code:" + vCode,Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onServiceConnected(): Connected");
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Log.d(TAG, "onServiceDisconnected(): Disconnected");
        }
    }


    private void initService() {
        Log.i(TAG, "initService()");
        connection = new UpdateServiceConnection();
        Intent i = new Intent(
                "com.farsitel.bazaar.service.UpdateCheckService.BIND");
        i.setPackage("com.farsitel.bazaar");
        boolean ret = getActivity().bindService(i, connection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "initService() bound value: " + ret);

    }



    /** This is our function to un-binds this activity from our service. */
    private void releaseService() {
        getActivity().unbindService(connection);
        connection = null;
        Log.d(TAG, "releaseService(): unbound.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseService();
    }

}