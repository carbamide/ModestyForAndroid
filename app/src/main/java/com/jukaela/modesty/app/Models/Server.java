package com.jukaela.modesty.app.models;

import java.util.ArrayList;

/**
 * Created by jbarrow on 5/4/14.  Yay!
 */

public class Server
{
    public String getHostIp()
    {
        return hostIp;
    }

    public void setHostIp(String hostIp)
    {
        this.hostIp = hostIp;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public ArrayList<String> getPlugins()
    {
        return plugins;
    }

    public void setPlugins(ArrayList<String> plugins)
    {
        this.plugins = plugins;
    }

    public int getMaxPlayers()
    {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public int getPlayers()
    {
        return players;
    }

    public void setPlayers(int players)
    {
        this.players = players;
    }

    public int getHostPort()
    {
        return hostPort;
    }

    public void setHostPort(int hostPort)
    {
        this.hostPort = hostPort;
    }

    private String hostIp;

    private String version;

    private ArrayList<String> plugins;

    private int maxPlayers;

    private int players;

    private int hostPort;


}
