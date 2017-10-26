package com.example.admin.dictbooktest;

/**
 * Created by admin on 2017/10/12.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class Fragement_right extends Fragment {
    private String stringCenterFragment;
    private SimpleAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
    }

}