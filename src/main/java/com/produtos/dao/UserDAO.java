package com.produtos.dao;

import com.produtos.model.User;

public interface UserDAO {
    User findByUsernameAndPassword(String username, String password);
    User findById(int id);
}
