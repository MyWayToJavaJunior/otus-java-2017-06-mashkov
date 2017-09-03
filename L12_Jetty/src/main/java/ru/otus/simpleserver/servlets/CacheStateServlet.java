package ru.otus.simpleserver.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.otus.simplecache.SimpleCacheEngine;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CacheStateServlet extends HttpServlet{
    SimpleCacheEngine cacheEngine;

    public CacheStateServlet(SimpleCacheEngine cacheEngine){
        this.cacheEngine = cacheEngine;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){

        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        object.addProperty("hit", cacheEngine.getHitCount());
        object.addProperty("miss", cacheEngine.getMissCount());
        object.addProperty("dead", cacheEngine.getDeadReferences());
        String result = gson.toJson(object);

        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus( HttpServletResponse.SC_OK );
    }

}
