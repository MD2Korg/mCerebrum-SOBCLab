package org.md2k.studymperflab;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import org.md2k.mcerebrum.core.access.appinfo.AppBasicInfo;
import org.md2k.mcerebrum.core.access.appinfo.AppInfo;
import org.md2k.mcerebrum.core.access.serverinfo.ServerCP;
import org.md2k.mcerebrum.system.update.Update;
import org.md2k.studymperflab.menu.MenuContent;
import org.md2k.studymperflab.menu.MyMenu;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static org.md2k.studymperflab.menu.MyMenu.MENU_START_STOP;

public abstract class AbstractActivityMenu extends AbstractActivityBasics {
    private Drawer result = null;
    int selectedMenu = MyMenu.MENU_HOME;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void createUI() {
        Observable.just(true).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean aBoolean) {
                createDrawer();
                result.resetDrawerContent();
                result.getHeader().refreshDrawableState();
                result.setSelection(MyMenu.MENU_HOME);
                return true;
            }
        }).subscribe();
    }

    public void updateUI() {
        if(result==null) {
            createUI();
            return;
        }
        int index = (int) result.getCurrentSelection();
        if(index==-1) index = MyMenu.MENU_HOME;
        int badgeValue= Update.hasUpdate(AbstractActivityMenu.this);
        if(badgeValue>0){
            StringHolder a = new StringHolder(String.valueOf(badgeValue));
            result.updateBadge(MyMenu.MENU_UPDATE, a);
        }else{
            StringHolder a = new StringHolder("");
            result.updateBadge(MyMenu.MENU_UPDATE, a);
        }
        boolean start = AppInfo.isServiceRunning(this, ServiceStudy.class.getName());
        PrimaryDrawerItem pd = (PrimaryDrawerItem) result.getDrawerItem(MENU_START_STOP);
        MenuContent m;
        if(start==false){
            pd = pd.withName("Start Data Collection").withIcon(FontAwesome.Icon.faw_play_circle_o);
        }else{
            pd = pd.withName("Stop Data Collection").withIcon(FontAwesome.Icon.faw_pause_circle_o);
        }
        int pos = result.getPosition(MENU_START_STOP);
        result.removeItem(MENU_START_STOP);
        result.addItemAtPosition(pd, pos);
        result.setSelection(MyMenu.MENU_HOME);
/*
        Observable.just(true).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean aBoolean) {
                Log.d("abc","time10="+ DateTime.getDateTime());
                createDrawer();
                result.resetDrawerContent();
                result.getHeader().refreshDrawableState();
                result.setSelection(MyMenu.MENU_HOME);
                Log.d("abc","time11="+ DateTime.getDateTime());
                return true;
            }
        }).subscribe();
*/
    }

    void createDrawer() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.cover_image)
                .withCompactStyle(true)
                .addProfiles(new MyMenu().getHeaderContent(ServerCP.getUserName(getBaseContext()), responseCallBack))
                .build();
        boolean start = AppInfo.isServiceRunning(this, ServiceStudy.class.getName());

            result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(new MyMenu().getMenuContent(start, responseCallBack))
                .build();
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            if (selectedMenu != MyMenu.MENU_HOME) {
                responseCallBack.onResponse(null, MyMenu.MENU_HOME);
            } else {
                stopAll();
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
/*
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
*/
//        super.onSaveInstanceState(outState);
    }

    public ResponseCallBack responseCallBack = new ResponseCallBack() {
        @Override
        public void onResponse(IDrawerItem drawerItem, int responseId) {
            selectedMenu = responseId;
            if (drawerItem != null)
                toolbar.setTitle(getStudyName()+": " + ((Nameable) drawerItem).getName().getText(AbstractActivityMenu.this));
            else toolbar.setTitle(getStudyName());
            switch (responseId) {
/*
                case MyMenu.MENU_HOME:
                    result.setSelection(MyMenu.MENU_HOME, false);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commitAllowingStateLoss();
                    break;
*/
                case MENU_START_STOP:
                    boolean start =AppInfo.isServiceRunning(AbstractActivityMenu.this, ServiceStudy.class.getName());

                    if(start) {
                        stopDataCollection();
                    }
                    else {
                        startDataCollection();
                    }
                    toolbar.setTitle(getStudyName());
                    break;
                case MyMenu.MENU_RESET:
                    boolean startt =AppInfo.isServiceRunning(AbstractActivityMenu.this, ServiceStudy.class.getName());

                    if(startt) {
                        resetDataCollection();
                    }
                    else {
                        startDataCollection();
                    }
                    toolbar.setTitle(getStudyName());
                    break;
                case MyMenu.MENU_UPDATE:
                    stopDataCollection();
                    Intent intent = new Intent();
                    String p=AppBasicInfo.getMCerebrum(AbstractActivityMenu.this);
                    intent.putExtra("STUDY",getPackageName());
                    intent.setComponent(new ComponentName(p, p+".UI.check_update.ActivityCheckUpdate"));
                    startActivity(intent);
                    finish();
                    break;
                case MyMenu.MENU_SETTINGS:
                    stopAndQuit();
                    break;
/*
                case MyMenu.MENU_WORK_ANNOTATION:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentWorkAnnotation()).commitAllowingStateLoss();
                    break;
*/

/*
                case AbstractMenu.MENU_APP_ADD_REMOVE:
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FragmentFoldingUIAppInstall()).commitAllowingStateLoss();
                    break;
                case AbstractMenu.MENU_APP_SETTINGS:
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FragmentFoldingUIAppSettings()).commitAllowingStateLoss();
                    break;
                case AbstractMenu.MENU_JOIN:
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FragmentJoinStudy()).commitAllowingStateLoss();
                    break;
                case AbstractMenu.MENU_LEAVE:
                    materialDialog= Dialog.simple(AbstractActivityMenu.this, "Leave Study", "Do you want to leave the study?", "Yes", "Cancel", new DialogCallback() {
                        @Override
                        public void onSelected(String value) {
                            if(value.equals("Yes")){
                                configManager.clear();
                                prepareConfig();
                            }
                        }
                    }).show();
                    break;
                case AbstractMenu.MENU_LOGIN:
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FragmentLogin()).commitAllowingStateLoss();
//                Intent i = new Intent(this, ActivityLogin.class);
//                startActivityForResult(i, ID_JOIN_STUDY);
                    break;
                case AbstractMenu.MENU_LOGOUT:
//                ((UserServer) user).setLoggedIn(this,false);
                    userInfo.setLoggedIn(false);
                    Toasty.success(AbstractActivityMenu.this, "Logged out", Toast.LENGTH_SHORT, true).show();
                    updateUI();
                    break;
*/
                default:
            }
        }
    };
}

