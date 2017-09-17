package ru.otus.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.servlets.service.AuthService;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configurable
public class AdminServlet extends HttpServlet {

    private AuthService authService;

    @Autowired
    private TestClass testClass;

    private String userName = "anonymous";

    public AdminServlet(){

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        testClass.startTestCache();

        String id = request.getSession().getId();
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        if (authService.contains(id)){

            userName = authService.get(id).getName();
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

    @Autowired
    private void setAuthService(AuthService service){
        this.authService = service;
    }





}
