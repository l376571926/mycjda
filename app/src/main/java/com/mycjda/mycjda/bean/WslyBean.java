package com.mycjda.mycjda.bean;

/**
 * Created by liyiwei on 2017/4/8.
 */

public class WslyBean {
    private String topic;
    private String date;
    private String question;
    private String replier;
    private String answer;

    @Override
    public String toString() {
        return "WslyBean{" +
                "topic='" + topic + '\'' +
                ", date='" + date + '\'' +
                ", question='" + question + '\'' +
                ", replier='" + replier + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReplier() {
        return replier;
    }

    public void setReplier(String replier) {
        this.replier = replier;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
