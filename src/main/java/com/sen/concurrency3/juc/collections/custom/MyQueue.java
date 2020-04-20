package com.sen.concurrency3.juc.collections.custom;

/**
 * @Author: Sen
 * @Date: 2019/12/20 15:35
 * @Description: 非线程安全队列
 */
public class MyQueue<E> {

    private static class Node<E> {

        private E element;

        private Node next;

        public Node(E element) {
            this.element = element;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private Node<E> first = new Node<>(null);

    private Node<E> last = new Node<>(null);

    private int size;

    public MyQueue<E> addLast(E element) {
        Node<E> newNode = new Node<>(element);
        last.next = newNode;
        last = newNode;
        if (size == 0)
            first = newNode;
        size++;
        return this;
    }

    public E removeFist() {
        if (size == 0) return null;
        Node<E> temp = first;
        first = first.next;
        temp.next = null;
        size--;
        if (size == 0)
            last = null;
        return temp.getElement();
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public E peekFist(){
        return first == null ? null: first.getElement();
    }

    public E peekLast(){
        return last == null ? null : last.getElement();
    }

    public static void main(String[] args) {
        MyQueue<String> myQueue = new MyQueue<>();
        myQueue.addLast("Hello");
        myQueue.addLast("World");
        myQueue.addLast("Java");

        System.out.println(myQueue.removeFist());
        System.out.println(myQueue.removeFist());
        System.out.println(myQueue.removeFist());
    }
}
