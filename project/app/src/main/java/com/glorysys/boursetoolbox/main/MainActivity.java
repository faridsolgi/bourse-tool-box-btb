package com.glorysys.boursetoolbox.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.DataBase.DataBaseHelper;
import com.glorysys.boursetoolbox.main.Fragments.AboutUsFragment;
import com.glorysys.boursetoolbox.main.Fragments.CalcProfitFragment;
import com.glorysys.boursetoolbox.main.Fragments.Learn.LearnFragment;
import com.glorysys.boursetoolbox.main.Fragments.NewUpdateFragment;
import com.glorysys.boursetoolbox.main.Fragments.News.NewsMainFragment;
import com.glorysys.boursetoolbox.main.Fragments.PortfolioFragment;
import com.glorysys.boursetoolbox.main.Fragments.ReviewFragment;
import com.glorysys.boursetoolbox.main.Fragments.SplashFragment;
import com.glorysys.boursetoolbox.main.Fragments.Namads.NamadsMainFragment;
import com.glorysys.boursetoolbox.main.Fragments.TestBuy.TestBuySellFragment;
import com.glorysys.boursetoolbox.main.Models.ArzeDataSample;
import com.glorysys.boursetoolbox.main.Models.TseDataSimple;
import com.glorysys.boursetoolbox.main.WebServer.ArzeWebApi;
import com.najva.sdk.NajvaClient;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ArzeWebApi.getApiArzeCallBack, SplashFragment.interface_splash {
    long time = 0;
   private ConstraintLayout constraintLayout;
   private ConstraintSet constraintSet;
    ///
    int review_counter;
    boolean review_show;
    int showint;
    //
    private View view_status;
    private TextView textView_statusText;
    ConstraintLayout constraintLayout_main_arzetimer;
    Button button_calc, button_portfolio,button_filter,button_Aboutus;
    TextView textView_main_apptitle;
    TextView textView_main_bazarstatusnativ;
    TextView textView_ct_day;
    TextView textView_ct_name;
    TextView textView_ct_hour;
    TextView textView_ct_min;
    TextView textView_ct_sec;
    TextView textView_main_bazarstatustimer;
    TextView textView_main_bazarstatus;
    TextView textView_main_shakheskolnativ;
    TextView textView_main_shakheskol;
    TextView textView_main_shakheskolchange;
    TextView textView_main_shakheskolchangepersent;
    TextView textView_main_shakheskolhamvaznnativ;
    TextView textView_main_shakheskolhamvazn;
    TextView textView_main_shakheskolhamvaznchange;
    TextView textView_main_shakheskolhamvaznchangepersent;
    TextView textView_main_arzeshbazarnativ;
    TextView textView_main_arzeshbazar;
    TextView textView_main_priceinfonativ;
    TextView textView_main_priceinfo;
    TextView textView_main_numofexchangenativ;
    TextView textView_main_numofexchange;
    TextView textView_main_valueofexchangenativ;
    TextView textView_main_valueofexchange;
    TextView textView_main_negahbazar;
    TextView textView_main_volofexchangenativ;
    TextView textView_main_volofexchange;
    Button button_main_namads, button_main_news, button_main_testBuy, button_learn;
    private String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateFunction();

        ///////////////najva////////

        ///////////
    }

    public void onCreateFunction() {
        setupItems();
        view_status.setVisibility(View.GONE);
        setSplashFrag();
        webApiInit();
        onClickFragment();
        instagram();
        ////////////////

    }

    private void webApiInit() {
        //  TseWebApi tseWebApi = new TseWebApi(this);
        ArzeWebApi arzeWebApi = new ArzeWebApi(this);
        arzeWebApi.getapiarze(this);
        //  tseWebApi.getapi(this);

    }


    private void setupItems() {
        constraintLayout =findViewById(R.id.cl_mainActivity);
        button_main_testBuy = findViewById(R.id.button_main_testBuy);
        button_learn = findViewById(R.id.button_main_learn);
        button_Aboutus = findViewById(R.id.button_main_About);
        button_filter = findViewById(R.id.button_main_filter);
        button_portfolio = findViewById(R.id.button_main_portfolio);
        button_main_news = findViewById(R.id.button_main_news);
        button_calc = findViewById(R.id.button_main_calc);
        constraintLayout_main_arzetimer = findViewById(R.id.cl_main_arzecl);
        textView_main_apptitle = findViewById(R.id.textView_main_apptitle);
        button_main_namads = findViewById(R.id.Button_main_namads);
        textView_ct_day = findViewById(R.id.textView_counttimer__daynumber);
        textView_ct_name = findViewById(R.id.textView10);
        textView_ct_hour = findViewById(R.id.textView_counttimer_hournumber);
        textView_ct_min = findViewById(R.id.textView_counttimer_minnumber);
        textView_ct_sec = findViewById(R.id.textView_counttimer_secnumber);
        textView_main_bazarstatusnativ = findViewById(R.id.textView_main_bazarstatusnativ);
        textView_main_negahbazar = findViewById(R.id.textView_main_negahbazar);
        textView_main_bazarstatus = findViewById(R.id.textView_main_bazarstatus);
        textView_main_bazarstatustimer = findViewById(R.id.textView_main_bazarstatustimer);
        textView_main_shakheskolnativ = findViewById(R.id.textView_main_shakheskolnativ);
        textView_main_shakheskol = findViewById(R.id.textView_main_shakheskol);
        textView_main_shakheskolchange = findViewById(R.id.textView_main_shakheskolchange);
        textView_main_shakheskolchangepersent = findViewById(R.id.textView_main_shakheskolchangepersent);
        textView_main_shakheskolhamvaznnativ = findViewById(R.id.textView_main_shakheskolhamvaznnativ);
        textView_main_shakheskolhamvazn = findViewById(R.id.textView_main_shakheskolhamvazn);
        textView_main_shakheskolhamvaznchange = findViewById(R.id.textView_main_shakheskolhamvaznchange);
        textView_main_shakheskolhamvaznchangepersent = findViewById(R.id.textView_main_shakheskolhamvaznchangepersent);
        textView_main_arzeshbazarnativ = findViewById(R.id.textView_main_arzeshbazarnativ);
        textView_main_arzeshbazar = findViewById(R.id.textView_main_arzeshbazar);
        textView_main_priceinfonativ = findViewById(R.id.textView_main_priceinfonativ);
        textView_main_priceinfo = findViewById(R.id.textView_main_priceinfo);
        textView_main_numofexchangenativ = findViewById(R.id.textView_main_numofexchangenativ);
        textView_main_numofexchange = findViewById(R.id.textView_main_numofexchange);
        textView_main_valueofexchangenativ = findViewById(R.id.textView_main_valueofexchangenativ);
        textView_main_valueofexchange = findViewById(R.id.textView_main_valueofexchange);
        textView_main_volofexchangenativ = findViewById(R.id.textView_main_volofexchangenativ);
        textView_main_volofexchange = findViewById(R.id.textView_main_volofexchange);
        textView_statusText=findViewById(R.id.textview_main_statustext);
        view_status=findViewById(R.id.view_main_status);

    }


    public void popSplashFrag() {
        try {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack("splash", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
            e.getStackTrace();


        }
    }

    public void setSplashFrag() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SplashFragment splashFragment = new SplashFragment(this);
        transaction.add(R.id.cl_main_main, splashFragment);
        transaction.addToBackStack("splash");
        transaction.commit();
    }

    @Override
    public void arzeSuccess(List<ArzeDataSample> arzeDataSampleList) {
        String aa = arzeDataSampleList.get(0).getCountdown();
        time = Long.valueOf(aa);
        time(time);
        arzeDataParse();

    }

    @Override
    public void arzeError() {
        constraintLayout_main_arzetimer.setVisibility(View.INVISIBLE);
        textView_main_apptitle.setVisibility(View.VISIBLE);
        constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.clear(R.id.view_main_background,ConstraintSet.TOP);
        constraintSet.connect(R.id.view_main_background,ConstraintSet.TOP,R.id.textView_main_apptitle,ConstraintSet.BOTTOM);
        constraintSet.setMargin(R.id.view_main_background,ConstraintSet.TOP,32);
        constraintSet.applyTo(constraintLayout);
    }


    @Override
    public void splash_Success(List<TseDataSimple> dataBazarmainList, String s,long vCode) {
        //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

        textView_main_bazarstatusnativ.setText(dataBazarmainList.get(0).getBazarStatusText());
        textView_main_shakheskolnativ.setText(dataBazarmainList.get(0).getShakhesKolText());
        textView_main_shakheskolhamvaznnativ.setText(dataBazarmainList.get(0).getShakhesKolHamVaznText());
        textView_main_arzeshbazarnativ.setText(dataBazarmainList.get(0).getArzeshBazarText());
        textView_main_priceinfonativ.setText(dataBazarmainList.get(0).getPriceInfoText());
        textView_main_numofexchangenativ.setText(dataBazarmainList.get(0).getNumOfExchangeText());
        textView_main_valueofexchangenativ.setText(dataBazarmainList.get(0).getValueOfExchangeText());
        textView_main_volofexchangenativ.setText(dataBazarmainList.get(0).getVolOfExchangeText());
        textView_main_bazarstatus.setText(dataBazarmainList.get(0).getBazarStatus());
        textView_main_shakheskol.setText(dataBazarmainList.get(0).getShakhesKol());
        textView_main_shakheskolhamvazn.setText(dataBazarmainList.get(0).getShakhesKolHamVazn());
        textView_main_arzeshbazar.setText(dataBazarmainList.get(0).getArzeshBazar());
        textView_main_priceinfo.setText(dataBazarmainList.get(0).getPriceInfo());
        textView_main_numofexchange.setText(dataBazarmainList.get(0).getNumOfExchange());
        textView_main_valueofexchange.setText(dataBazarmainList.get(0).getValueOfExchange());
        textView_main_volofexchange.setText(dataBazarmainList.get(0).getVolOfExchange());
        textView_main_shakheskolchange.setText(dataBazarmainList.get(0).getShakhesKolChange());
        textView_main_shakheskolhamvaznchange.setText(dataBazarmainList.get(0).getShakhesKolHamVaznChange());
        textView_main_negahbazar.setVisibility(View.VISIBLE);
        dataparse();
        dataparsehamvazn();
        popSplashFrag();
        ReviewApp();

         if(vCode>0 && vCode!=-1){
             FragmentManager manager=getSupportFragmentManager();
             FragmentTransaction transaction=manager.beginTransaction();
             NewUpdateFragment newUpdateFragment=new NewUpdateFragment();
             transaction.add(R.id.cl_main_main,newUpdateFragment);
             transaction.commit();

        }






    }

    @Override
    public void splash_Error(String s) {
       // Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

/*
    class CheckConnection extends TimerTask {
        private Context context;

        public CheckConnection(Context context) {
            this.context = context;
        }

        public void run() {

        }
    }*/


    private void clock() {
        final Handler hander = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hander.post(new Runnable() {
                    @Override
                    public void run() {
                        getTime();
                        clock();
                    }
                });
            }
        }).start();
    }


    void getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        textView_main_bazarstatustimer.setText(dateFormat.format(new Date()));
        String status=textView_main_bazarstatus.getText().toString() +"  "+dateFormat.format(new Date());
        view_status.setVisibility(View.VISIBLE);
        if(textView_main_bazarstatus.getText().toString().equals("باز")){
            view_status.setBackgroundColor(getResources().getColor(R.color.green_500));
        }else{
            view_status.setBackgroundColor(getResources().getColor(R.color.red_500));
        }
        textView_statusText.setText(status);
    }

    private void arzeDataParse() {

        //////////////////////DATA PARSE ARZE TIMER///////////////////////////

        String timetoparse = String.valueOf(time);
        // Toast.makeText(this, timetoparse, Toast.LENGTH_SHORT).show();
        String twoFirstCharacterstime = timetoparse.substring(0, 1);
        if (twoFirstCharacterstime.equals("-")) {
            constraintSet = new ConstraintSet();
            constraintLayout_main_arzetimer.setVisibility(View.INVISIBLE);
            textView_main_apptitle.setVisibility(View.VISIBLE);
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.view_main_background,ConstraintSet.TOP);
            constraintSet.connect(R.id.view_main_background,ConstraintSet.TOP,R.id.textView_main_apptitle,ConstraintSet.BOTTOM);
            constraintSet.setMargin(R.id.view_main_background,ConstraintSet.TOP,48);
            constraintSet.applyTo(constraintLayout);
            //TODO:textView_main_apptitle.setSelected(true);
        } else {
            constraintLayout_main_arzetimer.setVisibility(View.VISIBLE);
            textView_main_apptitle.setVisibility(View.INVISIBLE);
        }

    }

    public void dataparse() {
        try {
            clock();
            String example = textView_main_shakheskolchange.getText().toString();
            String kol = textView_main_shakheskol.getText().toString();
            String beforeFirstDot = kol.split("\\.")[0];
            textView_main_shakheskol.setText(beforeFirstDot);
            ////replace &nbsp;
            String repnbsp = textView_main_bazarstatus.getText().toString();
            repnbsp = repnbsp.replace("&nbsp;", "");
            textView_main_bazarstatus.setText(repnbsp);


//PERCENT
            String a = beforeFirstDot.replaceAll(",", "");
            String aa = example.replace("(", "");
            String aa2 = aa.replace(")", "");
            float i = Float.parseFloat(aa2);
            float x = Float.parseFloat(a);
            float y = ((x + i) - x) / x * 100;
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);


            String getY = String.valueOf(df.format(y) + "%");
            textView_main_shakheskolchangepersent.setText(getY);
            String twoFirstCharacters = example.substring(0, 1);
            if (twoFirstCharacters.equals("-")) {

                String finaltext = example.substring(example.lastIndexOf("-") + 1);
                textView_main_shakheskolchangepersent.setTextColor(getResources().getColor(R.color.red_400));
                textView_main_shakheskolchange.setText(finaltext);
                textView_main_shakheskolchange.setTextColor(getResources().getColor(R.color.red_400));
            } else {

                textView_main_shakheskolchange.setText(example);
                textView_main_shakheskolchangepersent.setTextColor(getResources().getColor(R.color.green_400));
                textView_main_shakheskolchange.setTextColor(getResources().getColor(R.color.green_400));
            }


        } catch (Exception e) {
            e.getStackTrace();
        }


    }


    public void dataparsehamvazn() {
        String example = textView_main_shakheskolhamvaznchange.getText().toString();
        String kol = textView_main_shakheskolhamvazn.getText().toString();
        String beforeFirstDot = kol.split("\\.")[0];
        textView_main_shakheskolhamvazn.setText(beforeFirstDot);
        //PERCENT
        String a = beforeFirstDot.replaceAll(",", "");
        String aa = example.replace("(", "");
        String aa2 = aa.replace(")", "");
        float i = Float.parseFloat(aa2);
        float x = Float.parseFloat(a);
        float y = ((x + i) - x) / x * 100;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        String getY = String.valueOf(df.format(y) + "%");
        textView_main_shakheskolhamvaznchangepersent.setText(getY);


        String twoFirstCharacters = example.substring(0, 1);
        if (twoFirstCharacters.equals("-")) {

            String finaltext = example.substring(example.lastIndexOf("-") + 1);
            textView_main_shakheskolhamvaznchangepersent.setTextColor(getResources().getColor(R.color.red_400));
            textView_main_shakheskolhamvaznchange.setText(finaltext);
            textView_main_shakheskolhamvaznchange.setTextColor(getResources().getColor(R.color.red_400));
        } else {

            textView_main_shakheskolhamvaznchange.setText(example);
            textView_main_shakheskolhamvaznchangepersent.setTextColor(getResources().getColor(R.color.green_400));
            textView_main_shakheskolhamvaznchange.setTextColor(getResources().getColor(R.color.green_400));
        }


    }

