package org.md2k.studymperflab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;

import org.md2k.datakitapi.DataKitAPI;
import org.md2k.datakitapi.datatype.DataTypeString;
import org.md2k.datakitapi.exception.DataKitException;
import org.md2k.datakitapi.time.DateTime;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;


public class FragmentWorkTypeStart extends Fragment{

    View view;
    FancyButton fancyButtonBack;
    AwesomeTextView textViewWorkType;
    ActivityMain activityMain;

    private BootstrapButton buttonFinish;
    private BootstrapButton buttonStart;
    private BootstrapButton buttonCancel;
    private BootstrapButton buttonAdd;
    private EditText editTextNote;


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
                if(activityMain.started==true){
                    Toasty.normal(getContext(), "Please stop the session first", Toast.LENGTH_SHORT).show();

                }else
                activityMain.loadWorkType();
            }
        });
        buttonStart = (BootstrapButton) view.findViewById(R.id.btn_work_start);
        buttonFinish = (BootstrapButton) view.findViewById(R.id.btn_work_finish);
        buttonCancel = (BootstrapButton) view.findViewById(R.id.btn_work_cancel);
        buttonAdd = (BootstrapButton) view.findViewById(R.id.btn_work_add);
        editTextNote = (EditText) view.findViewById(R.id.editView_notes);
        prepareCancel();
        prepareStart();
        prepareFinish();
        prepareNoteAdd();
        enableButtons(true, false, false);

    }


    void prepareStart() {
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //   workAnnotation.work_activity_other=other_activity.getText().toString();
              //  workAnnotation.work_context_other=other_context.getText().toString();
              //  workAnnotation.operation="START";
              //  workAnnotation.timestamp= DateTime.getDateTime();
              //  insertData();
              //  writeSharedPreference();
          //      handler.removeCallbacks(runnable);
            //    handler.post(runnable);
                activityMain.started=true;
                String work_status= activityMain.workType+ ","+"start";
                try {
                    DataKitAPI.getInstance(getActivity()).insert(((ActivityMain)getActivity()).dataSourceClient, new DataTypeString(DateTime.getDateTime(), work_status));
                } catch (DataKitException e) {
                }
                Toasty.normal(getContext(), work_status, Toast.LENGTH_SHORT).show();
                enableButtons(false, true, false);


            }
        });
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
                Toasty.normal(getContext(), "Note added", Toast.LENGTH_SHORT).show();


            }
        });
    }


    void prepareFinish() {
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

    private void prepareCancel() {
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
