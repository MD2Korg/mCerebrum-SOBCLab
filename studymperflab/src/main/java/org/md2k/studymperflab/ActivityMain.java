package org.md2k.studymperflab;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.services.youtube.model.ActivityContentDetails;

import org.md2k.mcerebrum.commons.permission.Permission;
import org.md2k.mcerebrum.commons.permission.PermissionCallback;
import org.md2k.mcerebrum.commons.ui.data_quality.ActivityDataQuality;
import org.md2k.mcerebrum.commons.ui.data_quality.ViewDataQuality;
import org.md2k.studymperflab.configuration.CConfig;
import org.md2k.studymperflab.configuration.CDataQuality;
import org.md2k.studymperflab.configuration.ConfigManager;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;


public class ActivityMain extends AppCompatActivity {
    FragmentWorkType fragmentWorkType;
    FragmentTyping fragmentTyping;
    FragmentWorkTypeStart fragmentWorkTypeStart;
    FragmentManager manager;
    FragmentTransaction transaction;
    FancyButton dq1;
    FancyButton dq2;
    FancyButton dq3;
    FancyButton dq4;

    public String workType;
    CConfig cConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permission.requestPermission(this, new

                PermissionCallback() {
                    @Override
                    public void OnResponse(boolean isGranted) {
                        if (!isGranted) {
                            Toasty.error(getApplicationContext(), "!PERMISSION DENIED !!! Could not continue...", Toast.LENGTH_SHORT).show();
                            Log.d("abc","abc");

                            finish();
                        } else {
                            loadDataQualityUI();

//                            cConfig.ui.data_quality[0].);
//                            updateUI();
                            Log.d("abc", "abc");
                        }
                    }
                });





//        fragmentDataQuality.setArguments();
        fragmentWorkType=new FragmentWorkType();
        fragmentTyping=new FragmentTyping();
        fragmentWorkTypeStart=new FragmentWorkTypeStart();

        manager=getSupportFragmentManager();//create an instance of fragment manager

        transaction=manager.beginTransaction();//create an instance of Fragment-transaction


        transaction.add(R.id.container_lab, fragmentWorkType, "Fragment_Work_Type");
        transaction.commit();
    }
    public void loadDataQualityUI(){
        cConfig = ConfigManager.read();
        TextView textView1= (TextView) findViewById(R.id.textview_data_quality_title_1);
        textView1.setText(cConfig.ui.data_quality[0].title);
        dq1 = (FancyButton) findViewById(R.id.button_data_quality_1);
        dq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityDataQuality.class);
                intent.putExtra("title", cConfig.ui.data_quality[0].title);
                intent.putExtra("message", cConfig.ui.data_quality[0].message);
                intent.putExtra("video_link", cConfig.ui.data_quality[0].video_link);
                intent.putExtra("datasource", cConfig.ui.data_quality[0].datasource);
//                intent.putExtra(CDataQuality.class.getSimpleName(), cConfig.ui.data_quality[0]);
                startActivity(intent);
            }
        });



        TextView textView2= (TextView) findViewById(R.id.textview_data_quality_title_2);
        textView2.setText(cConfig.ui.data_quality[1].title);
        dq2 = (FancyButton) findViewById(R.id.button_data_quality_2);
        dq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityDataQuality.class);
                intent.putExtra("title", cConfig.ui.data_quality[1].title);
                intent.putExtra("message", cConfig.ui.data_quality[1].message);
                intent.putExtra("video_link", cConfig.ui.data_quality[1].video_link);
                intent.putExtra("datasource", cConfig.ui.data_quality[1].datasource);
                startActivity(intent);
            }
        });


        TextView textView3= (TextView) findViewById(R.id.textview_data_quality_title_3);
        textView3.setText(cConfig.ui.data_quality[2].title);
        dq3 = (FancyButton) findViewById(R.id.button_data_quality_3);
        dq3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityDataQuality.class);
                intent.putExtra("title", cConfig.ui.data_quality[2].title);
                intent.putExtra("message", cConfig.ui.data_quality[2].message);
                intent.putExtra("video_link", cConfig.ui.data_quality[2].video_link);
                intent.putExtra("datasource", cConfig.ui.data_quality[2].datasource);
                startActivity(intent);
            }
        });



        TextView textView4= (TextView) findViewById(R.id.textview_data_quality_title_4);
        textView4.setText(cConfig.ui.data_quality[3].title);
        dq4 = (FancyButton) findViewById(R.id.button_data_quality_4);
        dq4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityDataQuality.class);
                intent.putExtra("title", cConfig.ui.data_quality[3].title);
                intent.putExtra("message", cConfig.ui.data_quality[3].message);
                intent.putExtra("video_link", cConfig.ui.data_quality[3].video_link);
                intent.putExtra("datasource", cConfig.ui.data_quality[3].datasource);
                startActivity(intent);
            }
        });



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