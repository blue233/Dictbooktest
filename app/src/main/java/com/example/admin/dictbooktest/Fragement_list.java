package com.example.admin.dictbooktest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dictbooktest.model.Words;
import com.example.admin.dictbooktest.util.WordsAction;

import java.util.ArrayList;
import java.util.List;

import static com.example.admin.dictbooktest.R.id.searchWords_key1;

public class Fragement_list extends Fragment  {
    private final String DB_WORDS = "Words";//库名
    private final String TABLE_WORDS1 = "listWords";//表名
    private final String TABLE_WORDS2 = "newWords";//表名
    private ListView list;
    private List<String> date =new ArrayList<>();
    private TextView searchWords_key, searchWords_psE, searchWords_psA, searchWords_posAcceptation, searchWords_sent;
    private ImageButton searchWords_voiceE, searchWords_voiceA,Words_delete;
    private LinearLayout searchWords_posA_layout,searchWords_posE_layout;
    private WordsAction wordsAction;
    private Words words = new Words();
    private MyAdapter adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wordsAction = WordsAction.getInstance(this.getActivity());
        View view = inflater.inflate(R.layout.fragement_list, container, false);
        list = (ListView)view.findViewById(R.id.list);
        adapter =new MyAdapter(getContext());
        list.setAdapter(adapter);
        //初始化控件
        searchWords_posA_layout = (LinearLayout)  getActivity().findViewById(R.id.searchWords_posA_layout1);
        searchWords_posE_layout = (LinearLayout)  getActivity().findViewById(R.id.searchWords_posE_layout1);
        searchWords_key = (TextView) getActivity().findViewById(searchWords_key1);
        searchWords_psE = (TextView) getActivity().findViewById(R.id.searchWords_psE1);
        searchWords_psA = (TextView) getActivity().findViewById(R.id.searchWords_psA1);
        searchWords_posAcceptation = (TextView) getActivity().findViewById(R.id.searchWords_posAcceptation1);
        searchWords_sent = (TextView) getActivity().findViewById(R.id.searchWords_sent1);
        return view;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = (ListView) getActivity().findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                try {
                    date = wordsAction.getWordslistFromSQLite_loc();
                    Toast.makeText(getActivity(), list.getAdapter().getItem(arg2).toString(), Toast.LENGTH_SHORT).show();
                    loadWords(date.get(arg2));
                    Log.v("列表测试","1"+date);
                }catch (Exception e){}
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("删除该词？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String s=searchWords_key.getText().toString();
                                    wordsAction.deleteFromSQLite_loc(s);
                                    list.setAdapter(adapter);
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                }catch (Exception e){
                }
                return true;
            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    /**
     * 更新UI显示
     */
    private void upDateView() {
        if (words.getIsChinese()) {
            searchWords_posAcceptation.setText(words.getFy());
            searchWords_posA_layout.setVisibility(View.GONE);
            searchWords_posE_layout.setVisibility(View.GONE);
        } else {
            searchWords_posAcceptation.setText(words.getPosAcceptation());
            if(words.getPsE()!="") {
                searchWords_psE.setText(String.format(getResources().getString(R.string.psE), words.getPsE()));
                searchWords_posE_layout.setVisibility(View.VISIBLE);
            }else {
                searchWords_posE_layout.setVisibility(View.GONE);
            }
            if(words.getPsA()!="") {
                searchWords_psA.setText(String.format(getResources().getString(R.string.psA), words.getPsA()));
                searchWords_posA_layout.setVisibility(View.VISIBLE);
            }else {
                searchWords_posA_layout.setVisibility(View.GONE);
            }
        }
        searchWords_key.setText(words.getKey());
        searchWords_sent.setText(words.getSent());
    }
    private void loadWords(String key) {
        words = wordsAction.getWordsFromSQLite_loc(key);
        upDateView();
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



