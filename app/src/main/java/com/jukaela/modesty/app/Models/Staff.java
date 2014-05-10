package com.jukaela.modesty.app.Models;

import java.net.URL;

/**
 * Created by jbarrow on 5/4/14.  Yay!
 */

public class Staff
{
    private int staffId;

    public void setStaffId(int staffId)
    {
        this.staffId = staffId;
    }

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

    public void setUrl(URL url)
    {
        this.url = url;
    }

    private String username;
    private String rank;
    private URL url;
}
