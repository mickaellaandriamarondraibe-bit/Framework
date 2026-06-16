package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontControllerServlet extends HttpServlet {

    private List<Class<?>> controllers = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        String packageName = getServletConfig().getInitParameter("controller-package");

        try {
            this.controllers = Utilitaire.getClassesWithAnnotation(packageName, "controller.Annotation");

            System.out.println("Package scanné : " + packageName);
            System.out.println("Nombre de controllers trouvés : " + this.controllers.size());

            for (Class<?> controller : this.controllers) {
                System.out.println("Controller trouvé : " + controller.getName());
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        String route = requestURI.substring(contextPath.length());

        if (route.equals("")) {
            route = "/";
        }

        response.getWriter().println("<h1>FrontControllerServlet</h1>");
        response.getWriter().println("<p>Context path : " + contextPath + "</p>");
        response.getWriter().println("<p>Request URI : " + requestURI + "</p>");
        response.getWriter().println("<p>Route capturée : " + route + "</p>");
        response.getWriter().println("<p>Méthode HTTP : " + method + "</p>");

        response.getWriter().println("<hr>");
        response.getWriter().println("<h2>Controllers trouvés</h2>");
        response.getWriter().println("<p>Nombre : " + controllers.size() + "</p>");

        for (Class<?> controller : controllers) {
            response.getWriter().println("<p>Controller trouvé : " + controller.getName() + "</p>");
        }
    }
}