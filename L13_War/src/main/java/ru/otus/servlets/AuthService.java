package ru.otus.servlets;

import ru.otus.models.UserDataSet;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, UserDataSet> users;

    public AuthService(){
        users = new HashMap<>();
    }

    public void put(String id, UserDataSet user){
        users.put(id, user);
    }

    public boolean contains(String id){
        return users.keySet().contains(id);
    }

    public UserDataSet get(String id){
        return users.get(id);
    }
}
