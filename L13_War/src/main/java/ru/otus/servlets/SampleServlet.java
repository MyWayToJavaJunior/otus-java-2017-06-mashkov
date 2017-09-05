package ru.otus.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SampleServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            response.getWriter().write("Hello");
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
