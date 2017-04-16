package com.mycjda.mycjda.bean;

/**
 * Created by liyiwei on 2017/4/8.
 */

public class WslyBean {
    private String topic;
    private String question;
    private String answer;

    @Override
    public String toString() {
        return "WslyBean{" +
                "topic='" + topic + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
