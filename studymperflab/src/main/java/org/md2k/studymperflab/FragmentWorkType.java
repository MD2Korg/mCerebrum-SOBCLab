package org.md2k.studymperflab;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.api.view.BootstrapTextView;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;


public class FragmentWorkType extends Fragment{

    View view;
    FancyButton fancyButtonGo;
    RadioGroup radioGroup;
    ActivityMain activityMain;
/*
    String[] workTypes=new String[]{
            "Baseline rest period",
            "Typing task"
    };
*/

    private BootstrapButton buttonStop;
    private BootstrapButton buttonStart;
    private BootstrapButton buttonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view=inflater.inflate(R.layout.fragment_work_type, container, false);

        return view;
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        activityMain= (ActivityMain) getActivity();
        fancyButtonGo = (FancyButton) view.findViewById(R.id.button_go);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_work_type);

        fancyButtonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId==-1){
                    Toasty.error(getContext(), "Please select a work type to continue...", Toast.LENGTH_SHORT).show();
                }else {
                    RadioButton r= (RadioButton) view.findViewById(selectedId);
                    activityMain.workType=r.getText().toString();
                    if(r.getText().equals("Typing task")) {
                        activityMain.loadTyping();
                    }else{
                        activityMain.loadWorkTypeStart();
                    }
                }
            }
        });




/*
        radioGroup= (RadioGroup) view.findViewById(R.id.radio_work_type);
        for(int i=0;i<workTypes.length;i++){
            RadioButton radioButton=new RadioButton(getContext());
            radioButton.setId();
        }

*/
    }

}
