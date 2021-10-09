package com.tree.avl;

public class AVLNode<T extends Comparable> {
    public AVLNode left;
    public AVLNode right;
    public T data;
    public int height;

    public AVLNode(T data) {
        this.data = data;
        height = 1;
    }
}