///////////////Arze avalie web api///////////////////////


    //////////////////////     timer    ////////////////////////////
    private void time(long ftime) {
        new CountDownTimer(ftime, 1000) {

            public void onTick(long millisUntilFinished) {
                long secs = millisUntilFinished / 1000;
                long mins = secs / 60;
                long hours = secs / 3600;
                long days = secs / (3600 * 24);
                secs = secs % 60;
                mins = mins % 60;
                hours = hours % 24;
                days = days % 30;

                textView_ct_day.setText(days + "");
                textView_ct_hour.setText(hours + "");
                textView_ct_min.setText(mins + "");
                textView_ct_sec.setText(secs + "");


            }

            public void onFinish() {
                //  nameOfComp.setText("done!");
            }
        }.start();

    }


    private void onClickFragment() {
        button_main_namads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                NamadsMainFragment namadsMainFragment = new NamadsMainFragment();
                transaction.add(R.id.cl_main_main, namadsMainFragment);
                transaction.addToBackStack("namadslist");
                transaction.commit();

            }
        });
        ///////////////////
        button_main_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                NewsMainFragment newsMainFragment = new NewsMainFragment();
                fragmentTransaction.add(R.id.cl_main_main, newsMainFragment);
                fragmentTransaction.addToBackStack("newsfragment");
                fragmentTransaction.commit();
            }
        });
        ///////////////////
        button_main_testBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                TestBuySellFragment testBuyFragment = new TestBuySellFragment();
                transaction.add(R.id.cl_main_main, testBuyFragment);
                transaction.addToBackStack("TestBuyFragment");
                transaction.commit();
            }
        });
        /////////////
        button_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                PortfolioFragment portfolioFragment = new PortfolioFragment();
                transaction.add(R.id.cl_main_main, portfolioFragment);
                transaction.addToBackStack("portfolioFragment");
                transaction.commit();
            }
        });
        /////////////
        button_learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                LearnFragment learnFragment = new LearnFragment();
                transaction.add(R.id.cl_main_main, learnFragment);
                transaction.addToBackStack("learnFragment");
                transaction.commit();
            }
        });     /////////////
        button_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                CalcProfitFragment calcProfitFragment = new CalcProfitFragment();
                transaction.add(R.id.cl_main_main, calcProfitFragment);
                transaction.addToBackStack("calcProfitFragment");
                transaction.commit();
            }
        });
        //////////
        button_Aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                AboutUsFragment aboutUsFragment=new AboutUsFragment();
                transaction.add(R.id.cl_main_main,aboutUsFragment);
                transaction.addToBackStack("aboutus");
                transaction.commit();
            }
        });
    }

    //////////////////////////////////////////////////
    //Review APP
    /////////////////////////////////////////////////
    private void ReviewApp() {


        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        if(dataBaseHelper.Exists_review("review")){
            Cursor cursor = dataBaseHelper.ReadData_Table_Review("review");
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    review_counter = cursor.getInt(1);
                    showint = cursor.getInt(2);
                }
                if(showint==1){
                    review_show=true;
                }else{review_show=false;}
                if (review_counter == 4 && review_show) {
////////////////show review to user
                    PackageManager pm=getApplicationContext().getPackageManager();
                    Boolean isPackageInstalled=isPackageInstalled("com.farsitel.bazaar",pm);
                   if(isPackageInstalled){

                       FragmentManager manager=getSupportFragmentManager();
                       FragmentTransaction transaction=manager.beginTransaction();
                       ReviewFragment reviewFragment=new ReviewFragment();
                       transaction.add(R.id.cl_main_main,reviewFragment);
                       transaction.addToBackStack("reviewFragment");
                       transaction.commit();


                   }
                }else if(review_counter<4) {
                    review_counter++;
                    dataBaseHelper.DB_Update_Review("review", review_counter, showint);
                }
            }


        }else{
            dataBaseHelper.DB_Write_Review("review",0,1);
        }






     //




    }



    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

private void instagram(){
button_filter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try{
            Uri uri = Uri.parse("http://instagram.com/boursetoolbox");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.instagram.android");
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            e.getStackTrace();
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://ig.me/boursetoolbox")));
        }
    }
});




}

}


