package ru.otus.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.interfaces.DBService;
import ru.otus.models.UserDataSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configurable
public class LoginServlet extends HttpServlet {

    @Autowired
    private DBService dbService;


    @Autowired
    private AuthService authService;

    @Autowired
    private TestClass testClass;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> data = new HashMap<>();
        data.put("user", "anonymous");
        data.put("message", "Paste user name and password");

        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        if (name != null && pass != null) {
            UserDataSet user = dbService.readByName(name);
            if (user == null) {
                data.put("message", "User does not exist. Right login: anton, pass: admin");
            } else if (user.getPass().equals(pass)) {
                data.put("user", user.getName());
                data.put("message", "You have been successfully authorized");
                if (user.getStatus().equals("admin")) {
                    authService.put(request.getSession().getId(), user);
                }
            } else {
                data.put("message", "wrong password. Right login: anton, pass: admin");
            }
        }

        try {
            response.getWriter().write(TemplateProcessor.instance().getPage("login.html", data));
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
