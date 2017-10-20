package org.md2k.studymperflab;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;


public class ActivityMain extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Top_Fragment frg=new Top_Fragment();//create the fragment instance for the top fragment
        Middle_Fragment frg1=new Middle_Fragment();//create the fragment instance for the middle fragment
    //create the fragment instance for the bottom fragment

        FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager

        FragmentTransaction transaction=manager.beginTransaction();//create an instance of Fragment-transaction

        transaction.add(R.id.My_Container_1_ID, frg, "Frag_Top_tag");
        transaction.add(R.id.My_Container_2_ID, frg1, "Frag_Middle_tag");



        transaction.commit();

    }


}