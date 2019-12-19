package com.sen.concurrency3.juc.collections.custom;

/**
 * @Auther: Sen
 * @Date: 2019/12/18 22:11
 * @Description:
 */
public class LinkedList<T> {

    private Node<T> root;

    private int size = 0;

    public void of(T ... ts) {
        for (T t : ts) {
            addFirst(t);
        }
    }

    public boolean contains(T t){
        Node<T> current = root;
        while (current != null) {
            if (t == current.t)
                return true;
            current = current.next;
        }
        return false;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public LinkedList<T> addFirst(T t){
        Node<T> newNode = new Node<>(t);
        if (root == null)
            root = newNode;
        else {
            Node<T> temp = root;
            root = newNode;
            root.next = temp;
        }
        size++;
        return this;
    }

    public Node<T> removeFirst(){
        Node<T> temp = root;
        root = root.next;
        return temp;
    }

    public String toString(){
        if (isEmpty())
            return "[]";
        else {
            StringBuilder builder = new StringBuilder("[");
            Node<T> current = root;
            while (current != null) {
                builder.append(current.t).append(" ,");
                current = current.next;
            }
            builder.replace(builder.length()-1, builder.length(), "]");
            return builder.toString();
        }
    }

    private static class Node<T>{

        private T t;

        private  Node<T> next;

        private Node(T t) {
            this.t = t;
        }
    }

    public static void main(String[] args) {
        final LinkedList<String> linkedList = new LinkedList<>();
        linkedList.of("Java","C","C++","Scala","GO");
        linkedList.addFirst("Ruby").addFirst("Python");
        System.out.println(linkedList.size());
        System.out.println(linkedList.isEmpty());
        System.out.println(linkedList.contains("Jav"));
        System.out.println(linkedList.toString());
        System.out.println(linkedList.removeFirst().t);
        System.out.println(linkedList.removeFirst().t);
        System.out.println(linkedList.removeFirst().t);
        System.out.println(linkedList.toString());
    }
}
