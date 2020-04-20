package com.sen.concurrency3.juc.collections.custom;

import java.util.Random;
/**
 * @Author: Sen
 * @Date: 2019/12/18 23:48
 * @Description: 简单的跳表实现
 */
public class SimpleSkipList {

    private static final byte HEAD_TYPE = 1;

    private static final byte DEFAULT_TYPE = 0;

    private static final byte TAIL_TYPE = -1;

    private final Random random;

    private Node head;

    private Node tail;

    private int size;

    private int hight = 1;

    public SimpleSkipList() {
        this.head = new Node(null, HEAD_TYPE);
        this.tail = new Node(null, TAIL_TYPE);
        head.right = tail;
        tail.left = head;
        this.random = new Random(System.currentTimeMillis());
    }

    private Node find(Integer element) {
        Node current = head;
        for (; ; ) {
            //满足循环条件右移
            while (current.right.type != TAIL_TYPE && current.right.value <= element) {
                current = current.right;
            }
            //跳出循环条件下移
            if (current.down != null) {
                current = current.down;
            }else {
                break;
            }
        }
        return current;  //current.value <= element < current.right.value;
    }

    /**
     * 添加元素
     *
     * @param element
     */
    public void add(Integer element) {
        //最近的左节点
        Node pre = find(element);
        Node newNode = new Node(element);
        newNode.left = pre;
        newNode.right = pre.right;
        pre.right.left = newNode;
        pre.right = newNode;
        //随机算法是否提高
        int currentHeight = 1;
        while (random.nextDouble() <= 0.3d) {
            //当前高度高于原来的高度时直接将提高的节点和头尾节点建立关系
            if (currentHeight >= hight) {
                hight++;
                Node newHead = new Node(null, HEAD_TYPE);
                Node newTail = new Node(null, TAIL_TYPE);

                newHead.down = head;
                head.up = newHead;
                newHead.right = newTail;

                newTail.down = tail;
                tail.up = newTail;
                newTail.left = newHead;
                //更新头部尾部
                head = newHead;
                tail = newTail;
            }
            //左移寻找提高一层所要建立关系的最近左节点
            while (pre != null && pre.up == null) {
                pre = pre.left;
            }
            //更新pre将pre移到当前高度
            pre = pre.up;
            Node upNode = new Node(element);
            upNode.left = pre;
            upNode.right = pre.right;
            pre.right.left = upNode;
            pre.right = upNode;

            upNode.down = newNode;
            newNode.up = upNode;
            //更新newNode
            newNode = upNode;
            currentHeight++;
        }

    }

    public void show(){
        Node upright = head;
        int i = hight;
        while (upright != null) {
            Node crosswise = upright.right;
            System.out.printf("Total height[%d],currentHeight[%d] ", hight, i--);
            while (crosswise.value != null) {
                System.out.printf("-->%d",crosswise.value);
                crosswise = crosswise.right;
            }
            System.out.println("\n");
            upright = upright.down;
        }
    }

    public boolean contains(Integer element) {
        return find(element) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    private static class Node {
        private Integer value;

        private Node up, down, left, right;

        private byte type;

        public Node(Integer value) {
            this(value, DEFAULT_TYPE);
        }

        private Node(Integer value, byte type) {
            this.value = value;
            this.type = type;
        }
    }

    public static void main(String[] args) {
        SimpleSkipList simpleSkipList = new SimpleSkipList();
        for (int i = 0; i < 100; i++) {
            simpleSkipList.add(i);
        }
        simpleSkipList.show();
    }
}
