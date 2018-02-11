package qge.cn.com.qgenglish.app.articel.question;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fony on 2018/2/8.
 */

public class ArticelBean implements Serializable {


    /**
     * questions : [{"explain":"根据文章第一段第二句 S is Selina,H is Hebe,E is Ella 可知S.H.E 一共有三个人。 故选C","answer":"C","name":"How many people are there in S．H．E．?","item_A":"Two．","items":["Nine","Six","Three","Two．"]},{"explain":"根据Ella is31 years old  故选C","answer":"C","name":"Ella is     years old．","item_B":"33","items":["27","29","31","33"]},{"explain":"根据倒数第二句Her favorite color is pink 可知她喜欢粉色  故选 A","answer":"A","name":"What color does Ella like best?","item_C":"red","items":["pink","black","yellow","red"]},{"explain":"根据第一句S.H.E is a pop music group from Taiwan.她们来自台湾 故A错   Ella喜欢猫  故B错   Selina喜欢粉色 故D错  正确答案选C","answer":"C","item_D":"Selina\u2019s favorite color is black","name":"Which of the following is right?","items":["S．H．E．is from Shanghai","Ella likes rabbits","Hebe likes watching movies","Selina\u2019s favorite color is black"]},{"explain":"根据文章  Selina和hebe是长发  故选D","answer":"D","item_E":"A and B","name":"Who has long hair?","items":["Selina","Hebe","Ella","A and B"]}]
     * content : S．H．E．is a pop music group from Taiwan，China．S is Selina．H is Hebe．E is Ella. Selina is 30 years old．She has long hair．She likes rabbits(兔子)．Her favorite color is pink. She likes going shopping and making clothes．Hebe is 28 years old．She has long hair，too. She likes dogs．Her favorite color is black．She likes watching movies and talking to friends. Ella is 31 years old．She has short hair．She likes cats．Her favorite color is pink·She likes playing basketball and computer games.
     * They all like singing．
     */

    private String content;
    private List<QuestionsBean> questions;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<QuestionsBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionsBean> questions) {
        this.questions = questions;
    }

    public static class QuestionsBean implements Serializable {
        /**
         * explain : 根据文章第一段第二句 S is Selina,H is Hebe,E is Ella 可知S.H.E 一共有三个人。 故选C
         * answer : C
         * name : How many people are there in S．H．E．?
         * item_A : Two．
         * items : ["Nine","Six","Three","Two．"]
         * item_B : 33
         * item_C : red
         * item_D : Selina’s favorite color is black
         * item_E : A and B
         */

        private String explain;
        private String answer;
        private String name;
        private String item_A;
        private String item_B;
        private String item_C;
        private String item_D;
        private String item_E;
        private List<String> items;

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getItem_A() {
            return item_A;
        }

        public void setItem_A(String item_A) {
            this.item_A = item_A;
        }

        public String getItem_B() {
            return item_B;
        }

        public void setItem_B(String item_B) {
            this.item_B = item_B;
        }

        public String getItem_C() {
            return item_C;
        }

        public void setItem_C(String item_C) {
            this.item_C = item_C;
        }

        public String getItem_D() {
            return item_D;
        }

        public void setItem_D(String item_D) {
            this.item_D = item_D;
        }

        public String getItem_E() {
            return item_E;
        }

        public void setItem_E(String item_E) {
            this.item_E = item_E;
        }

        public List<String> getItems() {
            return items;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }
    }
}
