package com.glorysys.boursetoolbox.main.Fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.DataBase.DataBaseHelper;


public class ReviewFragment extends Fragment {
RatingBar myRatingBar;
Button button_submit,button_close;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_review, container, false);
setupViews(view);
anim();
button_submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try {
            String packageName=getActivity().getPackageName();
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(Uri.parse("bazaar://details?id=" + packageName));
            intent.setPackage("com.farsitel.bazaar");
            startActivity(intent);
            DataBaseHelper dataBaseHelper=new DataBaseHelper(getContext());
            dataBaseHelper.DB_Update_Review("review",2,0);
        }catch (Exception e){e.getStackTrace();}

    }
});
//////
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getFragmentManager();
                try {
                    DataBaseHelper dataBaseHelper=new DataBaseHelper(getContext());
                    dataBaseHelper.DB_Update_Review("review",0,1);
                    fragmentManager.popBackStack("reviewFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }catch (Exception e){e.getStackTrace();}


            }
        });



        return view;
    }

    private void setupViews(View view) {
        myRatingBar = view.findViewById(R.id.ratingbar);
        button_close = view.findViewById(R.id.button_review_close);
        button_submit =view.findViewById(R.id.button_review_submit);
    }

    private void anim(){


        final float current = myRatingBar.getRating();

        ObjectAnimator anim = ObjectAnimator.ofFloat(myRatingBar, "rating", current, 5f);
        anim.setDuration(1000);
        anim.setStartDelay(500);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ObjectAnimator anim1 = ObjectAnimator.ofFloat(myRatingBar, "rating", 5f, current);
                anim1.setDuration(1700);
                anim1.setStartDelay(500);
                anim1.start();
                anim1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ObjectAnimator anim2 = ObjectAnimator.ofFloat(myRatingBar, "rating", current, 5f);
                        anim2.setDuration(1700);
                        anim2.setStartDelay(500);
                        anim2.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
}
