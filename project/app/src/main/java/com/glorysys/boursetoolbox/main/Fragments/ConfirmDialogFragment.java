package com.glorysys.boursetoolbox.main.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.glorysys.boursetoolbox.R;


public class ConfirmDialogFragment extends Fragment {
    Button button_confirm,button_cancel;
    TextView textView_title,textView_description;
private interface_confirmDialog confirmDialog;
private String t,d;

    public ConfirmDialogFragment(interface_confirmDialog confirmDialog) {
        // Required empty public constructor
        this.confirmDialog=confirmDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_confirm_dialog, container, false);
        setupViews(view);
        Bundle bundle=this.getArguments();
        if(bundle!=null){
           t= bundle.getString("title");
            d=bundle.getString("description");
            showDialog(t,d);
        }else{
            FragmentManager fragmentManager=getFragmentManager();
            fragmentManager.popBackStack("confirmDialogFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }



        return view;
    }

    private void setupViews(View view) {
        button_cancel=view.findViewById(R.id.button_confirmDialog_cancel);
        button_confirm=view.findViewById(R.id.button_confirmDialog_confirm);
        textView_title=view.findViewById(R.id.textview_confirmDialog_title);
        textView_description=view.findViewById(R.id.textview_confirmDialog_description);
    }
    public void showDialog(String title,String description){
        textView_title.setText(title);
        textView_description.setText(description);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.ConfirmDialog();
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.CancelDialog();
            }
        });

    }
    public interface interface_confirmDialog{
        void ConfirmDialog();
        void CancelDialog();
    }
}