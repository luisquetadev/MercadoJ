# 🎓 Aula Magna: Estruturas de Dados na Prática (Mercado J)

Bem-vindo! Se você nunca ouviu falar de estruturas de dados, pense nelas como **diferentes formas de organizar objetos em um armazém**. Se você organizar bem, encontra o que precisa rápido; se organizar mal, perde tempo procurando.

Neste projeto, transformamos o sistema de produtos em um laboratório vivo. Vamos entender cada conceito como se estivéssemos montando peças de um quebra-cabeça.

---

## 1. O "Nó" (Node): A Peça Fundamental
Imagine que você tem várias caixas de sapatos. Em cada caixa, você coloca um **Produto** e um **Bilhete** dizendo onde está a próxima caixa.

*   **O que é no código?** É a classe `Node.java`.
*   **O "Ponteiro":** O bilhete com o endereço da próxima caixa é o que chamamos de **Ponteiro** (ou Referência em Java). É ele que liga uma caixa à outra.
*   **Exemplo:** Se você tem a Caixa A e o bilhete nela aponta para a Caixa B, você acabou de criar uma conexão!

---

## 2. A Pilha (Stack): O Conceito LIFO
Pense em uma **pilha de pratos**.
1.  Você coloca um prato (Push).
2.  Coloca outro por cima (Push).
3.  Para lavar, você tira o que está **no topo** (Pop).

*   **Regra de Ouro (LIFO):** *Last-In, First-Out* (O último que entra é o primeiro que sai).
*   **No Projeto:** Usamos isso para o **Histórico de Vistos Recentemente**. O último produto que você clicou deve ser o primeiro a aparecer na lista lateral.
*   **Funções Principais:**
    *   `push()`: Coloca um produto no topo da pilha.
    *   `pop()`: Tira o produto do topo.
    *   `peek()` (ou Top): Apenas olha quem está no topo sem tirar.
    *   `isEmpty()`: Pergunta: "A pilha está vazia?".

---

## 3. Lista Encadeada (Linked List): O Trem de Dados
Imagine um trem. Cada vagão está engatado no próximo. Se você quiser adicionar um vagão no começo, é muito fácil: basta engatar o novo vagão na frente do antigo primeiro vagão.

*   **No Projeto:** Usamos para o **Log de Ações** (Ex: "Você favoritou X", "Você viu Y"). 
*   **Por que usar?** Porque queremos que a ação mais nova apareça sempre no topo da lista de logs rapidamente.
*   **Vantagem:** Diferente de uma lista comum (Array), não precisamos dizer o tamanho da lista no começo. Ela cresce conforme a necessidade, vagão por vagão.

---

## 4. Árvore Binária de Busca (BST): O GPS da Informação
Esta é a estrutura mais "inteligente" do projeto. Imagine que você está procurando um nome em uma agenda telefônica gigante. Em vez de ler nome por nome desde a letra A, você abre no meio.
*   Se o nome que você quer vem antes, você descarta toda a metade da direita.
*   Se vem depois, descarta a metade da esquerda.

**Na nossa Árvore (`ProductBST.java`):**
1.  Temos uma **Raiz** (o primeiro produto).
2.  Produtos com nomes "menores" (ex: começam com A) vão para a **Esquerda**.
3.  Produtos com nomes "maiores" (ex: começam con Z) vão para a **Direita**.

*   **A Mágica da Busca:** Quando você digita "Celular", o computador não olha todos os 1000 produtos. Ele pergunta para a raiz: "Celular é maior ou menor que você?". Em poucos passos (O log n), ele encontra o que você quer.
*   **Categorias:** Aplicamos a mesma lógica para agrupar produtos da mesma categoria rapidamente.

---

## 5. Como explicar isso para alguém? (Dicas de Ouro)

Se alguém te perguntar: *"Por que você não usou uma lista comum do Java para tudo?"*, você responde:

1.  **Pela Performance:** "Uma lista comum é como procurar uma chave em uma sacola bagunçada. A **Árvore** é como procurar um livro em uma biblioteca organizada por seções."
2.  **Pela Ordem:** "A **Pilha** garante que o usuário veja o que acabou de acessar primeiro, respeitando a ordee m natural de navegação humana."
3.  **Pela Memória:** "Usando **Nós e Ponteiros**, nossa aplicação só gasta memória para o que realmente existe. É como um trem que só adiciona vagões quando tem passageiros."

---

## Resumo Visual dos Arquivos
*   `Node.java`: A caixa e o bilhete (Ponteiro).
*   `ProductStack.java`: A pilha de pratos (Histórico).
*   `CustomLinkedList.java`: O trem de logs (Ações).
*   `ProductBST.java`: O GPS de busca (Performance).

**Agora você não é apenas um usuário, você é o arquiteto que entende como a informação flui!** 🚀
