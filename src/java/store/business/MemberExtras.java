/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

/**
 *
 * @author williamdobbs
 */
public class MemberExtras
{

    private String memberColor;
    private String memberLevel;
    private int idMemberLevels;

    public MemberExtras()
    {
        memberLevel = null;
        memberColor = null;
        idMemberLevels = 0;
    }

    public String getMemberColor()
    {
        return memberColor;
    }

    public void setMemberColor(String memberColor)
    {
        this.memberColor = memberColor;
    }

    public String getMemberLevel()
    {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel)
    {
        this.memberLevel = memberLevel;
    }

    public int getIdMemberLevels()
    {
        return idMemberLevels;
    }

    public void setIdMemberLevels(int idMemberLevels)
    {
        this.idMemberLevels = idMemberLevels;
    }
}
