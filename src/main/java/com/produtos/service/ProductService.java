package com.produtos.service;

import com.produtos.dao.*;
import com.produtos.model.Product;
import com.produtos.model.User;
import com.produtos.structures.ProductBST;

import java.util.*;
import java.util.stream.Collectors;

public class ProductService {
    private ProductDAO productDAO;
    private UserDAO userDAO;
    private FavoriteDAO favoriteDAO;

    public ProductService() {
        this.productDAO = new ProductDAOImpl();
        this.userDAO = new UserDAOImpl();
        this.favoriteDAO = new FavoriteDAOImpl();
    }

    // --- Data Structure Integration (BST) ---
    
    /**
     * Retorna uma Árvore Binária de Busca populada com todos os produtos.
     * Isso permite busca e filtragem eficiente.
     */
    public ProductBST getProductsTree() {
        ProductBST tree = new ProductBST();
        List<Product> all = productDAO.getAllProducts();
        for (Product p : all) {
            tree.insert(p);
        }
        return tree;
    }

    public List<Product> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return listAll();
        }
        return getProductsTree().search(query);
    }

    public List<Product> filterProductsByCategory(String category) {
        if (category == null || category.trim().isEmpty() || category.equals("all")) {
            return listAll();
        }
        return getProductsTree().filterByCategory(category);
    }

    public List<String> getAllCategories() {
        return listAll().stream()
                .map(Product::getCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Product> listAll() {
        return productDAO.getAllProducts();
    }

    public Product findById(int id) {
        return productDAO.getProductById(id);
    }

    // --- Authentication ---
    public User login(String username, String password) {
        return userDAO.findByUsernameAndPassword(username, password);
    }

    // --- Favorites ---
    public void toggleFavorite(int userId, int productId) {
        if (favoriteDAO.isFavorite(userId, productId)) {
            favoriteDAO.removeFavorite(userId, productId);
        } else {
            favoriteDAO.addFavorite(userId, productId);
        }
    }

    public boolean isFavorite(int userId, int productId) {
        return favoriteDAO.isFavorite(userId, productId);
    }

    public Set<Integer> getFavoriteIds(int userId) {
        return new HashSet<>(favoriteDAO.getFavoriteProductIds(userId));
    }

    /**
     * Sugere produtos baseados em um produto específico.
     * Aplica Estruturas de Dados:
     * - Map para agrupamento por categoria.
     * - Set para evitar duplicatas.
     * - PriorityQueue para ordenar por preço (exemplo de lógica de negócio).
     */
    public List<Product> getSuggestions(Product product) {
        if (product == null)
            return Collections.emptyList();

        List<Product> allProducts = productDAO.getAllProducts();

        // 1. Usando Map para agrupar por categoria (demonstração de agrupamento
        // eficiente)
        Map<String, List<Product>> productsByCategory = allProducts.stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        // 2. Usando Set para garantir sugestões únicas (excluindo o próprio produto)
        Set<Product> suggestionsSet = new HashSet<>();

        // Adiciona produtos da mesma categoria
        List<Product> sameCategory = productsByCategory.getOrDefault(product.getCategory(), new ArrayList<>());
        for (Product p : sameCategory) {
            if (!p.getId().equals(product.getId())) {
                suggestionsSet.add(p);
            }
        }

        // 3. Usando PriorityQueue para pegar os produtos mais baratos da categoria
        // (como "oportunidades")
        PriorityQueue<Product> cheapestQueue = new PriorityQueue<>(Comparator.comparingDouble(Product::getPrice));
        cheapestQueue.addAll(suggestionsSet);

        List<Product> finalSuggestions = new ArrayList<>();
        int count = 0;
        while (!cheapestQueue.isEmpty() && count < 3) {
            finalSuggestions.add(cheapestQueue.poll());
            count++;
        }

        return finalSuggestions;
    }

    public void save(Product product) {
        if (product.getId() == null) {
            productDAO.addProduct(product);
        } else {
            productDAO.updateProduct(product);
        }
    }

    public void delete(int id) {
        productDAO.deleteProduct(id);
    }
}
