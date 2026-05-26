<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>${product != null ? 'Editar' : 'Novo'} Produto</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; background-color: #f4f7f6; color: #333; }
        header { background-color: #2c3e50; color: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; }
        .nav-links { display: flex; align-items: center; gap: 20px; }
        .container { padding: 40px 20px; }
        .form-card { background: white; max-width: 600px; margin: 0 auto; padding: 30px; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); }
        h1 { color: #2c3e50; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: bold; color: #34495e; }
        input[type="text"], input[type="number"], textarea { 
            width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box; font-size: 1rem;
        }
        .btn { padding: 8px 12px; text-decoration: none; border-radius: 5px; font-size: 0.9rem; cursor: pointer; border: none; display: inline-block; }
        .btn-add { background-color: #27ae60; color: white; }
        .btn-submit { background-color: #27ae60; color: white; padding: 12px 25px; border: none; border-radius: 6px; cursor: pointer; font-size: 1rem; width: 100%; }
        .btn-login { background-color: #ecf0f1; color: #2c3e50; }
        .btn-logout { color: #bdc3c7; text-decoration: none; font-size: 0.9rem; }
        .btn-cancel { display: block; text-align: center; margin-top: 15px; color: #7f8c8d; text-decoration: none; }
        .user-info { font-size: 0.9rem; color: #ecf0f1; }
        .admin-badge { background: #e74c3c; color: white; padding: 2px 6px; border-radius: 4px; font-size: 0.7rem; vertical-align: middle; margin-left: 5px; }
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
        <div class="form-card">
            <h1>${product != null ? 'Editar' : 'Novo'} Produto</h1>
            
            <form action="product-save" method="post">
                <c:if test="${product != null}">
                    <input type="hidden" name="id" value="${product.id}">
                </c:if>

                <div class="form-group">
                    <label for="name">Nome do Produto:</label>
                    <input type="text" id="name" name="name" value="${product.name}" placeholder="Ex: Teclado Mecânico RGB" required>
                </div>

                <div class="form-group">
                    <label for="category">Categoria:</label>
                    <input type="text" id="category" name="category" value="${product.category}" placeholder="Ex: Eletronicos" required>
                </div>

                <div class="form-group">
                    <label for="price">Preço (Kz):</label>
                    <input type="number" step="0.01" id="price" name="price" value="${product.price}" placeholder="0.00" required>
                </div>

                <div class="form-group">
                    <label for="imageUrl">URL da Imagem:</label>
                    <input type="text" id="imageUrl" name="imageUrl" value="${product.imageUrl}" placeholder="https://exemplo.com/imagem.jpg">
                    <small style="color: #95a5a6;">Dica: Use links de imagens do Unsplash ou Google.</small>
                </div>

                <div class="form-group">
                    <label for="description">Descrição Detalhada:</label>
                    <textarea id="description" name="description" rows="5" placeholder="Descreva as características do produto...">${product.description}</textarea>
                </div>

                <button type="submit" class="btn-submit">Confirmar e Salvar</button>
                <a href="products" class="btn-cancel">Cancelar e Voltar</a>
            </form>
        </div>
    </div>
</body>
</html>
