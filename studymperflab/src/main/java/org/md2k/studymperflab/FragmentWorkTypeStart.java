package org.md2k.studymperflab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.AwesomeTextView;

import mehdi.sakout.fancybuttons.FancyButton;


public class FragmentWorkTypeStart extends Fragment{

    View view;
    FancyButton fancyButtonBack;
    AwesomeTextView textViewWorkType;
    ActivityMain activityMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_work_type_start, container, false);
        return view;
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        activityMain= (ActivityMain) getActivity();
        fancyButtonBack = (FancyButton) view.findViewById(R.id.button_back);
        textViewWorkType= (AwesomeTextView) view.findViewById(R.id.textview_work_type);
        textViewWorkType.setText(activityMain.workType);
        fancyButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityMain.loadWorkType();
            }
        });

    }

}
