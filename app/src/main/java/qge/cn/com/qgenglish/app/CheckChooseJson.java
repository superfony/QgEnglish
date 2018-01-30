package qge.cn.com.qgenglish.app;

import java.util.List;

/**
 * Created by fony on 2018/1/28.
 */

public class CheckChooseJson {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Answer : D
         * QuestionBody : carry
         * QuestionID : 1
         * QuestionItem : [{"itemValue":"A","itemContent":"抓住"},{"itemValue":"B","itemContent":"快点"},{"itemValue":"C","itemContent":"凯利"},{"itemValue":"D","itemContent":"搬运"},{"itemValue":"E","itemContent":"四轮马车"},{"itemValue":"F","itemContent":"汽车"},{"itemValue":"G","itemContent":"拿起来"}]
         */

        private String Answer;
        private String QuestionBody;
        private int QuestionID;
        private List<QuestionItemBean> QuestionItem;

        public String getAnswer() {
            return Answer;
        }

        public void setAnswer(String Answer) {
            this.Answer = Answer;
        }

        public String getQuestionBody() {
            return QuestionBody;
        }

        public void setQuestionBody(String QuestionBody) {
            this.QuestionBody = QuestionBody;
        }

        public int getQuestionID() {
            return QuestionID;
        }

        public void setQuestionID(int QuestionID) {
            this.QuestionID = QuestionID;
        }

        public List<QuestionItemBean> getQuestionItem() {
            return QuestionItem;
        }

        public void setQuestionItem(List<QuestionItemBean> QuestionItem) {
            this.QuestionItem = QuestionItem;
        }

        public static class QuestionItemBean {
            /**
             * itemValue : A
             * itemContent : 抓住
             */

            private String itemValue;
            private String itemContent;

            public String getItemValue() {
                return itemValue;
            }

            public void setItemValue(String itemValue) {
                this.itemValue = itemValue;
            }

            public String getItemContent() {
                return itemContent;
            }

            public void setItemContent(String itemContent) {
                this.itemContent = itemContent;
            }
        }
    }
}
