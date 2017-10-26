package com.example.admin.dictbooktest;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 ***************************************************************
 *
 * @版权 LinFeng
 *
 * @作者 LinFeng
 *
 * @版本 1.0
 *
 * @创建日期 2016-6-6
 *
 * @功能描述
 *****************************************************************
 */

/**
 * @author LinFeng
 *
 */
public class Fragment2 extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament2, container,false);
        Fragment videoFragment1 = new Fragement_list();
        Fragment videoFragment2 = new Fragement_right();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_list, videoFragment1).commit();
        return view;
    }
}