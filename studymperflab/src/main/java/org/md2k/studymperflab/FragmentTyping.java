package org.md2k.studymperflab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

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
    private BootstrapButton buttonInterupt;
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
        buttonInterupt = (BootstrapButton) view.findViewById(R.id.btn_work_interupt);
        fancyButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityMain.loadWorkType();
            }
        });
        prepareCancel(view);
        prepareStart(view);
        prepareFinish(view);
        prepareInterupt(view);


        //   radioGroupTypingTask = (RadioGroup) view.findViewById(R.id.radio_typing_task);


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
                    String work_status = activityMain.workType + " " + typing_task + " " + typing_status + " " + "start";
                    Toasty.error(getContext(), work_status, Toast.LENGTH_SHORT).show();
                }
                    enableButtons(false, true, false);


            }
        });
    }


    void prepareInterupt(View view) {
        buttonInterupt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   workAnnotation.work_activity_other=other_activity.getText().toString();
                //  workAnnotation.work_context_other=other_context.getText().toString();
                //  workAnnotation.operation="START";
                //  workAnnotation.timestamp= DateTime.getDateTime();
                //  insertData();
                //  writeSharedPreference();
                Toasty.normal(getContext(), "Interrupted...", Toast.LENGTH_SHORT).show();
                //      handler.removeCallbacks(runnable);
                //    handler.post(runnable);
                if(check()==true) {
                    String work_status = activityMain.workType + " " + typing_task + " " + typing_status + " " + "interrupted";
                    Toasty.error(getContext(), work_status, Toast.LENGTH_SHORT).show();
                }

                    enableButtons(false, true, false);


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
                if(check()==true) {
                    String work_status = activityMain.workType + " " + typing_task + " " + typing_status + " " + "finish";
                    Toasty.error(getContext(), work_status, Toast.LENGTH_SHORT).show();
                }
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
                if(check()==true) {
                    String work_status = activityMain.workType + " " + typing_task + " " + typing_status + " " + "cancel";
                    Toasty.error(getContext(), work_status, Toast.LENGTH_SHORT).show();
                }
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
        buttonInterupt.setEnabled(sp);
        buttonInterupt.setShowOutline(!sp);
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
