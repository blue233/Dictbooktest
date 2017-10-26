package com.example.admin.dictbooktest;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.admin.dictbooktest.model.Words;
import com.example.admin.dictbooktest.util.HttpCallBackListener;
import com.example.admin.dictbooktest.util.HttpUtil;
import com.example.admin.dictbooktest.util.ParseXML;
import com.example.admin.dictbooktest.util.WordsAction;
import com.example.admin.dictbooktest.util.WordsHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


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

public class Fragment3 extends Fragment{
    private SearchView searchView;
    private TextView searchWords_key, searchWords_psE, searchWords_psA, searchWords_posAcceptation, searchWords_sent;
    private ImageButton searchWords_voiceE, searchWords_voiceA,Words_add;
    private LinearLayout searchWords_posA_layout,searchWords_posE_layout, searchWords_linerLayout, searchWords_fatherLayout;
    private WordsAction wordsAction;
    private Words words = new Words();
    private SQLiteDatabase db;
    private List<String> data=new ArrayList<>();
    public List<String> bbblist=new ArrayList<>();
    /**
     * 网络查词完成后回调handleMessage方法
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:
                    //判断网络查找不到该词的情况
                    if (words.getSent().length() > 0) {
                        upDateView();
                    } else {
                        searchWords_linerLayout.setVisibility(View.GONE);
                       // Toast.makeText(, "抱歉！找不到该词！", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("测试", "tv保存2");
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament3, container,false);
        wordsAction = WordsAction.getInstance(this.getActivity());
        //初始化控件
        searchWords_linerLayout = (LinearLayout) view.findViewById(R.id.searchWords_linerLayout);
        searchWords_posA_layout = (LinearLayout)  view.findViewById(R.id.searchWords_posA_layout);
        searchWords_posE_layout = (LinearLayout)  view.findViewById(R.id.searchWords_posE_layout);
        searchWords_fatherLayout = (LinearLayout)  view.findViewById(R.id.searchWords_fatherLayout);
        searchWords_fatherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击输入框外实现软键盘隐藏
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        searchWords_key = (TextView) view.findViewById(R.id.searchWords_key);
        searchWords_psE = (TextView) view.findViewById(R.id.searchWords_psE);
        searchWords_psA = (TextView) view.findViewById(R.id.searchWords_psA);
        Words_add=(ImageButton)view.findViewById((R.id.Words_add));
        searchWords_posAcceptation = (TextView) view.findViewById(R.id.searchWords_posAcceptation);
        searchWords_sent = (TextView) view.findViewById(R.id.searchWords_sent);
        searchWords_voiceE = (ImageButton) view.findViewById(R.id.searchWords_voiceE);
        searchWords_voiceE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordsAction.playMP3(words.getKey(), "E",getActivity());
            }
        });
        searchWords_voiceA = (ImageButton) view.findViewById(R.id.searchWords_voiceA);
        searchWords_voiceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordsAction.playMP3(words.getKey(), "A", getActivity());
            }
        });
        searchView = (SearchView) view.findViewById(R.id.searchWords_searchView);
        searchView.setSubmitButtonEnabled(true);//设置显示搜索按钮
        searchView.setIconifiedByDefault(false);//设置不自动缩小为图标
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadWords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        Words_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              try {
                  words = wordsAction.getWordsFromSQLite(searchWords_key.getText().toString());
                  wordsAction.saveWords_loc(words);
                  bbblist.add(searchWords_key.getText().toString());
                  data = wordsAction.getWordslistFromSQLite_loc();
                  Log.v("测试测试", searchWords_key.getText().toString() + "应该是写入成功了" + "读出" + data);

              }catch (Exception e){}
            }
        });
        return view;
    }
    /**
     * 读取words的方法，优先从数据中搜索，没有在通过网络搜索
     */
    private void loadWords(String key) {
        words = wordsAction.getWordsFromSQLite(key);
        if ("" == words.getKey()) {
            String address = wordsAction.getAddressForWords(key);
            HttpUtil.sentHttpRequest(address, new HttpCallBackListener() {
                @Override
                public void onFinish(InputStream inputStream) {
                    WordsHandler wordsHandler = new WordsHandler();
                    ParseXML.parse(wordsHandler, inputStream);
                    words = wordsHandler.getWords();
                    wordsAction.saveWords(words);
                    wordsAction.saveWordsMP3(words);
                    handler.sendEmptyMessage(111);
                }

                @Override
                public void onError() {

                }
            });
        } else {
            upDateView();
        }
    }

    /**
     * 更新UI显示
     */
     void upDateView() {
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
        searchWords_linerLayout.setVisibility(View.VISIBLE);
    }
}