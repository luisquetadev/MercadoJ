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
        .container { padding: 20px; max-width: 1400px; margin: 0 auto; }
        .main-layout { display: flex; gap: 30px; }
        .sidebar { width: 300px; flex-shrink: 0; }
        .content { flex-grow: 1; }
        .sidebar-section { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); margin-bottom: 20px; }
        .sidebar-section h3 { font-size: 1rem; margin-top: 0; color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
        .history-list, .log-list { list-style: none; padding: 0; margin: 10px 0; font-size: 0.9rem; }
        .history-list li, .log-list li { padding: 8px 0; border-bottom: 1px solid #eee; }
        .history-list li:last-child, .log-list li:last-child { border-bottom: none; }
        .history-list a { color: #3498db; text-decoration: none; }
        .history-list a:hover { text-decoration: underline; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px; }
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
    
    <div class="container main-layout">
        <aside class="sidebar">
            <div class="sidebar-section" style="background: #2c3e50; color: white;">
                <h3><i class="fas fa-flask"></i> Lab de Estruturas</h3>
                <div style="font-size: 0.85rem; line-height: 1.4;">
                    <p><strong>BST (Busca):</strong> Usa <code>insert()</code> para montar e <code>search()</code> para encontrar em O(log n).</p>
                    <p><strong>Pilha (History):</strong> <code>push()</code> insere no topo, <code>peek()</code> olha o topo sem tirar.</p>
                    <p><strong>Fila (Buscas):</strong> <code>enqueue()</code> entra no fim, <code>dequeue()</code> sai no início.</p>
                    <p><strong>Lista (Logs):</strong> <code>addFirst()</code> insere no início em O(1).</p>
                    <p><strong>Nós:</strong> Todas usam <code>Node.next</code> para apontar ao próximo item.</p>
                </div>
            </div>

            <c:if test="${not empty recentSearches}">
                <div class="sidebar-section">
                    <h3><i class="fas fa-search"></i> Buscas Recentes (Fila)</h3>
                    <div style="display: flex; flex-wrap: wrap; gap: 8px; margin-top: 10px;">
                        <c:forEach var="query" items="${recentSearches}">
                            <div style="background: #e1f5fe; color: #0288d1; padding: 5px 12px; border-radius: 20px; font-size: 0.85rem; display: flex; align-items: center; gap: 10px; border: 1px solid #b3e5fc;">
                                <a href="products?search=${query}" style="color: inherit; text-decoration: none; font-weight: 500;">${query}</a>
                                <a href="remove-search?query=${query}" style="color: #e74c3c; text-decoration: none; font-weight: bold; font-size: 1.1rem; line-height: 1;" title="Remover (Método remove())">&times;</a>
                            </div>
                        </c:forEach>
                    </div>
                    <div style="margin-top: 12px; font-size: 0.8rem; color: #7f8c8d;">
                        <i class="fas fa-exchange-alt"></i> <strong>FIFO:</strong> Operação <code>enqueue()</code> ativa. <br>
                        <code>isEmpty()</code>: ${empty recentSearches ? 'Sim' : 'Não'}
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty viewHistory}">
                <div class="sidebar-section">
                    <h3><i class="fas fa-history"></i> Vistos (Pilha)</h3>
                    <ul class="history-list">
                        <c:forEach var="item" items="${viewHistory}">
                            <li><a href="product-details?id=${item.id}">${item.name}</a></li>
                        </c:forEach>
                    </ul>
                    <div style="font-size: 0.8rem; color: #7f8c8d;">
                        <i class="fas fa-layer-group"></i> <strong>LIFO:</strong> <code>top</code> = ${topHistory.name} <br>
                        <code>peek()</code> realizado com sucesso.
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty actionLog}">
                <div class="sidebar-section">
                    <h3><i class="fas fa-list-ul"></i> Log (Lista Encadeada)</h3>
                    <ul class="log-list">
                        <c:forEach var="log" items="${actionLog}">
                            <li>${log}</li>
                        </c:forEach>
                    </ul>
                    <div style="font-size: 0.8rem; color: #7f8c8d;">
                        <i class="fas fa-link"></i> <strong>Ponteiros:</strong> <code>head</code> aponta para: <br>
                        "${topAction}" (O(1))
                    </div>
                </div>
            </c:if>
        </aside>

        <main class="content">
            <div style="margin-bottom: 25px; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.05);">
                <form action="products" method="GET" style="display: flex; gap: 10px; margin: 0; flex-wrap: wrap;">
                    <div style="flex-grow: 1; position: relative; min-width: 300px;">
                        <i class="fas fa-search" style="position: absolute; left: 15px; top: 50%; transform: translateY(-50%); color: #7f8c8d;"></i>
                        <input type="text" name="search" value="${searchQuery}" placeholder="Pesquisar produtos (Busca em Árvore BST)..." 
                               style="width: 100%; padding: 12px 12px 12px 45px; border: 1px solid #ddd; border-radius: 25px; outline: none; font-size: 1rem;">
                    </div>
                    
                    <select name="category" onchange="this.form.submit()" 
                            style="padding: 10px 20px; border-radius: 25px; border: 1px solid #ddd; outline: none; background: white; cursor: pointer;">
                        <option value="all">Todas as Categorias</option>
                        <c:forEach var="cat" items="${categories}">
                            <option value="${cat}" ${selectedCategory == cat ? 'selected' : ''}>${cat}</option>
                        </c:forEach>
                    </select>

                    <button type="submit" class="btn btn-details" style="padding: 0 25px; border-radius: 25px;">Buscar</button>
                    <c:if test="${not empty searchQuery or (not empty selectedCategory and selectedCategory != 'all')}">
                        <a href="products" class="btn btn-logout" style="line-height: 40px;">Limpar</a>
                    </c:if>
                </form>
                <div style="margin-top: 10px; font-size: 0.85rem; color: #7f8c8d;">
                    <i class="fas fa-info-circle"></i> A busca e o filtro por categoria utilizam a <strong>Árvore Binária de Busca (BST)</strong> para maior performance.
                </div>
            </div>

            <c:if test="${empty products}">
                <div style="text-align: center; padding: 50px; background: white; border-radius: 10px;">
                    <h2>Nenhum produto encontrado.</h2>
                    <c:if test="${sessionScope.user.role == 'ADMIN'}">
                        <a href="product-form" class="btn btn-add">Cadastrar Primeiro Produto</a>
                    </c:if>
                </div>
            </c:if>
            
            <div class="grid" id="product-grid">
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
        </main>
    </div>

    <script>
        const searchInput = document.querySelector('input[name="search"]');
        const productGrid = document.getElementById('product-grid');
        const userRole = '${sessionScope.user.role}';
        const favoriteIds = [
            <c:forEach var="id" items="${favoriteIds}" varStatus="status">
                ${id}${not status.last ? ',' : ''}
            </c:forEach>
        ];

        searchInput.addEventListener('input', function(e) {
            const query = e.target.value;
            
            fetch(`api/search?search=\${encodeURIComponent(query)}`)
                .then(response => response.json())
                .then(products => {
                    renderProducts(products);
                })
                .catch(err => console.error('Erro na busca:', err));
        });

        function renderProducts(products) {
            if (products.length === 0) {
                productGrid.innerHTML = `
                    <div style="grid-column: 1 / -1; text-align: center; padding: 50px; background: white; border-radius: 10px;">
                        <h2>Nenhum produto encontrado para esta busca.</h2>
                    </div>
                `;
                return;
            }

            productGrid.innerHTML = products.map(p => `
                <div class="card">
                    \${userRole ? `
                        <a href="product-favorite?id=\${p.id}" class="favorite-btn">
                            <i class="\${favoriteIds.includes(p.id) ? 'fas' : 'far'} fa-star"></i>
                        </a>
                    ` : ''}
                    
                    <img src="\${p.imageUrl || 'https://via.placeholder.com/400x200?text=Sem+Imagem'}" class="card-img" alt="\${p.name}">
                    <div class="card-body">
                        <div class="card-category">\${p.category}</div>
                        <h3 class="card-title">\${p.name}</h3>
                        <div class="card-price">Kz \${p.price}</div>
                    </div>
                    <div class="card-footer">
                        <a href="product-details?id=\${p.id}" class="btn btn-details">Ver Detalhes</a>
                        \${userRole === 'ADMIN' ? `
                            <div>
                                <a href="product-form?id=\${p.id}" class="btn btn-edit" title="Editar"><i class="fas fa-edit"></i></a>
                                <a href="product-delete?id=\${p.id}" class="btn btn-delete" title="Excluir" onclick="return confirm('Excluir este produto?')"><i class="fas fa-trash"></i></a>
                            </div>
                        ` : ''}
                    </div>
                </div>
            `).join('');
        }
    </script>
</body>
</html>
