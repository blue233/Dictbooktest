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
 * 这里要注意，Fragment是要引入android.support.v4.app.Fragment这个包里面的，不是那个app.fragment那个包哦
 * @author LinFeng
 *
 */
public class Fragment1 extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament1, container,false);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        Fragment videoFragment1 = new Fragement_list();
        transaction.add(R.id.fragment_list, videoFragment1).commit();
        return view;
    }

}