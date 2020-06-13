/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.Serializable;

/**
 *
 * @author williamdobbs
 */
public class SecurityQuestions implements Serializable
{

    private int questionId;
    private String question;
    private int questionSet;

    public SecurityQuestions()
    {
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public int getQuestionId()
    {
        return questionId;
    }

    public void setQuestionId(int questionId)
    {
        this.questionId = questionId;
    }

    public int getQuestionSet()
    {
        return questionSet;
    }

    public void setQuestionSet(int questionSet)
    {
        this.questionSet = questionSet;
    }

}
