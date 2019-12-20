package com.sen.concurrency3.juc.collections.custom;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * @Auther: Sen
 * @Date: 2019/12/20 17:16
 * @Description: 无锁队列（基于原子类型实现）
 */
public class LockFreeQueue<E> {

    private static class Node<E> {
        E element;
        volatile Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }

    private AtomicReference<Node> head;

    private AtomicReference<Node> tail;

    private AtomicInteger size = new AtomicInteger();

    public LockFreeQueue() {
        Node<E> newNode = new Node<>(null);
        this.head = new AtomicReference<>(newNode);
        this.tail = new AtomicReference<>(newNode);
    }

    public void addLast(E element) {
        if (element == null) throw new NullPointerException("not allow add null");
        Node<E> newNode = new Node<>(element);
        //保证插入操作原子性的关键
        Node<E> pres = tail.getAndSet(newNode);
        pres.next = newNode;
        size.getAndIncrement();
    }

    public E removeFirst() {
        Node<E> headNode;
        Node<E> valueNode;
        do {
            headNode = head.get();
            valueNode = headNode.next;
            //保证remove线程安全的核心
        } while (valueNode != null && !head.compareAndSet(headNode, valueNode));
        //head是否与tail重合
        E value = (valueNode != null ? valueNode.element : null);
        if (valueNode != null) {
            valueNode.element = null;
        }
        return value;
    }

    public int size() {
        return size.get();
    }

    public static void main(String[] args) throws InterruptedException {
        LockFreeQueue<String> lockFreeQueue = new LockFreeQueue<>();
        HashMap<String, Long> data = new HashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.rangeClosed(1, 5).boxed().map(i -> (Runnable) () -> {
            int count = 11;
            for (int j = 0; j < count; j++) {
                lockFreeQueue.addLast(UUID.randomUUID().toString());
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).forEach(executorService::submit);

        IntStream.rangeClosed(1, 5).boxed().map(i -> (Runnable) () -> {
            int count = 11;
            while (count > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = lockFreeQueue.removeFirst();
                if (result == null) continue;
                System.out.println("result:" + result);
                data.put(result, System.currentTimeMillis());
                count--;
            }
        }).forEach(executorService::submit);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        System.out.println(lockFreeQueue.size);
        System.out.println("======================================");
        System.out.println(data.size());
    }
}
