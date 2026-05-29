# 🧠 Explicação Técnica: Linha por Linha do Código

Este guia disseca os métodos mais importantes do projeto, explicando a lógica por trás de cada linha para que você possa entender e defender seu código.

---

## 1. A Base: Classe `Node<T>` (O "Ponteiro")

```java
public class Node<T> implements Serializable {
    private T data;       // Aqui guardamos o Objeto (ex: um Produto ou uma String)
    private Node<T> next;  // Este é o "Ponteiro". Ele guarda a referência para outro objeto Node.
}
```
*   **Linha `private Node<T> next`**: Esta é a mágica. O nó contém a si mesmo como um atributo. É assim que criamos correntes infinitas na memória.

---

## 2. Pilha: Método `push(T data)` (Inserção no Topo)

```java
public void push(T data) {
    Node<T> newNode = new Node<>(data); // 1. Criamos um novo nó com o produto.
    newNode.setNext(top);               // 2. O novo nó aponta para quem era o antigo topo.
    top = newNode;                      // 3. O 'ponteiro' top agora aponta para este novo nó.
    size++;                             // 4. Aumentamos o contador.
}
```
*   **Por que o `setNext(top)` vem antes?** Porque se você mudar o `top` primeiro, você perde a referência do resto da pilha. Você precisa "segurar" a pilha antiga com o novo nó antes de avisar que ele é o novo chefe.

---

## 3. Lista Encadeada: Método `addFirst(T data)`

```java
public void addFirst(T data) {
    Node<T> newNode = new Node<>(data); // Criamos o vagão.
    newNode.setNext(head);              // O vagão aponta para o atual primeiro vagão (head).
    head = newNode;                     // O head (início do trem) passa a ser este novo vagão.
    size++;
}
```
*   **Diferença da Pilha:** Na prática, o `addFirst` da lista e o `push` da pilha são idênticos em lógica, pois ambos manipulam o início da corrente.

---

## 4. Árvore Binária (BST): Método `insertRecursive`

Este é o cérebro da busca rápida.

```java
private TreeNode insertRecursive(TreeNode current, Product product) {
    if (current == null) {
        return new TreeNode(product); // 1. Se chegamos num lugar vazio, plantamos o nó aqui.
    }

    // 2. Comparamos o nome do novo produto com o nome do produto no nó atual.
    if (product.getName().compareToIgnoreCase(current.product.getName()) < 0) {
        // 3. Se for MENOR (alfabeticamente), tentamos inserir na ESQUERDA.
        current.left = insertRecursive(current.left, product);
    } else {
        // 4. Se for MAIOR ou IGUAL, tentamos inserir na DIREITA.
        current.right = insertRecursive(current.right, product);
    }

    return current; // 5. Retornamos o nó atualizado para manter a árvore ligada.
}
```
*   **Recursividade:** O método chama a si mesmo. É como se ele dissesse: "Eu não posso ser seu pai, pergunte ao meu filho da esquerda se ele pode".

---

## 5. Árvore Binária (BST): Método `searchRecursive`

```java
private void searchRecursive(TreeNode current, String query, List<Product> results) {
    if (current == null) return; // Se o galho está vazio, paramos.

    searchRecursive(current.left, query, results); // 1. Primeiro olha tudo na esquerda (In-Order).

    if (current.product.getName().toLowerCase().contains(query)) {
        results.add(current.product); // 2. Se o nome contém o que buscamos, adiciona na lista.
    }

    searchRecursive(current.right, query, results); // 3. Depois olha tudo na direita.
}
```
*   **In-Order Traversal:** Ao olhar `Esquerda -> Atual -> Direita`, garantimos que os resultados da busca apareçam em **ordem alfabética perfeita**, pois a árvore foi montada assim.

---

## 6. O Filtro de Categoria (Aproveitando a Árvore)

```java
if (current.product.getCategory().toLowerCase().equals(category)) {
    results.add(current.product);
}
```
*   Aqui, em vez de comparar o nome para decidir o caminho, percorremos a árvore filtrando apenas os produtos que dão "match" com a categoria selecionada.

---

## 💡 Resumo para Defesa do Código
Ao explicar esse código, foque no termo **"Encadeamento Dinâmico"**. Diferente de um `ArrayList` que reserva um bloco grande de memória (como um estacionamento), nossas estruturas criam memória conforme a necessidade (como puxar uma cadeira extra para a mesa).
