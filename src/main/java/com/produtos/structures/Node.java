package com.produtos.structures;

import java.io.Serializable;

/**
 * ESTRUTURA: Nó (Node)
 * CONCEITO: Unidade básica de memória para estruturas dinâmicas.
 * APLICAÇÃO NO PROJETO: Serve como base para a Lista Encadeada, Pilha e Fila.
 * 
 * Descrição: Cada nó armazena um dado (T data) e uma referência (ponteiro) 
 * para o próximo elemento da sequência, permitindo o encadeamento dinâmico.
 */
public class Node<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private T data; // Informação armazenada no nó
    private Node<T> next; // "Ponteiro" para o próximo elemento da estrutura

    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
