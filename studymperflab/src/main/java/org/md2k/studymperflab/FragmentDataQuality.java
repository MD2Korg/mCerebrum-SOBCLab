package org.md2k.studymperflab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDataQuality.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDataQuality#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDataQuality extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view=inflater.inflate(R.layout.top_fragment, container, false);

        return view;
    }
}