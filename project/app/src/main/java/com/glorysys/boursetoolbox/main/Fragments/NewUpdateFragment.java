package com.glorysys.boursetoolbox.main.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.MainActivity;

public class NewUpdateFragment extends Fragment {
private Button button_update;
private TextView textView_dontUpdate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_new_update, container, false);
        setupViews(view);
        textView_dontUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    getActivity().finish();
                }catch (Exception e){e.getStackTrace();}
            }
        });
        //////
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String packagename=getActivity().getPackageName();
               try{
                   Intent intent = new Intent(Intent.ACTION_VIEW);
                   intent.setData(Uri.parse("bazaar://details?id=" +packagename));
                   intent.setPackage("com.farsitel.bazaar");
                   startActivity(intent);
               }catch (Exception e){e.getStackTrace();}

            }
        });


        return view;
    }

    private void setupViews(View view) {
        button_update=view.findViewById(R.id.button_update_submit);
        textView_dontUpdate=view.findViewById(R.id.textview_update_dontupdate);
    }
}