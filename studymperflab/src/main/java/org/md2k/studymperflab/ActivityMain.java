package org.md2k.studymperflab;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import org.md2k.studymperflab.configuration.CConfig;
import org.md2k.studymperflab.configuration.ConfigManager;


public class ActivityMain extends AppCompatActivity {
    FragmentWorkType fragmentWorkType;
    FragmentTyping fragmentTyping;
    FragmentWorkTypeStart fragmentWorkTypeStart;
    FragmentManager manager;
    FragmentTransaction transaction;
    public String workType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CConfig cConfig = ConfigManager.read();



//        fragmentDataQuality.setArguments();
        fragmentWorkType=new FragmentWorkType();
        fragmentTyping=new FragmentTyping();
        fragmentWorkTypeStart=new FragmentWorkTypeStart();

        manager=getSupportFragmentManager();//create an instance of fragment manager

        transaction=manager.beginTransaction();//create an instance of Fragment-transaction


        transaction.add(R.id.container_lab, fragmentWorkType, "Fragment_Work_Type");
        transaction.commit();
    }
    public void loadWorkType(){
        transaction=manager.beginTransaction();//create an instance of Fragment-transaction
        transaction.replace(R.id.container_lab, fragmentWorkType,"Fragment_Work_Type");
        transaction.commit();
    }
    public void loadTyping(){
        transaction=manager.beginTransaction();//create an instance of Fragment-transaction
        transaction.replace(R.id.container_lab, fragmentTyping,"Fragment_Typing");
        transaction.commit();
    }
    public void loadWorkTypeStart(){
        transaction=manager.beginTransaction();//create an instance of Fragment-transaction
        transaction.replace(R.id.container_lab, fragmentWorkTypeStart,"Fragment_Work_Type_Start");
        transaction.commit();
    }
}