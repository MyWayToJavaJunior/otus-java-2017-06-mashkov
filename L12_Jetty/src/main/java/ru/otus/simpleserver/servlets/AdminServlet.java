package ru.otus.simpleserver.servlets;

import ru.otus.models.UserDataSet;
import ru.otus.simpleserver.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private Map<String, UserDataSet> authorized;
    private String userName = "anonymous";

    public AdminServlet(Map<String, UserDataSet> authorized){
        this.authorized = authorized;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        String id = request.getSession().getId();
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        if (authorized.keySet().contains(id)){

            userName = authorized.get(id).getName();
            data.put("user", userName);
            try {
                body.put("body", TemplateProcessor.instance().getPage("adminBody.html", data));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                body.put("body", TemplateProcessor.instance().getPage("authRequest.html", data));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            response.getWriter().write(TemplateProcessor.instance().getPage("admin.html", body));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus( HttpServletResponse.SC_OK );
    }


}
