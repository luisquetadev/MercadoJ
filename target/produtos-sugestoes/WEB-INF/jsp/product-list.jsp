<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Catálogo de Produtos</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; background-color: #f4f7f6; color: #333; }
        header { background-color: #2c3e50; color: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; }
        .nav-links { display: flex; align-items: center; gap: 20px; }
        .container { padding: 20px; max-width: 1200px; margin: 0 auto; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px; margin-top: 20px; }
        .card { background: white; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.1); transition: transform 0.2s; position: relative; }
        .card:hover { transform: translateY(-5px); }
        .card-img { width: 100%; height: 200px; object-fit: cover; background-color: #eee; }
        .card-body { padding: 15px; }
        .card-title { font-size: 1.25rem; font-weight: bold; margin: 0 0 10px 0; }
        .card-category { font-size: 0.9rem; color: #7f8c8d; text-transform: uppercase; margin-bottom: 10px; }
        .card-price { font-size: 1.2rem; color: #27ae60; font-weight: bold; margin-bottom: 15px; }
        .card-footer { padding: 10px 15px; background: #f9f9f9; border-top: 1px solid #eee; display: flex; justify-content: space-between; align-items: center; }
        .btn { padding: 8px 12px; text-decoration: none; border-radius: 5px; font-size: 0.9rem; cursor: pointer; border: none; display: inline-block; }
        .btn-add { background-color: #27ae60; color: white; }
        .btn-details { background-color: #3498db; color: white; }
        .btn-edit { background-color: #f39c12; color: white; padding: 5px 8px; }
        .btn-delete { background-color: #e74c3c; color: white; padding: 5px 8px; }
        .btn-login { background-color: #ecf0f1; color: #2c3e50; }
        .btn-logout { color: #bdc3c7; text-decoration: none; font-size: 0.9rem; }
        .favorite-btn { 
            position: absolute; 
            top: 10px; 
            right: 10px; 
            background: rgba(255,255,255,0.8); 
            border-radius: 50%; 
            width: 35px; 
            height: 35px; 
            display: flex; 
            align-items: center; 
            justify-content: center; 
            color: #f1c40f; 
            text-decoration: none;
            box-shadow: 0 2px 4px rgba(0,0,0,0.2);
            z-index: 10;
        }
        .user-info { font-size: 0.9rem; color: #ecf0f1; }
        .admin-badge { background: #e74c3c; color: white; padding: 2px 6px; border-radius: 4px; font-size: 0.7rem; vertical-align: middle; margin-left: 5px; }
    </style>
</head>
<body>
    <header>
        <h1><a href="products" style="color: white; text-decoration: none;">Mercado Java</a></h1>
        <div class="nav-links">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <span class="user-info">
                        Ola, <strong>${sessionScope.user.username}</strong>
                        <c:if test="${sessionScope.user.role == 'ADMIN'}"><span class="admin-badge">ADMIN</span></c:if>
                    </span>
                    <c:if test="${sessionScope.user.role == 'ADMIN'}">
                        <a href="product-form" class="btn btn-add">+ Novo Produto</a>
                    </c:if>
                    <a href="logout" class="btn-logout"><i class="fas fa-sign-out-alt"></i> Sair</a>
                </c:when>
                <c:otherwise>
                    <a href="login" class="btn btn-login">Fazer Login</a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
    
    <div class="container">
        <c:if test="${empty products}">
            <div style="text-align: center; padding: 50px; background: white; border-radius: 10px;">
                <h2>Nenhum produto encontrado.</h2>
                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                    <a href="product-form" class="btn btn-add">Cadastrar Primeiro Produto</a>
                </c:if>
            </div>
        </c:if>
        
        <div class="grid">
            <c:forEach var="product" items="${products}">
                <div class="card">
                    <c:if test="${not empty sessionScope.user}">
                        <a href="product-favorite?id=${product.id}" class="favorite-btn">
                            <c:choose>
                                <c:when test="${favoriteIds.contains(product.id)}">
                                    <i class="fas fa-star"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="far fa-star"></i>
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </c:if>
                    
                    <img src="${not empty product.imageUrl ? product.imageUrl : 'https://via.placeholder.com/400x200?text=Sem+Imagem'}" class="card-img" alt="${product.name}">
                    <div class="card-body">
                        <div class="card-category">${product.category}</div>
                        <h3 class="card-title">${product.name}</h3>
                        <div class="card-price">Kz ${product.price}</div>
                    </div>
                    <div class="card-footer">
                        <a href="product-details?id=${product.id}" class="btn btn-details">Ver Detalhes</a>
                        <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <div>
                                <a href="product-form?id=${product.id}" class="btn btn-edit" title="Editar"><i class="fas fa-edit"></i></a>
                                <a href="product-delete?id=${product.id}" class="btn btn-delete" title="Excluir" onclick="return confirm('Excluir este produto?')"><i class="fas fa-trash"></i></a>
                            </div>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
