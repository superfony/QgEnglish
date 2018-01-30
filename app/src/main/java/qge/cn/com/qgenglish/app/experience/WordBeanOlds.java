package qge.cn.com.qgenglish.app.experience;

import qge.cn.com.qgenglish.app.word.table.Word_niujinban_7_1;

/**
 * Created by fony on 2017/12/7.
 * // 每行都显示两个单词 对应的状态也是两个
 */

public class WordBeanOlds {

    public Word_niujinban_7_1 wordBeanOld;
    public Word_niujinban_7_1 wordBeanOld1;
    public boolean state = false; // 是否被选择识记
    public boolean state1 = false;
    public boolean isShow = false; // 显示单词or 词义
    public boolean isShow1 = false;
    public boolean isClick = false; // 判断是否点击过
    public boolean isClick1 = false;

    public WordBeanOlds() {

    }
}
