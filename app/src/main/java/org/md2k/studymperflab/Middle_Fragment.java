package org.md2k.studymperflab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Middle_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Middle_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Middle_Fragment extends Fragment{

    View view;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view=inflater.inflate(R.layout.middle_fragment, container, false);
        btn=(Button) view.findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub



            }
        });
        return view;
    }
}
