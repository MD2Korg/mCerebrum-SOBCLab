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

import com.beardedhen.androidbootstrap.BootstrapButton;

import org.md2k.datakitapi.DataKitAPI;
import org.md2k.datakitapi.datatype.DataTypeString;
import org.md2k.datakitapi.exception.DataKitException;
import org.md2k.datakitapi.time.DateTime;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;


public class FragmentTyping extends Fragment {

    View view;
    FancyButton fancyButtonBack;
    ActivityMain activityMain;
    RadioGroup radioGroupTypingTask;
    RadioGroup radioGroupTypingStatus;
    private BootstrapButton buttonFinish;
    private BootstrapButton buttonStart;
    private BootstrapButton buttonCancel;
    private BootstrapButton buttonInterrupt;
    private BootstrapButton buttonAdd;
    private EditText editTextNote;

    String typing_task;
    String typing_status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_typing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        activityMain = (ActivityMain) getActivity();
        fancyButtonBack = (FancyButton) view.findViewById(R.id.button_back);
        radioGroupTypingTask = (RadioGroup) view.findViewById(R.id.radio_typing_task);
        radioGroupTypingStatus = (RadioGroup) view.findViewById(R.id.radio_typing_status);
        buttonStart = (BootstrapButton) view.findViewById(R.id.btn_work_start);
        buttonFinish = (BootstrapButton) view.findViewById(R.id.btn_work_finish);
        buttonCancel = (BootstrapButton) view.findViewById(R.id.btn_work_cancel);
        buttonInterrupt = (BootstrapButton) view.findViewById(R.id.btn_work_interupt);
        fancyButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityMain.started==true){
                    Toasty.normal(getContext(), "Please stop the session first", Toast.LENGTH_SHORT).show();

                }else
                    activityMain.loadWorkType();
            }
        });
        editTextNote = (EditText) view.findViewById(R.id.editView_notes);
        buttonAdd = (BootstrapButton) view.findViewById(R.id.btn_work_add);

        prepareCancel(view);
        prepareStart(view);
        prepareFinish(view);
        prepareInterupt(view);
        prepareNoteAdd();
        enableButtons(true, false, false);


        //   radioGroupTypingTask = (RadioGroup) view.findViewById(R.id.radio_typing_task);


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


    void prepareStart(final View view) {
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
                if(check()==true) {
                    activityMain.started=true;
                    String work_status = activityMain.workType + "," + typing_task + "," + typing_status + "," + "start";
                    Toasty.normal(getContext(), work_status, Toast.LENGTH_SHORT).show();
                    enableButtons(false, true, false);
                }


            }
        });
    }


    void prepareInterupt(View view) {
        buttonInterrupt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DataKitAPI.getInstance(getActivity()).insert(((ActivityMain)getActivity()).dataSourceClient, new DataTypeString(DateTime.getDateTime(), activityMain.workType+","+"interrupted"));
                } catch (DataKitException e) {
                }
                Toasty.normal(getContext(), "Interrupted...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void prepareFinish(View view) {
        buttonFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(check()==true) {
                    String work_status = activityMain.workType + "," + typing_task + "," + typing_status+ ",finish";
                    activityMain.started=false;
                    try {
                        DataKitAPI.getInstance(getActivity()).insert(((ActivityMain)getActivity()).dataSourceClient, new DataTypeString(DateTime.getDateTime(), work_status));
                    } catch (DataKitException e) {
                    }
                    Toasty.normal(getContext(), work_status, Toast.LENGTH_SHORT).show();
                    enableButtons(true, false, true);
                }
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
                if(check()==true) {
                    activityMain.started=false;
                    String work_status = activityMain.workType + "," + typing_task + "," + typing_status + "," + "cancel";
                    try {
                        DataKitAPI.getInstance(getActivity()).insert(((ActivityMain)getActivity()).dataSourceClient, new DataTypeString(DateTime.getDateTime(), work_status));
                    } catch (DataKitException e) {
                    }
                    Toasty.normal(getContext(), work_status, Toast.LENGTH_SHORT).show();
                    enableButtons(true, false, true);
                }
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
        buttonInterrupt.setEnabled(sp);
        buttonInterrupt.setShowOutline(!sp);
        //   other_activity.setEnabled(wt);
        //  other_context.setEnabled(wt);
        //  select_activity.setEnabled(wt);
        //  select_context.setEnabled(wt);
    }

    boolean check() {

        int selectedId = radioGroupTypingTask.getCheckedRadioButtonId();
        int selectedstatus = radioGroupTypingStatus.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toasty.error(getContext(), "Please select a typing type to continue...", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (selectedstatus == -1) {
            Toasty.error(getContext(), "Please select a typing status to continue...", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  {
            RadioButton r_task = (RadioButton) view.findViewById(selectedId);
            RadioButton r_status = (RadioButton) view.findViewById(selectedstatus);
            typing_task = r_task.getText().toString();
            typing_status=r_status.getText().toString();
            return true;
        }




}
}
