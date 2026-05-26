-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS mercado_java;
USE mercado_java;

-- Criar a tabela de produtos
CREATE TABLE IF NOT EXISTS product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    image_url TEXT
);

-- Criar a tabela de usuarios
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') DEFAULT 'USER'
);

-- Criar a tabela de favoritos
CREATE TABLE IF NOT EXISTS favorite (
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- Inserir dados iniciais de produtos
INSERT INTO product (name, category, price, description, image_url) VALUES 
('Smartphone X', 'Eletronicos', 2500.00, 'Celular top de linha com camera de 48MP', 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400'),
('Notebook Pro', 'Eletronicos', 5500.00, 'Notebook para trabalho com 16GB RAM', 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400'),
('Monitor 24', 'Eletronicos', 900.00, 'Monitor Full HD com taxa de 144Hz', 'https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=400'),
('Camisa Polo', 'Vestuario', 80.00, 'Camisa de algodao premium', 'https://images.unsplash.com/photo-1581655353564-df123a1eb820?w=400'),
('Tenis Esportivo', 'Vestuario', 300.00, 'Tenis confortavel para corrida', 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400'),
('Cafeteira Express', 'Eletrodomesticos', 450.00, 'Cafeteira automatica multi-capsulas', 'https://images.unsplash.com/photo-1520970014086-2208d157c9e2?w=400');

-- Inserir usuarios iniciais (admin/admin e user/user)
INSERT INTO user (username, password, role) VALUES 
('admin', 'admin', 'ADMIN'),
('user', 'user', 'USER');
