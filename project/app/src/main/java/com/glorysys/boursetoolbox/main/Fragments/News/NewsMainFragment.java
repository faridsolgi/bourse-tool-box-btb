package com.glorysys.boursetoolbox.main.Fragments.News;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.glorysys.boursetoolbox.R;
import com.glorysys.boursetoolbox.main.Adapter.NewsAdapter;
import com.glorysys.boursetoolbox.main.Models.NewsDataModel;
import com.glorysys.boursetoolbox.main.WebServer.AparatWepApi;
import com.glorysys.boursetoolbox.main.WebServer.NewsWebServer;

import java.util.List;

public class NewsMainFragment extends Fragment implements NewsWebServer.getNewsInterface, NewsAdapter.NewsViewHolder.onNewsClickListener {
    private RecyclerView recyclerView;
    private LottieAnimationView progressBar;
    private Button button_back,button_retry;
    private NewsAdapter newsAdapter;
int ctn;
    public NewsMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewsWebServer newsWebServer = new NewsWebServer(getContext());
        newsWebServer.getnews(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_main, container, false);
        onCreateViewFunction(view);


        return view;
    }

    public void onCreateViewFunction(View view) {
        setupViews(view);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager=getFragmentManager();
                manager.popBackStack("newsfragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        /////////////
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
                            NewsWebServer newsWebServer = new NewsWebServer(getContext());
                            newsWebServer.getnews(NewsMainFragment.this);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

    }

    private void setupViews(View view) {
        recyclerView = view.findViewById(R.id.RV_NewsFragment_RV);
        progressBar = view.findViewById(R.id.progressbar_NewsFragment_progressbar);
        button_back = view.findViewById(R.id.button_newsFragment_back);
        button_retry = view.findViewById(R.id.button_NewsFragment_retry);

    }


    //////////////////////////////////
    @Override
    public void getNewsSuccessful(List<NewsDataModel> newsDataModelList) {
        newsAdapter = new NewsAdapter(newsDataModelList,this);
        recyclerView.setAdapter(newsAdapter);
        progressBar.setVisibility(View.INVISIBLE);
        button_retry.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getNewsError() {
        progressBar.setVisibility(View.INVISIBLE);
        button_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNewsClick(NewsDataModel newsDataModel) {
        NewsBottomSheet newsBottomSheet=new NewsBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putString("url", newsDataModel.getUrl()); // Put anything what you want
        newsBottomSheet.setArguments(bundle);
        newsBottomSheet.show(getFragmentManager(),"da");
    }
}