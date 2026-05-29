package com.produtos.structures;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de Lista Encadeada Simples.
 * 
 * CONCEITO:
 * Cada elemento conhece apenas o próximo. É uma estrutura dinâmica,
 * não precisamos definir o tamanho no início (diferente de um Array).
 */
public class CustomLinkedList<T> implements Iterable<T>, Serializable {
    private static final long serialVersionUID = 1L;
    private Node<T> head; // Início da lista
    private int size;

    public CustomLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * ADDFIRST: Insere no início da lista. 
     * Complexidade O(1) - Muito eficiente.
     */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    /**
     * REMOVELAST: Remove o último elemento.
     * Complexidade O(n) - Precisa percorrer toda a lista para achar o penúltimo.
     */
    public void removeLast() {
        if (isEmpty()) return;
        if (head.getNext() == null) {
            head = null;
            size = 0;
            return;
        }
        Node<T> current = head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        current.setNext(null);
        size--;
    }

    public T getFirst() {
        if (isEmpty()) return null;
        return head.getData();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
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
            private Node<T> current = head;

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
