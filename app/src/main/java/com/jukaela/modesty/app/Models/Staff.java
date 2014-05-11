package com.jukaela.modesty.app.models;

/**
 * Created by jbarrow on 5/4/14.  Yay!
 */

public class Staff
{
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getRank()
    {
        return rank;
    }

    public void setRank(String rank)
    {
        this.rank = rank;
    }

    private String username;
    private String rank;
}
