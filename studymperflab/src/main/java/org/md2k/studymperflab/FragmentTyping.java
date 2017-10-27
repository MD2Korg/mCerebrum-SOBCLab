package org.md2k.studymperflab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import mehdi.sakout.fancybuttons.FancyButton;


public class FragmentTyping extends Fragment{

    View view;
    FancyButton fancyButtonBack;
    ActivityMain activityMain;
    RadioGroup radioGroupTypingTask;
    RadioGroup radioGroupTypingStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_typing, container, false);
        return view;
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        activityMain= (ActivityMain) getActivity();
        fancyButtonBack = (FancyButton) view.findViewById(R.id.button_back);
        radioGroupTypingStatus = (RadioGroup) view.findViewById(R.id.radio_typing_status);
        radioGroupTypingTask = (RadioGroup) view.findViewById(R.id.radio_typing_task);
        fancyButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityMain.loadWorkType();
            }
        });

    }
}
