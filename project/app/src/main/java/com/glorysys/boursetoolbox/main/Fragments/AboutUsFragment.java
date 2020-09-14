package com.glorysys.boursetoolbox.main.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.glorysys.boursetoolbox.BuildConfig;
import com.glorysys.boursetoolbox.R;

public class AboutUsFragment extends Fragment {
Button button_back,button_instagram,button_mail;
TextView textView_version,textView_aparat,textView_tsetmc,textView_isna;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_us, container, false);
        setupView(view);
        String version= BuildConfig.VERSION_NAME;
textView_version.setText(" ورژن : "+version);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager=getFragmentManager();
                manager.popBackStack();
            }
        });
        ////
        button_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"solgifarid@gmail.com"});
                try {
                    startActivity(Intent.createChooser(i, "ارسال ایمیل ..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "برنامه ارسال ایمیل نصب نیست", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ////////

        button_instagram.setOnClickListener(new View.OnClickListener() {
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
                            Uri.parse("http://instagram.com/boursetoolbox")));
                }
            }
        });
/////////////////////
        textView_isna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.isna.ir/")));
            }
        });
/////////////////////
        textView_tsetmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://tsetmc.com/Loader.aspx?ParTree=15")));
            }
        });
/////////////////////
        textView_aparat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.aparat.com/")));
            }
        });
/////////////////////








        return view;
    }

    private void setupView(View view) {
        button_back=view.findViewById(R.id.button_about_back);
        button_instagram=view.findViewById(R.id.button_about_instagram);
        button_mail=view.findViewById(R.id.button_about_mail);
        textView_version=view.findViewById(R.id.textView_about_version);
        textView_aparat=view.findViewById(R.id.textView_about_Aparat);
        textView_tsetmc=view.findViewById(R.id.textView_about_Tsetmc);
        textView_isna=view.findViewById(R.id.textView_about_Isna);
    }
}