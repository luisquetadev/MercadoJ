package com.produtos.structures;

import com.produtos.model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Árvore Binária de Busca (BST - Binary Search Tree).
 * 
 * CONCEITO:
 * Cada nó tem no máximo dois filhos. Para qualquer nó:
 * - O filho à esquerda é MENOR.
 * - O filho à direita é MAIOR.
 * 
 * COMPLEXIDADE:
 * - Busca/Inserção: O(log n) em média. 
 * É muito mais rápido que buscar em uma lista (O(n)).
 */
public class ProductBST {
    
    /**
     * Nó da Árvore. Contém ponteiros para o filho esquerdo e direito.
     */
    private class TreeNode {
        Product product;
        TreeNode left;  // Ponteiro para subárvore esquerda
        TreeNode right; // Ponteiro para subárvore direita

        TreeNode(Product product) {
            this.product = product;
        }
    }

    private TreeNode root; // Raiz da árvore (entrada principal)

    /**
     * INSERÇÃO RECURSIVA:
     * Compara o nome do novo produto com o nó atual e decide se vai para a esquerda ou direita.
     */
    public void insert(Product product) {
        root = insertRecursive(root, product);
    }

    private TreeNode insertRecursive(TreeNode current, Product product) {
        if (current == null) {
            return new TreeNode(product);
        }

        if (product.getName().compareToIgnoreCase(current.product.getName()) < 0) {
            current.left = insertRecursive(current.left, product);
        } else if (product.getName().compareToIgnoreCase(current.product.getName()) > 0) {
            current.right = insertRecursive(current.right, product);
        } else {
            current.right = insertRecursive(current.right, product);
        }

        return current;
    }

    public List<Product> search(String query) {
        List<Product> results = new ArrayList<>();
        searchRecursive(root, query.toLowerCase(), results);
        return results;
    }

    private void searchRecursive(TreeNode current, String query, List<Product> results) {
        if (current == null) return;
        searchRecursive(current.left, query, results);
        if (current.product.getName().toLowerCase().contains(query)) {
            results.add(current.product);
        }
        searchRecursive(current.right, query, results);
    }

    public List<Product> filterByCategory(String category) {
        List<Product> results = new ArrayList<>();
        filterByCategoryRecursive(root, category.toLowerCase(), results);
        return results;
    }

    private void filterByCategoryRecursive(TreeNode current, String category, List<Product> results) {
        if (current == null) return;
        filterByCategoryRecursive(current.left, category, results);
        if (current.product.getCategory().toLowerCase().equals(category)) {
            results.add(current.product);
        }
        filterByCategoryRecursive(current.right, category, results);
    }

    public List<Product> getAllInOrder() {
        List<Product> results = new ArrayList<>();
        inOrderTraversal(root, results);
        return results;
    }

    private void inOrderTraversal(TreeNode current, List<Product> results) {
        if (current == null) return;
        inOrderTraversal(current.left, results);
        results.add(current.product);
        inOrderTraversal(current.right, results);
    }
}
