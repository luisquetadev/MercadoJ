package com.produtos.structures;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ESTRUTURA: Pilha (Stack)
 * CONCEITO: LIFO (Last-In, First-Out) - O último a entrar é o primeiro a sair.
 * APLICAÇÃO NO PROJETO: Histórico de Produtos Visualizados Recentemente.
 * 
 * Por que usar: Ideal para navegação reversa, onde o último item visitado 
 * deve ser o primeiro a ser exibido no topo da lista de histórico.
 */
public class ProductStack<T> implements Iterable<T>, Serializable {
    private static final long serialVersionUID = 1L;
    private Node<T> top; // "Ponteiro" para o elemento que está no topo da pilha
    private int size;

    public ProductStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * PUSH: Insere um novo elemento no topo.
     * O novo nó aponta para o antigo topo, e ele se torna o novo topo.
     */
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    /**
     * POP: Remove e retorna o elemento do topo.
     */
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Pilha vazia");
        T data = top.getData();
        top = top.getNext(); // O topo passa a ser o próximo elemento
        size--;
        return data;
    }

    /**
     * PEEK (TOP): Apenas visualiza o elemento do topo sem removê-lo.
     */
    public T peek() {
        if (isEmpty()) return null;
        return top.getData();
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    /**
     * Remove um elemento da base da pilha se exceder o limite (usado para manter histórico curto).
     * Esta é uma operação atípica para pilha pura, mas útil para o requisito do projeto.
     */
    public void removeBottom() {
        if (isEmpty()) return;
        if (top.getNext() == null) {
            top = null;
            size = 0;
            return;
        }
        Node<T> current = top;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        current.setNext(null);
        size--;
    }

    public java.util.List<T> toList() {
        java.util.List<T> list = new java.util.ArrayList<>();
        for (T item : this) {
            list.add(item);
        }
        return list;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = top;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T data = current.getData();
                current = current.getNext();
                return data;
            }
        };
    }
}
