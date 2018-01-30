package org.md2k.studysobclab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;

import org.md2k.datakitapi.DataKitAPI;
import org.md2k.datakitapi.exception.DataKitException;
import org.md2k.datakitapi.messagehandler.OnConnectionListener;
import org.md2k.datakitapi.source.datasource.DataSource;
import org.md2k.datakitapi.source.datasource.DataSourceBuilder;
import org.md2k.datakitapi.source.datasource.DataSourceClient;
import org.md2k.datakitapi.source.datasource.DataSourceType;
import org.md2k.mcerebrum.commons.permission.Permission;
import org.md2k.mcerebrum.commons.permission.PermissionCallback;
import org.md2k.mcerebrum.commons.ui.data_quality.CDataQuality;
import org.md2k.mcerebrum.commons.ui.data_quality.DataQualityManager;
import org.md2k.mcerebrum.core.access.studyinfo.StudyCP;
import org.md2k.studysobclab.configuration.CConfig;
import org.md2k.studysobclab.configuration.ConfigManager;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public abstract class AbstractActivityBasics extends AppCompatActivity {
    static final String TAG = AbstractActivityBasics.class.getSimpleName();
    Toolbar toolbar;
    public CConfig cConfig;
    public DataQualityManager dataQualityManager;
    public DataSourceClient dataSourceClient;

    abstract void createMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.init(this);
        loadToolbar();
        getPermission();
    }
    void getPermission() {
        SharedPreferences sharedpreferences = getSharedPreferences("permission", Context.MODE_PRIVATE);
        if (sharedpreferences.getBoolean("permission", false) == true) {
            loadConfig();
            connectDataKit();
        } else {
            Permission.requestPermission(this, new PermissionCallback() {
                @Override
                public void OnResponse(boolean isGranted) {
                    SharedPreferences sharedpreferences = getSharedPreferences("permission", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("permission", isGranted);
                    editor.apply();
                    if (!isGranted) {
                        Toasty.error(getApplicationContext(), "StudyDataCollection ... !PERMISSION DENIED !!! Could not continue...", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        loadConfig();
                        connectDataKit();
                    }
                }
            });
        }

    }

    void connectDataKit() {
        try {
            DataKitAPI.getInstance(this).connect(new OnConnectionListener() {
                @Override
                public void onConnected() {
                    try {
                        dataSourceClient = DataKitAPI.getInstance(AbstractActivityBasics.this).register(new DataSourceBuilder().setType(DataSourceType.LABEL));
                    } catch (DataKitException e) {
                    }
                    dataQualityStart();
                    createMenu();
                }
            });
        } catch (DataKitException e) {
            Toasty.error(getApplicationContext(), "StudyDataCollection ... Failed to connect datakit..", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    void dataQualityStart() {
        ArrayList<DataSource> dataSources=new ArrayList<>();
        CDataQuality[] cDataQualities=cConfig.ui.home_screen.data_quality;
        if (cDataQualities == null || cDataQualities.length==0) return;
        for(int i=0;i<cDataQualities.length;i++){
            dataSources.add(cDataQualities[i].read);
        }
        dataQualityManager = new DataQualityManager();
        dataQualityManager.set(AbstractActivityBasics.this, dataSources);
    }

    void loadConfig() {
        cConfig = ConfigManager.read();
    }

    void loadToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getStudyName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Handle Code
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void stop() {
        if (dataQualityManager != null)
            dataQualityManager.clear();
        try {
            DataKitAPI.getInstance(this).disconnect();
        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroy() {
        stop();
        super.onDestroy();
    }

    public String getStudyName() {
      //  return "SOBC Lab Study";
        return StudyCP.getTitle(this);
    }
}

