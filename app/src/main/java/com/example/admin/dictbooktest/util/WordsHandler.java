package com.example.admin.dictbooktest.util;

import com.example.admin.dictbooktest.model.Words;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by admin on 2017/10/17.
 */

public class WordsHandler extends DefaultHandler {
    //记录当前节点
    private String nodeName;
    private Words words;
    //单词的词性与词义
    private StringBuilder posAcceptation;
    //例句
    private StringBuilder sent;

    /**
     * 获取解析后的words对象
     */
    public Words getWords() {
        return words;
    }

    //开始解析XML时调用
    @Override
    public void startDocument() throws SAXException {
        //初始化
        words = new Words();
        posAcceptation = new StringBuilder();
        sent = new StringBuilder();
    }

    //结束解析XML时调用
    @Override
    public void endDocument() throws SAXException {
        //将所有解析出来的内容赋予words
        words.setPosAcceptation(posAcceptation.toString().trim());
        words.setSent(sent.toString().trim());
    }

    //开始解析节点时调用
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodeName = localName;
    }

    //结束解析节点时调用
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //在读完整个节点后换行
        if ("acceptation".equals(localName)) {
            posAcceptation.append("\n");
        } else if ("orig".equals(localName)) {
            sent.append("\n");
        } else if ("trans".equals(localName)) {
            sent.append("\n");
            sent.append("\n");
        }
    }

    //获取节点中内容时调用
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String a = new String(ch, start, length);
        //去掉文本中原有的换行
        for (int i = start; i < start + length; i++) {
            if (ch[i] == '\n')
                return;
        }
        //将节点的内容存入Words对象对应的属性中
        if ("key".equals(nodeName)) {
            words.setKey(a.trim());
        } else if ("ps".equals(nodeName)) {
            if (words.getPsE().length() <= 0) {
                words.setPsE(a.trim());
            } else {
                words.setPsA(a.trim());
            }
        } else if ("pron".equals(nodeName)) {
            if (words.getPronE().length() <= 0) {
                words.setPronE(a.trim());
            } else {
                words.setPronA(a.trim());
            }
        } else if ("pos".equals(nodeName)) {
            posAcceptation.append(a);
        } else if ("acceptation".equals(nodeName)) {
            posAcceptation.append(a);
        } else if ("orig".equals(nodeName)) {
            sent.append(a);
        } else if ("trans".equals(nodeName)) {
            sent.append(a);
        } else if ("fy".equals(nodeName)) {
            words.setFy(a);
            words.setIsChinese(true);
        }
    }
    /**
     * 获取网络查找单词的对应地址
     *
     * @param key 要查询的单词
     * @return address 所查单词对应的http地址
     */
    public String getAddressForWords(final String key) {
        String address_p1 = "http://dict-co.iciba.com/api/dictionary.php?w=";
        String address_p2 = "";
        String address_p3 = "&key=E568F04171398072F7EC5D8B4A6CBDB4";
        if (isChinese(key)) {
            try {
                //此处非常重要！对中文的key进行重新编码，生成正确的网址
                address_p2 = "_" + URLEncoder.encode(key, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            address_p2 = key;
        }
        return address_p1 + address_p2 + address_p3;

    }

    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     *
     * @param c char类型的字符串
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
}