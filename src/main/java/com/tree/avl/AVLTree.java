package com.tree.avl;

public class AVLTree<T extends Comparable> {

    private AVLNode<T> root;

    public static void main(String[] args) {
        AVLTree avl = new AVLTree<Integer>();
        avl.insert(1);
        avl.insert(2);
        avl.insert(3);
        avl.insert(4);
        avl.insert(5);
        avl.insert(6);
        avl.insert(7);
        avl.insert(8);
        avl.insert(9);
        avl.insert(10);
        avl.insert(11);
        avl.printTree(avl.root);

    }

    private void printTree(AVLNode avlNode){
        if(avlNode == null){
            return;
        }

        printTree(avlNode.left);
        printTree(avlNode.right);System.out.println(avlNode.data);

    }

    private int height(AVLNode<T> node) {
        if (node == null) return 0;
        return Math.max(height(node.left) + 1, height(node.right) + 1);
    }

    private AVLNode<T> singleRotateFromLeft(AVLNode<T> top) {
        AVLNode<T> newTop = top.left;
        top.left = newTop.right;
        newTop.right = top;
        return newTop;
    }

    private AVLNode<T> singleRotateFromRight(AVLNode<T> top) {
        AVLNode<T> newTop = top.right;
        top.right = newTop.left;
        newTop.left = top;
        return newTop;
    }

    private AVLNode<T> doubleRotateWithLeft(AVLNode<T> node) {
        node.left = singleRotateFromRight(node.left);
        return singleRotateFromLeft(node);
    }

    private AVLNode<T> doubleRotateWithRight(AVLNode<T> node) {
        node.right = singleRotateFromLeft(node.right);
        return singleRotateFromRight(node);
    }

    private AVLNode<T> insert(T data, AVLNode<T> node) {
        if (node == null) {
            node = new AVLNode<>(data);
        } else if (data.compareTo(node.data) < 0) {
            node.left = insert(data, node.left);
            if (height(node.left) - height(node.right) > 1) {
                if (data.compareTo(node.left.data) < 0) {
                    node = singleRotateFromLeft(node);
                } else {
                    node = doubleRotateWithLeft(node);
                }
            }
        } else if (data.compareTo(node.data) > 0) {
            node.right = insert(data, node.right);
            if (height(node.right) - height(node.left) > 1) {
                if (data.compareTo(node.right.data) > 0) {
                    node = singleRotateFromRight(node);
                } else {
                    node = doubleRotateWithRight(node);
                }
            }
        }
        node.height = height(node);
        return balance(node);
    }

    private AVLNode<T> insert2(T data, AVLNode<T> node) {
        if (node == null) {
            node = new AVLNode<>(data);
        } else if (data.compareTo(node.data) < 0) {
            node.left = insert(data, node.left);

        } else if (data.compareTo(node.data) > 0) {
            node.right = insert(data, node.right);
        }
        return balance(node);
    }

    private AVLNode<T> balance(AVLNode<T> node) {
        if (node == null) return null;
        if (height(node.left) - height(node.right) > 1) {
            if (height(node.left.left) >= height(node.left.right)) {
                node = singleRotateFromLeft(node);
            } else {
                node = doubleRotateWithLeft(node);
            }
        } else if (height(node.right) - height(node.left) > 1) {
            if (height(node.right.right) >= height(node.right.left)) {
                node = singleRotateFromRight(node);
            } else {
                node = doubleRotateWithRight(node);
            }
        }
        node.height = height(node);
        return node;
    }

    public void insert(T data) {
        if (data == null) return;
        root = insert(data, root);
    }

    public AVLNode<T> findMin(AVLNode<T> node) {
        if (node == null) return null;
        if (node.left == null) return node;
        return findMin(node.left);
    }

    public AVLNode<T> findMax(AVLNode<T> node) {
        if (node == null) return node;
        if (node.right == null) return node;
        return findMax(node.right);
    }

    public AVLNode<T> remove(T data, AVLNode<T> node) {
        if (node == null) return null;
        if (data.compareTo(node.data) < 0) {
            node.left = remove(data, node.left);
        } else if (data.compareTo(node.data) > 0) {
            node.right = remove(data, node.right);
        } else {
            if (node.left != null && node.right != null) {
                node.data = (T) findMin(node.right).data;
                node.right = remove(node.data, node.right);
            } else if (node.left == null) {
                node = node.right;
            } else if (node.right == null) {
                node = node.left;
            }
        }
        return balance(node);
    }

    public void remove(T data) {
        remove(data, root);
    }
}
