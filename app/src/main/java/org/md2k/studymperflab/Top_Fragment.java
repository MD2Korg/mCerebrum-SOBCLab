package org.md2k.studymperflab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Top_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Top_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Top_Fragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view=inflater.inflate(R.layout.top_fragment, container, false);

        return view;
    }
}