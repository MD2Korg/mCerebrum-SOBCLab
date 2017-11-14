package org.md2k.studymperflab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;

import org.md2k.datakitapi.DataKitAPI;
import org.md2k.datakitapi.datatype.DataTypeString;
import org.md2k.datakitapi.exception.DataKitException;
import org.md2k.datakitapi.time.DateTime;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;


public class FragmentSession extends Fragment{

    View view;
    FancyButton fancyButtonBack;
    AwesomeTextView textViewWorkType;
    ActivityMain activityMain;

    private BootstrapButton buttonFinish;
    private BootstrapButton buttonStart;
    private BootstrapButton buttonCancel;
    RadioGroup radioGroup;
    private EditText editTextNote;
    private BootstrapButton buttonAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_session, container, false);
        return view;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        activityMain= (ActivityMain) getActivity();
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_work_type);

        fancyButtonBack = (FancyButton) view.findViewById(R.id.button_back);
        textViewWorkType= (AwesomeTextView) view.findViewById(R.id.textview_work_type);
        editTextNote = (EditText) view.findViewById(R.id.textView_notes);

//        textViewWorkType.setText(activityMain.workType);




//        fancyButtonBack.setOnClickListener(new View.OnClickListener() {
 //           @Override
  //          public void onClick(View v) {
    //            activityMain.loadSession();
    //        }
    //    });
        buttonStart = (BootstrapButton) view.findViewById(R.id.btn_work_start);
        buttonFinish = (BootstrapButton) view.findViewById(R.id.btn_work_finish);
        buttonCancel = (BootstrapButton) view.findViewById(R.id.btn_work_cancel);
        buttonAdd = (BootstrapButton) view.findViewById(R.id.btn_work_add);

        prepareCancel(view);
        prepareStart(view);
        prepareFinish(view);
        prepareNoteAdd();
        enableButtons(true,false, false);


    }
    void prepareNoteAdd() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNote.getText()==null || editTextNote.getText().toString().length()==0) return;
                String work_status= activityMain.workType+ ","+"note,"+editTextNote.getText().toString();
                editTextNote.setText("");
                try {
                    DataKitAPI.getInstance(getActivity()).insert(((ActivityMain)getActivity()).dataSourceClient, new DataTypeString(DateTime.getDateTime(), work_status));
                } catch (DataKitException e) {
                }
                Toasty.normal(getContext(), work_status+" added", Toast.LENGTH_SHORT).show();


            }
        });
    }


    void prepareStart(final View view) {
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId==-1){
                    Toasty.error(getContext(), "Please select a session to continue...", Toast.LENGTH_SHORT).show();
                }else {
                    RadioButton r= (RadioButton) view.findViewById(selectedId);
                    activityMain.workType=r.getText().toString();

                    activityMain.started = true;
                    String work_status = activityMain.workType + "," + "start";
                    try {
                        DataKitAPI.getInstance(getActivity()).insert(((ActivityMain) getActivity()).dataSourceClient, new DataTypeString(DateTime.getDateTime(), work_status));
                    } catch (DataKitException e) {
                    }
                    Toasty.normal(getContext(), work_status, Toast.LENGTH_SHORT).show();
                    enableButtons(false, true, false);
                }
            }
        });
    }


    void prepareFinish(View view) {
        buttonFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
          //      workAnnotation.operation="FINISH";
          //      workAnnotation.timestamp=DateTime.getDateTime();
           //     insertData();
           //     writeSharedPreference();
            //    insertData();
            //    handler.removeCallbacks(runnable);
            //    writeSharedPreference();
                String work_status= activityMain.workType+ ","+"stop";
                activityMain.started=false;
                try {
                    DataKitAPI.getInstance(getActivity()).insert(((ActivityMain)getActivity()).dataSourceClient, new DataTypeString(DateTime.getDateTime(), work_status));
                } catch (DataKitException e) {
                }
                Toasty.normal(getContext(), work_status, Toast.LENGTH_SHORT).show();
                enableButtons(true, false, true);
           //     showMessage("FINISHED");
            }
        });
    }

    private void prepareCancel(View view) {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   workAnnotation.operation="CANCEL";
             //   workAnnotation.timestamp=DateTime.getDateTime();
             //   insertData();
             //   writeSharedPreference();
             //   handler.removeCallbacks(runnable);
                activityMain.started=false;
                String work_status= activityMain.workType+ ","+"cancel";
                try {
                    DataKitAPI.getInstance(getActivity()).insert(((ActivityMain)getActivity()).dataSourceClient, new DataTypeString(DateTime.getDateTime(), work_status));
                } catch (DataKitException e) {
                }
                Toasty.normal(getContext(), work_status, Toast.LENGTH_SHORT).show();
                enableButtons(true, false, true);
           //     showMessage("CANCELLED");
            }
        });
    }



    void enableButtons(boolean st, boolean sp, boolean wt) {
        buttonStart.setEnabled(st);
        buttonStart.setShowOutline(!st);
        buttonFinish.setEnabled(sp);
        buttonFinish.setShowOutline(!sp);
        buttonCancel.setEnabled(sp);
        buttonCancel.setShowOutline(!sp);
     //   other_activity.setEnabled(wt);
      //  other_context.setEnabled(wt);
      //  select_activity.setEnabled(wt);
      //  select_context.setEnabled(wt);
    }

}
