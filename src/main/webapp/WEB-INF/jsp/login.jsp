<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Login - Mercado Java</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }
        .login-card { background: white; padding: 40px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 100%; max-width: 400px; }
        .login-card h2 { text-align: center; color: #2c3e50; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; font-weight: bold; color: #7f8c8d; }
        .form-control { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; font-size: 1rem; }
        .btn-login { width: 100%; padding: 12px; background-color: #3498db; color: white; border: none; border-radius: 5px; font-size: 1.1rem; cursor: pointer; transition: background 0.3s; }
        .btn-login:hover { background-color: #2980b9; }
        .error-msg { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center; font-size: 0.9rem; }
        .back-link { display: block; text-align: center; margin-top: 20px; color: #7f8c8d; text-decoration: none; font-size: 0.9rem; }
        .back-link:hover { text-decoration: underline; }
        .info { margin-top: 20px; font-size: 0.8rem; color: #95a5a6; border-top: 1px solid #eee; padding-top: 15px; }
    </style>
</head>
<body>
    <div class="login-card">
        <h2>Acesse sua Conta</h2>
        
        <c:if test="${not empty error}">
            <div class="error-msg">${error}</div>
        </c:if>

        <form action="login" method="post">
            <div class="form-group">
                <label for="username">Usuário</label>
                <input type="text" id="username" name="username" class="form-control" required autofocus>
            </div>
            <div class="form-group">
                <label for="password">Senha</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn-login">Entrar</button>
        </form>
        
        <a href="products" class="back-link">Voltar para o Catálogo</a>

        <div class="info">
            <p><strong>Admin:</strong> admin / admin</p>
            <p><strong>User:</strong> user / user</p>
        </div>
    </div>
</body>
</html>
