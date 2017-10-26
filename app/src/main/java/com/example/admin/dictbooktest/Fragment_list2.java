package com.example.admin.dictbooktest;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dictbooktest.model.Words;
import com.example.admin.dictbooktest.util.WordsAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/10/23.
 */

public class Fragment_list2 extends Fragment {
    private ListView list;
    private WordsAction wordsAction;
    private Words words;
    private List<String> date =new ArrayList();
    private MyAdapter myAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragement_list, container, false);
        list = (ListView)view.findViewById(R.id.list1);
        myAdapter =new MyAdapter(getContext());
        list.setAdapter(myAdapter);
        return view;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = (ListView) getActivity().findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                try {
                    String test1 = list.getAdapter().getItem(arg2).toString();
                    date = wordsAction.getWordslistFromSQLite_loc();
                    Toast.makeText(getActivity(), list.getAdapter().getItem(arg2).toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    date = wordsAction.getWordslistFromSQLite_loc();
                    wordsAction.deleteFromSQLite_loc(date.get(i));
                    date.remove(i);
                    myAdapter.notifyDataSetChanged();
                }catch (Exception e){
                }
                return true;
            }
        });
    }
    private class MyAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;
        public ArrayList<String> arr;
        public MyAdapter(Context context) {
            super();
            this.context = context;
            inflater = LayoutInflater.from(context);
            arr = new ArrayList<String>();
            date=wordsAction.getWordslistFromSQLite_loc();
            for (int i=0;i<date.size();i++){
                arr.add(date.get(i).toString());
                Log.v("列表测试",date.get(i).toString());
            }

        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arr.size();
        }
        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }
        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }
        @Override
        public View getView(final int position, View view, ViewGroup arg2) {
            // TODO Auto-generated method stub
            if(view == null){
                view = inflater.inflate(R.layout.fragement_list_detail, null);
            }
            final TextView edit = (TextView) view.findViewById(R.id.detail_textView1);
            edit.setText(arr.get(position));    //在重构adapter的时候不至于数据错乱
            edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // TODO Auto-generated method stub
                    if(arr.size()>0){
                        arr.set(position, edit.getText().toString());
                    }
                }
            });
            return view;
        }

    }

}
