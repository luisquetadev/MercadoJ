package com.produtos.structures;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ESTRUTURA: Fila (Queue)
 * CONCEITO: FIFO (First-In, First-Out) - O primeiro a entrar é o primeiro a sair.
 * APLICAÇÃO NO PROJETO: Gerenciamento do Histórico de Pesquisas Recentes.
 * 
 * Por que usar: Garante que as pesquisas sejam processadas na ordem em que ocorreram,
 * mantendo o limite de itens recentes de forma organizada.
 */
public class ProductQueue<T> implements Iterable<T>, Serializable {
    private static final long serialVersionUID = 1L;
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public void enqueue(T data) {
        // Se já existe na fila, removemos para re-inserir no fim (tornar mais recente)
        remove(data);
        
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T data = front.getData();
        front = front.getNext();
        if (front == null) rear = null;
        size--;
        return data;
    }

    /**
     * Remove um item específico da fila.
     * Necessário para a funcionalidade do "X" no histórico.
     */
    public void remove(T data) {
        if (isEmpty()) return;

        // Se for o primeiro
        if (front.getData().equals(data)) {
            dequeue();
            return;
        }

        Node<T> current = front;
        while (current.getNext() != null) {
            if (current.getNext().getData().equals(data)) {
                // Se for o último, atualiza o rear
                if (current.getNext() == rear) {
                    rear = current;
                }
                current.setNext(current.getNext().getNext());
                size--;
                return;
            }
            current = current.getNext();
        }
    }

    public boolean isEmpty() {
        return front == null;
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
            private Node<T> current = front;
            @Override
            public boolean hasNext() { return current != null; }
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
