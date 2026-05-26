package com.produtos.controller;

import com.produtos.model.Product;
import com.produtos.model.User;
import com.produtos.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products", "/product-details", "/product-form", "/product-save", "/product-delete", "/product-favorite"})
public class ProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/product-details":
                showDetails(request, response);
                break;
            case "/product-form":
                if (isAdmin(request)) showForm(request, response);
                else response.sendRedirect("products");
                break;
            case "/product-delete":
                if (isAdmin(request)) deleteProduct(request, response);
                else response.sendRedirect("products");
                break;
            case "/product-favorite":
                toggleFavorite(request, response);
                break;
            default:
                listProducts(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isAdmin(request)) {
            saveProduct(request, response);
        } else {
            response.sendRedirect("products");
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user != null && user.isAdmin();
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productService.listAll();
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Set<Integer> favoriteIds = productService.getFavoriteIds(user.getId());
            request.setAttribute("favoriteIds", favoriteIds);
        }

        // Recuperar pilha de vistos recentemente e lista de ações
        request.setAttribute("viewHistory", session.getAttribute("viewHistory"));
        request.setAttribute("actionLog", session.getAttribute("actionLog"));

        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/product-list.jsp").forward(request, response);
    }

    private void toggleFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("id"));
        productService.toggleFavorite(user.getId(), productId);
        
        // --- APLICAÇÃO DE LISTA ENCADEADA (LinkedList) ---
        // Usada para registrar um log de ações recentes onde inserções no topo são frequentes.
        LinkedList<String> actionLog = (LinkedList<String>) session.getAttribute("actionLog");
        if (actionLog == null) actionLog = new LinkedList<>();
        
        Product p = productService.findById(productId);
        actionLog.addFirst("Favoritou: " + p.getName()); // addFirst é eficiente em LinkedList O(1)
        if (actionLog.size() > 5) actionLog.removeLast(); // Mantém apenas os 5 últimos
        session.setAttribute("actionLog", actionLog);

        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : "products");
    }

    private void showDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.findById(id);
        List<Product> suggestions = productService.getSuggestions(product);
        
        HttpSession session = request.getSession();
        
        // --- APLICAÇÃO DE PILHA (Stack) ---
        // Usada para rastrear o histórico de navegação (LIFO - Last In, First Out).
        Stack<Product> viewHistory = (Stack<Product>) session.getAttribute("viewHistory");
        if (viewHistory == null) viewHistory = new Stack<>();
        
        // Evita duplicatas consecutivas no topo da pilha
        if (viewHistory.isEmpty() || !viewHistory.peek().getId().equals(product.getId())) {
            viewHistory.push(product);
        }
        if (viewHistory.size() > 5) viewHistory.remove(0); // Limita o tamanho da pilha
        session.setAttribute("viewHistory", viewHistory);

        // Registro na Lista Encadeada
        LinkedList<String> actionLog = (LinkedList<String>) session.getAttribute("actionLog");
        if (actionLog == null) actionLog = new LinkedList<>();
        actionLog.addFirst("Visualizou: " + product.getName());
        if (actionLog.size() > 5) actionLog.removeLast();
        session.setAttribute("actionLog", actionLog);

        request.setAttribute("product", product);
        request.setAttribute("suggestions", suggestions);
        request.getRequestDispatcher("/WEB-INF/jsp/product-details.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            Product product = productService.findById(Integer.parseInt(idParam));
            request.setAttribute("product", product);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/product-form.jsp").forward(request, response);
    }

    private void saveProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            String priceStr = request.getParameter("price");
            String description = request.getParameter("description");
            String imageUrl = request.getParameter("imageUrl");

            System.out.println("LOG: Tentando salvar produto: " + name + " (ID: " + idParam + ")");

            Product product = new Product();
            if (idParam != null && !idParam.isEmpty()) {
                product.setId(Integer.parseInt(idParam));
            }
            product.setName(name);
            product.setCategory(category);
            product.setPrice(Double.parseDouble(priceStr));
            product.setDescription(description);
            product.setImageUrl(imageUrl);

            productService.save(product);
            System.out.println("LOG: Produto salvo com sucesso!");
            response.sendRedirect("products");
        } catch (Exception e) {
            System.err.println("LOG ERRO: Erro ao salvar produto: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao salvar: " + e.getMessage());
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        response.sendRedirect("products");
    }
}
