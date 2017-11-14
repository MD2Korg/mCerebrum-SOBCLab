package org.md2k.studymperflab;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import org.md2k.datakitapi.source.datasource.DataSourceClient;
import org.md2k.datakitapi.time.DateTime;
import org.md2k.mcerebrum.core.access.studyinfo.StudyCP;
import org.md2k.mcerebrum.system.update.Update;

import es.dmoral.toasty.Toasty;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class ActivityMain extends AbstractActivityMenu {
    public String workType;
    boolean started = false;
    FragmentWorkType fragmentWorkType;
    FragmentTyping fragmentTyping;
    FragmentWorkTypeStart fragmentWorkTypeStart;
    FragmentSession fragmentSession;
    FragmentManager manager;
    FragmentTransaction transaction;
    Subscription subscriptionCheckUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentWorkType=new FragmentWorkType();
        fragmentTyping=new FragmentTyping();
        fragmentWorkTypeStart=new FragmentWorkTypeStart();
        fragmentSession = new FragmentSession();

        manager=getSupportFragmentManager();//create an instance of fragment manager

        transaction=manager.beginTransaction();//create an instance of Fragment-transaction
        if(StudyCP.getTitle(this).endsWith("Minnesota"))
            transaction.replace(R.id.container_lab, fragmentWorkType, "Fragment_Work_Type");
        else
            transaction.replace(R.id.container_lab, fragmentSession, "Fragment_Session");
        transaction.commitNowAllowingStateLoss();
        start();

//        loadDataQualityUI();
//        fragmentDataQuality.setArguments();
    }
/*
    public void loadDataQualityUI(){
        TextView textView1= (TextView) findViewById(R.id.textview_data_quality_title_1);
        textView1.setText(cConfig.ui.home_screen.data_quality[0].title);
        dq1 = (FancyButton) findViewById(R.id.button_data_quality_1);
        dq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityDataQuality.class);
                intent.putExtra("title", cConfig.ui.home_screen.data_quality[0].title);
                intent.putExtra("message", cConfig.ui.home_screen.data_quality[0].message);
                intent.putExtra("video_link", cConfig.ui.home_screen.data_quality[0].video_link);
                intent.putExtra("read", cConfig.ui.home_screen.data_quality[0].read);
                intent.putExtra("plot", cConfig.ui.home_screen.data_quality[0].plot);
//                intent.putExtra(CDataQuality.class.getSimpleName(), cConfig.ui.data_quality[0]);
                startActivity(intent);
            }
        });



        TextView textView2= (TextView) findViewById(R.id.textview_data_quality_title_2);
        textView2.setText(cConfig.ui.home_screen.data_quality[1].title);
        dq2 = (FancyButton) findViewById(R.id.button_data_quality_2);
        dq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityDataQuality.class);
                intent.putExtra("title", cConfig.ui.home_screen.data_quality[1].title);
                intent.putExtra("message", cConfig.ui.home_screen.data_quality[1].message);
                intent.putExtra("video_link", cConfig.ui.home_screen.data_quality[1].video_link);
                intent.putExtra("read", cConfig.ui.home_screen.data_quality[1].read);
                intent.putExtra("plot", cConfig.ui.home_screen.data_quality[1].plot);
                startActivity(intent);
            }
        });


        TextView textView3= (TextView) findViewById(R.id.textview_data_quality_title_3);
        textView3.setText(cConfig.ui.home_screen.data_quality[2].title);
        dq3 = (FancyButton) findViewById(R.id.button_data_quality_3);
        dq3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityDataQuality.class);
                intent.putExtra("title", cConfig.ui.home_screen.data_quality[2].title);
                intent.putExtra("message", cConfig.ui.home_screen.data_quality[2].message);
                intent.putExtra("video_link", cConfig.ui.home_screen.data_quality[2].video_link);
                intent.putExtra("read", cConfig.ui.home_screen.data_quality[2].read);
                intent.putExtra("plot", cConfig.ui.home_screen.data_quality[2].plot);
                startActivity(intent);
            }
        });



        TextView textView4= (TextView) findViewById(R.id.textview_data_quality_title_4);
        textView4.setText(cConfig.ui.home_screen.data_quality[3].title);
        dq4 = (FancyButton) findViewById(R.id.button_data_quality_4);
        dq4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityDataQuality.class);
                intent.putExtra("title", cConfig.ui.home_screen.data_quality[3].title);
                intent.putExtra("message", cConfig.ui.home_screen.data_quality[3].message);
                intent.putExtra("video_link", cConfig.ui.home_screen.data_quality[3].video_link);
                intent.putExtra("read", cConfig.ui.home_screen.data_quality[3].read);
                intent.putExtra("plot", cConfig.ui.home_screen.data_quality[3].plot);
                startActivity(intent);
            }
        });
    }
*/
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
    void start(){
        startDataCollection();
        subscriptionCheckUpdate = Observable.just(true).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean aBoolean) {
                        return Update.checkUpdate(ActivityMain.this);
                    }
                }).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("abc","abeeee");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                    }
                });
        if (getIntent().getBooleanExtra("background", false))
            finish();

    }
    public void onDestroy() {
        if (subscriptionCheckUpdate != null && !subscriptionCheckUpdate.isUnsubscribed())
            subscriptionCheckUpdate.unsubscribe();
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        if(started){
            Toasty.normal(this, "Please stop the session first", Toast.LENGTH_SHORT).show();
        }
        else
            super.onBackPressed();
    }
}