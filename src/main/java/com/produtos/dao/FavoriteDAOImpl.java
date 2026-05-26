package com.produtos.dao;

import com.produtos.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAOImpl implements FavoriteDAO {

    @Override
    public void addFavorite(int userId, int productId) {
        String sql = "INSERT IGNORE INTO favorite (user_id, product_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFavorite(int userId, int productId) {
        String sql = "DELETE FROM favorite WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isFavorite(int userId, int productId) {
        String sql = "SELECT 1 FROM favorite WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Integer> getFavoriteProductIds(int userId) {
        List<Integer> productIds = new ArrayList<>();
        String sql = "SELECT product_id FROM favorite WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    productIds.add(rs.getInt("product_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productIds;
    }
}
