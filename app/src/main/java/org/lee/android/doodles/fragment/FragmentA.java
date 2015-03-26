package org.lee.android.doodles.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.lee.android.doodles.R;
import org.lee.android.util.Log;

/**
 * 测试用
 */
public class FragmentA extends Fragment {

    public static FragmentA newInstance() {
        FragmentA fragment = new FragmentA();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private String mSimpleName = getClass().getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.anchor(mSimpleName);
        ActionBar actionBar = activity.getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_a, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView iTextView = (TextView) view.findViewById(R.id.Text);
        iTextView.setText(mSimpleName);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.anchor(mSimpleName);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.anchor(mSimpleName);
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.anchor(mSimpleName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.anchor(mSimpleName);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.anchor(mSimpleName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.anchor(mSimpleName);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.anchor(mSimpleName);
    }
}