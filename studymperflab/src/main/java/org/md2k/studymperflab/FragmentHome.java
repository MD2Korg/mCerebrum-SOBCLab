package org.md2k.studymperflab;

import android.app.NotificationManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import org.md2k.mcerebrum.commons.ui.data_quality.CDataQuality;
import org.md2k.mcerebrum.commons.ui.data_quality.ResultCallback;
import org.md2k.mcerebrum.commons.ui.data_quality.UserViewDataQuality;
import org.md2k.mcerebrum.commons.ui.data_quality.ViewDataQuality;
import org.md2k.mcerebrum.commons.ui.privacy.ViewPrivacy;
import org.md2k.mcerebrum.core.access.appinfo.AppInfo;
import org.md2k.mcerebrum.core.data_format.DATA_QUALITY;
import org.md2k.mcerebrum.system.update.Update;
import org.md2k.studymperflab.configuration.CHomeScreen;

import static android.content.Context.NOTIFICATION_SERVICE;

public class FragmentHome extends Fragment {
    UserViewDataQuality userViewDataQuality;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        CHomeScreen cHomeScreen = ((ActivityMain)getActivity()).cConfig.ui.home_screen;
        if(cHomeScreen.data_quality!=null) {
            loadDataQuality(view, cHomeScreen.data_quality);
        }
        if(cHomeScreen.privacy!=null){
            loadPrivacy(view);
        }
    }

    void loadDataQuality(View view, CDataQuality[] cDataQualities){
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout_add);
        final ViewDataQuality viewDataQuality=new ViewDataQuality(getActivity(), cDataQualities);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewDataQuality.setLayoutParams(LLParams);
        linearLayout.addView(viewDataQuality);
        userViewDataQuality=new UserViewDataQuality(((ActivityMain)getActivity()).dataQualityManager);
        userViewDataQuality.set(new ResultCallback() {
            @Override
            public void onResult(int[] result) {
                viewDataQuality.setDataQuality(result);
            }
        });
    }
    void loadPrivacy(View view){
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout_add);
        ViewPrivacy viewPrivacy=new ViewPrivacy(getActivity());
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewPrivacy.setLayoutParams(LLParams);
        linearLayout.addView(viewPrivacy);
    }

    @Override
    public void onDestroyView() {
        CHomeScreen cHomeScreen = ((ActivityMain)getActivity()).cConfig.ui.home_screen;
        if(cHomeScreen.data_quality!=null) {
            userViewDataQuality.clear();
        }
        super.onDestroyView();
    }

}
