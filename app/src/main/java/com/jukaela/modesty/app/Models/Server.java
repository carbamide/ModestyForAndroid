package com.jukaela.modesty.app.Models;

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

    public void setSoftware(String software)
    {
        this.software = software;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    public int getMaxPlayers()
    {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public void setMap(String map)
    {
        this.map = map;
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

    private String gameType;

    private ArrayList<String> plugins;

    private String software;

    private String hostName;

    private int maxPlayers;

    private String map;

    private int players;

    private int hostPort;


}
