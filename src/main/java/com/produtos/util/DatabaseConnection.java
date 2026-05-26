package com.produtos.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Configurações do MySQL - Altere conforme seu ambiente
    private static final String URL = "jdbc:mysql://localhost:3306/mercado?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Coloque sua senha aqui

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("LOG: Driver MySQL carregado com sucesso.");
        } catch (ClassNotFoundException e) {
            System.err.println("LOG ERRO: Driver MySQL não encontrado!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("LOG ERRO: Falha ao conectar ao MySQL em " + URL);
            System.err.println("Certifique-se de que o banco 'mercado_java' existe e o usuário '" + USER + "' tem acesso.");
            throw e;
        }
    }
}
