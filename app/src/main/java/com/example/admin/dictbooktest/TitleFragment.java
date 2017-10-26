package com.example.admin.dictbooktest;

/**
 * Created by admin on 2017/10/11.
 */
import android.app.Fragment;
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
 * @功能描述 用Fragment制作标题栏
 *****************************************************************
 */
public class TitleFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * 加载布局文件然后返回view，显示在Activity
         */
        View view = inflater.inflate(R.layout.title, container,false);
        return view;
    }

}