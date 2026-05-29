package com.produtos.structures;

import java.io.Serializable;

/**
 * Representa um nó genérico que é a unidade fundamental de estruturas dinâmicas.
 * 
 * CONCEITO DE PONTEIROS:
 * Em linguagens como C, usamos ponteiros de memória. Em Java, usamos referências de objetos.
 * O atributo 'next' é um "ponteiro" que guarda o endereço de memória do próximo nó,
 * permitindo criar uma cadeia (lista) que pode crescer indefinidamente.
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
