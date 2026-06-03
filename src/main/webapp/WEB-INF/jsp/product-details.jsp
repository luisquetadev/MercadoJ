<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>${product.name} - Detalhes</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; background-color: #f4f7f6; color: #333; }
        header { background-color: #2c3e50; color: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; }
        .nav-links { display: flex; align-items: center; gap: 20px; }
        .container { padding: 40px 20px; max-width: 1000px; margin: 0 auto; }
        .product-header { display: flex; gap: 40px; background: white; padding: 30px; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); position: relative; }
        .product-image { width: 400px; height: 400px; object-fit: cover; border-radius: 10px; }
        .product-info h1 { margin-top: 0; color: #2c3e50; display: flex; align-items: center; gap: 15px; }
        .category { color: #3498db; text-transform: uppercase; font-weight: bold; letter-spacing: 1px; }
        .price { font-size: 2rem; color: #27ae60; font-weight: bold; margin: 20px 0; }
        .description { line-height: 1.6; color: #7f8c8d; }
        
        .suggestions-section { margin-top: 50px; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 20px; margin-top: 20px; }
        .sug-card { background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.1); text-align: center; padding-bottom: 15px; }
        .sug-img { width: 100%; height: 150px; object-fit: cover; }
        .sug-title { font-size: 1rem; margin: 10px 0; }
        .sug-price { color: #27ae60; font-weight: bold; }
        
        .back-btn { display: inline-block; margin-bottom: 20px; text-decoration: none; color: #3498db; font-weight: bold; }
        .btn { padding: 8px 12px; text-decoration: none; border-radius: 5px; font-size: 0.9rem; cursor: pointer; border: none; display: inline-block; }
        .btn-add { background-color: #27ae60; color: white; }
        .btn-login { background-color: #ecf0f1; color: #2c3e50; }
        .btn-logout { color: #bdc3c7; text-decoration: none; font-size: 0.9rem; }
        .user-info { font-size: 0.9rem; color: #ecf0f1; }
        .admin-badge { background: #e74c3c; color: white; padding: 2px 6px; border-radius: 4px; font-size: 0.7rem; vertical-align: middle; margin-left: 5px; }
        .fav-star { color: #f1c40f; text-decoration: none; font-size: 1.5rem; }
    </style>
</head>
<body>
    <header>
        <h1><a href="products" style="color: white; text-decoration: none;">Mercado J</a></h1>
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
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <a href="products" class="back-btn">&larr; Voltar ao Catálogo</a>
            <div style="background: #e8f4fd; padding: 10px 20px; border-radius: 10px; border-left: 5px solid #3498db; font-size: 0.9rem;">
                <i class="fas fa-microchip"></i> <strong>Operações Ativas:</strong> 
                <code>stack.push()</code> | <code>list.addFirst()</code> | <code>stack.peek()</code>
            </div>
        </div>
        
        <div class="product-header">
            <img src="${not empty product.imageUrl ? product.imageUrl : 'https://via.placeholder.com/400?text=Sem+Imagem'}" class="product-image">
            <div class="product-info">
                <span class="category">${product.category}</span>
                <h1>
                    ${product.name}
                </h1>
                <div class="price">Kz ${product.price}</div>
                <div class="description">
                    <h3>Descrição</h3>
                    <p>${product.description}</p>
                </div>
            </div>
        </div>

        <div class="suggestions-section">
            <h2>Quem viu este produto também se interessou por:</h2>
            <div class="grid">
                <c:forEach var="sug" items="${suggestions}">
                    <div class="sug-card">
                        <img src="${not empty sug.imageUrl ? sug.imageUrl : 'https://via.placeholder.com/200x150?text=Sem+Imagem'}" class="sug-img">
                        <h4 class="sug-title">${sug.name}</h4>
                        <div class="sug-price">Kz ${sug.price}</div>
                        <a href="product-details?id=${sug.id}" style="font-size: 0.8rem; color: #3498db;">Ver Detalhes</a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>
