package com.sen.concurrency3.juc.collections.custom;

/**
 * @Author: Sen
 * @Date: 2019/12/18 22:11
 * @Description: 带权栈
 */
public class PriorityLinkedList<T extends Comparable<T>> {

    private Node<T> root;

    private int size = 0;

    public void of(T... ts) {
        for (T t : ts) {
            addFirst(t);
        }
    }

    public boolean contains(T t) {
        Node<T> current = root;
        while (current != null) {
            if (t == current.t) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * 添加节点按权进行排序，升序排序
     * @param t
     * @return
     */
    public PriorityLinkedList<T> addFirst(T t) {
        Node<T> newNode = new Node<>(t);
        Node<T> pre = null;
        Node<T> current = root;
        if (root == null){
            root = newNode;
            return this;
        }
        // 添加的节点权比当前节点权值大，寻找合适的位置
        while (current != null && t.compareTo(current.t) > 0) {
            pre = current;
            current = current.next;
        }
        // 添加节点的权值最小
        if (pre == null) {
            newNode.next = root;
            root = newNode;
        } else {
            // 添加节点到合适的位置
            Node<T> temp = pre.next;
            pre.next = newNode;
            newNode.next = temp;
        }
        size++;
        return this;
    }

    public Node<T> removeFirst() {
        Node<T> temp = root;
        root = root.next;
        return temp;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder builder = new StringBuilder("[");
            Node<T> current = root;
            while (current != null) {
                builder.append(current.t).append(" ,");
                current = current.next;
            }
            builder.replace(builder.length() - 1, builder.length(), "]");
            return builder.toString();
        }
    }

    private static class Node<T> {

        private T t;

        private Node<T> next;

        private Node(T t) {
            this.t = t;
        }
    }

    public static void main(String[] args) {
        final PriorityLinkedList<Integer> list = new PriorityLinkedList<>();
        list.of(6, 2, 63, -3, 11, 4, 5);
        System.out.println(list.toString());
    }
}
