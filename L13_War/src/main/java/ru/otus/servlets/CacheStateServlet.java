package ru.otus.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.softcache.SimpleCache;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configurable
public class CacheStateServlet extends HttpServlet{

    @Autowired
    SimpleCache cacheEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){

        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        object.addProperty("hit", cacheEngine.getHitCount());
        object.addProperty("miss", cacheEngine.getMissCount());
        object.addProperty("dead", cacheEngine.getDeadReference());
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
