package com.produtos.controller;

import com.produtos.model.User;
import com.produtos.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AuthServlet", urlPatterns = {"/login", "/logout"})
public class AuthServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if ("/logout".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect("login");
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = productService.login(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("products");
        } else {
            request.setAttribute("error", "Usuario ou senha invalidos");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }
}
