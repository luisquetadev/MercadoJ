# Mercado Java - Sistema de Sugestões e Favoritos

Este projeto é uma aplicação Java Web (Jakarta EE) para gerenciamento de produtos, apresentando um sistema de autenticação, favoritos e sugestões inteligentes baseadas em estruturas de dados clássicas.

## 🚀 Estruturas de Dados Aplicadas

O sistema utiliza diversas estruturas de dados do Java Collections Framework para otimizar a lógica de negócio, especialmente no serviço de sugestões e na gestão de favoritos.

### 1. `List` (ArrayList)
- **Onde é aplicada:** 
    - No `ProductDAO` e `ProductService` para retornar todos os produtos do banco de dados.
    - No `FavoriteDAO` para listar os IDs dos produtos favoritos.
- **Como funciona:** 
    - A `List` é utilizada quando a ordem de inserção é importante ou quando precisamos de acesso indexado. É a estrutura ideal para exibir grades de produtos onde os dados são lidos sequencialmente do banco de dados.

### 2. `Map` (HashMap)
- **Onde é aplicada:** 
    - No método `getSuggestions` da classe `ProductService`.
- **Como funciona:** 
    - Utilizamos `Map<String, List<Product>>` para realizar um **agrupamento por categoria**. 
    - A chave do mapa é a `categoria` (String) e o valor é uma lista de produtos pertencentes a essa categoria. 
    - Isso permite que o sistema recupere instantaneamente todos os produtos de uma categoria específica sem precisar percorrer a lista completa de produtos várias vezes, otimizando a busca de itens relacionados.

### 3. `Set` (HashSet)
- **Onde é aplicada:** 
    - No método `getSuggestions` para filtrar duplicatas.
    - No `ProductServlet` para gerenciar os `favoriteIds` do usuário logado.
- **Como funciona:** 
    - **Unicidade:** No sistema de sugestões, o `Set` garante que o produto principal que o usuário está visualizando não apareça na lista de sugestões, evitando redundância.
    - **Performance de Busca:** Para os favoritos, usamos um `Set<Integer>`. Como o `HashSet` possui complexidade média de **O(1)** para a operação `contains()`, a verificação se um produto deve exibir a estrela preenchida ou vazia na interface é extremamente rápida, mesmo com muitos favoritos.

### 4. `PriorityQueue` (Heap)
- **Onde é aplicada:** 
    - No método `getSuggestions` para classificar as melhores "oportunidades" (menores preços).
- **Como funciona:** 
    - A `PriorityQueue` funciona como um **Min-Heap**. 
    - Configuramos um comparador baseado no preço (`Comparator.comparingDouble(Product::getPrice)`). 
    - Ao inserir os produtos da mesma categoria na fila, a estrutura organiza automaticamente o menor preço no topo. 
    - O sistema então remove (`poll`) os 3 primeiros itens, garantindo que o usuário receba sempre as sugestões mais baratas (mais atrativas) daquela categoria.

---

## 🔐 Sistema de Autenticação e Perfis

O sistema distingue entre dois tipos de usuários:

1.  **Administrador (ADMIN):**
    *   Pode cadastrar novos produtos.
    *   Pode editar e excluir produtos existentes.
    *   Pode favoritar produtos.
2.  **Usuário (USER):**
    *   Pode visualizar produtos e detalhes.
    *   Pode favoritar produtos para sua lista pessoal.
    *   **Não** tem acesso aos botões de edição, exclusão ou ao formulário de cadastro.

**Credenciais de Teste:**
- **Admin:** `admin` / `admin`
- **User:** `user` / `user`

---

## 🇦🇴 Localização
- A moeda do sistema foi configurada para **Kwanza (Kz)**, refletindo o mercado angolano.
- Interface responsiva com suporte a ícones (Font Awesome) para uma experiência de usuário moderna.

---

## 🛠️ Tecnologias Utilizadas
- **Linguagem:** Java 17+
- **Framework:** Jakarta EE (Servlets, JSP, JSTL)
- **Banco de Dados:** MySQL
- **Frontend:** HTML5, CSS3, JavaScript e Font Awesome
- **Gerenciador de Dependências:** Maven
