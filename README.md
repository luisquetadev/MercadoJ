# Sistema de Recomendação de Produtos - Mercado J

Este projeto é um sistema de recomendação de produtos desenvolvido em Java (Servlet/JSP) que demonstra a aplicação prática de diversas estruturas de dados fundamentais.

## 🚀 Estruturas de Dados Aplicadas

O sistema foi refatorado para utilizar implementações personalizadas de estruturas de dados, localizadas no pacote `com.produtos.structures`, para demonstrar conceitos de algoritmos e ponteiros.

### 1. Árvore Binária de Busca (BST - Binary Search Tree)
- **Onde é usada:** No sistema de busca e filtragem de produtos.
- **Implementação:** `ProductBST.java`.
- **Filtro por Categoria:** Além da busca por nome, a árvore agora suporta filtragem por categoria, percorrendo os nós de forma otimizada para agrupar produtos do mesmo tipo.

### 2. Pilha (Stack - LIFO)
- **Onde é usada:** Histórico de "Vistos Recentemente".
- **Implementação:** `ProductStack.java`.
- **Operações Fundamentais:** Implementa e utiliza explicitamente `push()` (adicionar ao topo), `pop()` (remover do topo), `peek()` (visualizar topo) e `isEmpty()` (verificar se está vazia).

### 3. Lista Encadeada (Linked List)
- **Onde é usada:** Log de Ações Recentes.
- **Implementação:** `CustomLinkedList.java`.
- **Conceito:** Uma lista dinâmica onde cada elemento aponta para o próximo. É ideal para o log de ações pois permite inserções no início (O(1)) de forma extremamente rápida.

### 4. Fila (Queue - FIFO)
- **Onde é usada:** Histórico de Pesquisas Recentes.
- **Implementação:** `ProductQueue.java`.
- **Conceito:** O primeiro item a ser pesquisado é o primeiro a ser mantido, garantindo a ordem cronológica das buscas do usuário.

### 5. Ponteiros e Referências
- **Demonstração:** Todas as estruturas acima (`BST`, `Stack`, `LinkedList`, `Queue`) foram construídas do zero utilizando a classe `Node.java`.
- **Aplicação:** Em Java, "ponteiros" são as referências de objetos. Ao definir `Node next;` ou `TreeNode left, right;`, estamos utilizando ponteiros para criar estruturas de dados dinâmicas na memória Heap.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Web:** Jakarta Servlet 6.0, JSP (JavaServer Pages)
- **Banco de Dados:** MySQL (com `DatabaseConnection` utilitário)
- **Estilização:** CSS3 e FontAwesome para ícones.
- **Build:** Maven

## 📂 Estrutura do Pacote de Dados

```text
src/main/java/com/produtos/structures/
├── Node.java               <-- A base de tudo (Ponteiros/Referências)
├── ProductBST.java         <-- Árvore para Busca e Filtragem
├── ProductStack.java       <-- Pilha para Histórico (LIFO)
├── ProductQueue.java       <-- Fila para Pesquisas (FIFO)
└── CustomLinkedList.java   <-- Lista Encadeada para Logs
```

## 💻 Como Rodar

1. Certifique-se de ter o MySQL instalado e configure os dados em `DatabaseConnection.java`.
2. Execute o script `schema.sql` (em `resources`) para criar as tabelas.
3. Compile o projeto com `mvn clean package`.
4. Faça o deploy do `.war` em um servidor Tomcat 10+.
