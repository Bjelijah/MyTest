package com.howell.mytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyListViewActivity extends AppCompatActivity {
    Button btn1,btn2;
    ListView lst1 ,lst2;
    Animation mShowAction;
    Animation mHideAction;
    String [] arr1= {"aaa","bbb","ccc","ddd","eee"};
    String [] arr2= {"aaa","bbb","ccc","ddd","eee","fff","ggg","hhh","iii","jjj","kkk","lll","mmm"};
    List<String> strArr = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        btn1 = findViewById(R.id.btn_lst1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lst1.setVisibility(View.VISIBLE);
                lst1.startAnimation(mShowAction);
                lst2.setVisibility(View.GONE);
                lst2.startAnimation(mHideAction);
            }
        });
        btn2 = findViewById(R.id.btn_lst2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lst2.setVisibility(View.VISIBLE);
                lst2.startAnimation(mShowAction);
                lst1.setVisibility(View.GONE);
                lst1.startAnimation(mHideAction);
            }
        });

        lst1 = findViewById(R.id.lst_lst1);
        lst1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr2));
        lst2 = findViewById(R.id.lst_lst2);
        lst2.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr2));
        lst1.setVisibility(View.GONE);
        lst2.setVisibility(View.GONE);
        initAnimations();
    }

    private void initAnimations(){
        mShowAction = AnimationUtils.loadAnimation(this,R.anim.push_up_in);
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,-1.0f,Animation.RELATIVE_TO_SELF,0f);
        mShowAction.setDuration(500);
        mHideAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f);
        mHideAction.setDuration(500);
    }
}
