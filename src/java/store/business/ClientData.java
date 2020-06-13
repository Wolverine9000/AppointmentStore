/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.util.ArrayList;
import java.util.HashMap;
import store.data.CustomerDB;

/**
 *
 * @author williamdobbs
 */
public class ClientData
{

    private ArrayList<Client> clients = new ArrayList();
    private HashMap clientsHashMap = new HashMap();

//    public ClientData()
//    {
//        clients = CustomerDB.selectAllCustomers();
//    }
    public ClientData()
    {
        clientsHashMap = CustomerDB.selectClients();
    }

    public ArrayList<Client> getClients()
    {
        return clients;
    }

    public void setClients(ArrayList<Client> clients)
    {
        this.clients = clients;
    }

    public HashMap getClientsHashMap()
    {
        return clientsHashMap;
    }

    public void setClientsHashMap(HashMap clientsHashMap)
    {
        this.clientsHashMap = clientsHashMap;
    }

}
