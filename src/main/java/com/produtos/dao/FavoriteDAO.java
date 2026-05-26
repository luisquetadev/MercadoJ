package com.produtos.dao;

import java.util.List;

public interface FavoriteDAO {
    void addFavorite(int userId, int productId);
    void removeFavorite(int userId, int productId);
    boolean isFavorite(int userId, int productId);
    List<Integer> getFavoriteProductIds(int userId);
}
