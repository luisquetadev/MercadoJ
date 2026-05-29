package com.produtos.controller;

import com.produtos.model.Product;
import com.produtos.model.User;
import com.produtos.service.ProductService;
import com.produtos.structures.CustomLinkedList;
import com.produtos.structures.ProductStack;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products", "/product-details", "/product-form", "/product-save", "/product-delete", "/product-favorite", "/api/search"})
public class ProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path == null || path.equals("/")) {
            path = "/products";
        }

        switch (path) {
            case "/api/search":
                apiSearch(request, response);
                break;
            case "/product-details":
                showDetails(request, response);
                break;
            case "/product-form":
                if (isAdmin(request)) showForm(request, response);
                else response.sendRedirect(request.getContextPath() + "/products");
                break;
            case "/product-delete":
                if (isAdmin(request)) deleteProduct(request, response);
                else response.sendRedirect(request.getContextPath() + "/products");
                break;
            case "/product-favorite":
                toggleFavorite(request, response);
                break;
            case "/products":
            default:
                listProducts(request, response);
                break;
        }
    }

    private void apiSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getParameter("search");
        List<Product> products = productService.searchProducts(query);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Construção manual de JSON simples para evitar dependências externas
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            json.append("{")
                .append("\"id\":").append(p.getId()).append(",")
                .append("\"name\":\"").append(escapeJson(p.getName())).append("\",")
                .append("\"category\":\"").append(escapeJson(p.getCategory())).append("\",")
                .append("\"price\":").append(p.getPrice()).append(",")
                .append("\"imageUrl\":\"").append(p.getImageUrl() != null ? escapeJson(p.getImageUrl()) : "").append("\"")
                .append("}");
            if (i < products.size() - 1) json.append(",");
        }
        json.append("]");
        
        response.getWriter().write(json.toString());
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
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
        String query = request.getParameter("search");
        String category = request.getParameter("category");
        List<Product> products;
        
        // --- APLICAÇÃO DE ÁRVORE BINÁRIA DE BUSCA (BST) ---
        // A BST permite buscar por nome ou filtrar por categoria com complexidade O(log n).
        // Isso é muito mais eficiente do que percorrer uma lista do banco de dados manualmente.
        if (query != null && !query.trim().isEmpty()) {
            products = productService.searchProducts(query);
            request.setAttribute("searchQuery", query);
        } else if (category != null && !category.trim().isEmpty() && !category.equals("all")) {
            products = productService.filterProductsByCategory(category);
            request.setAttribute("selectedCategory", category);
        } else {
            products = productService.listAll();
        }
        
        // Lista de categorias para o filtro
        request.setAttribute("categories", productService.getAllCategories());
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Set<Integer> favoriteIds = productService.getFavoriteIds(user.getId());
            request.setAttribute("favoriteIds", favoriteIds);
        }

        // --- MANIPULAÇÃO DA PILHA (STACK) E LISTA ENCADEADA ---
        // Recuperamos as estruturas da sessão. Elas foram implementadas do zero
        // para demonstrar o uso de ponteiros e alocação dinâmica.
        ProductStack<Product> viewHistory = (ProductStack<Product>) session.getAttribute("viewHistory");
        if (viewHistory != null) {
            // Usamos o método toList() para que o JSTL consiga iterar os dados no JSP.
            request.setAttribute("viewHistory", viewHistory.toList());
            // Operação PEEK (TOP): Visualizamos o elemento do topo sem remover.
            request.setAttribute("topHistory", viewHistory.peek());
        }

        CustomLinkedList<String> actionLog = (CustomLinkedList<String>) session.getAttribute("actionLog");
        if (actionLog != null) {
            request.setAttribute("actionLog", actionLog.toList());
            // Operação GETFIRST: Em listas encadeadas, o acesso ao primeiro nó é O(1).
            request.setAttribute("topAction", actionLog.getFirst());
        }

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
        
        // --- INSERÇÃO NA LISTA ENCADEADA ---
        CustomLinkedList<String> actionLog = (CustomLinkedList<String>) session.getAttribute("actionLog");
        if (actionLog == null) actionLog = new CustomLinkedList<>();
        
        Product p = productService.findById(productId);
        // Operação ADDFIRST: Adiciona no início da lista (ponteiro head).
        actionLog.addFirst("Favoritou: " + p.getName());
        // Se a lista crescer muito, removemos o último nó (ponteiro last traversal).
        if (actionLog.size() > 5) actionLog.removeLast(); 
        session.setAttribute("actionLog", actionLog);

        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : "products");
    }

    private void showDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.findById(id);
        List<Product> suggestions = productService.getSuggestions(product);
        
        HttpSession session = request.getSession();
        
        // --- INSERÇÃO NA PILHA (STACK) ---
        ProductStack<Product> viewHistory = (ProductStack<Product>) session.getAttribute("viewHistory");
        if (viewHistory == null) viewHistory = new ProductStack<>();
        
        // Operação PEEK para evitar duplicatas consecutivas.
        if (viewHistory.isEmpty() || !viewHistory.peek().getId().equals(product.getId())) {
            // Operação PUSH: Adiciona o novo produto no topo da pilha.
            viewHistory.push(product);
        }
        // Mantemos a pilha com tamanho limitado para o histórico.
        if (viewHistory.size() > 5) viewHistory.removeBottom();
        session.setAttribute("viewHistory", viewHistory);

        // Registro na Lista Encadeada Customizada
        CustomLinkedList<String> actionLog = (CustomLinkedList<String>) session.getAttribute("actionLog");
        if (actionLog == null) actionLog = new CustomLinkedList<>();
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
