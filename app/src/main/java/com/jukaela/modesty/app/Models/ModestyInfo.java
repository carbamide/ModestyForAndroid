package com.jukaela.modesty.app.Models;

import java.util.ArrayList;

/**
 * Created by jbarrow on 5/4/14.  Yay!
 */

public class ModestyInfo
{
    private Server serverInformation;
    private ArrayList<Player> players;

    public Server getServerInformation()
    {
        return serverInformation;
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public void setServerInformation(Server _serverInformation)
    {
        serverInformation = _serverInformation;
    }

    public void setPlayers(ArrayList<Player> _players)
    {
        players = _players;
    }
}
