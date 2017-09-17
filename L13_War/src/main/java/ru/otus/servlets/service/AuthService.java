package ru.otus.servlets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.models.UserDataSet;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthService {


    private String testValue;

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

    @Value("${test.value}")
    private void setTestValue(String value){
        this.testValue = testValue;
    }
}
