package dbarsukova.queue;

import java.util.LinkedList;
import java.util.Queue;


/**
 * MyQueue class is generic class represents thread-safe queue with capacity.
 * it provides methods to add and remove elements from queue,
 * check if queue is empty or full.
 */

public class MyQueue<T> {
    private final Queue<T> queue;
    private final int capacity;
    private final Object EMPTY = new Object();
    private final Object FULL = new Object();

    /**
     * constructs new MyQueue with specified capacity.
     *
     * @param capacity maximum number of elements queue can hold.
     */
    public MyQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    /**
     * checks if queue is empty.
     *
     * @return true if queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        synchronized (this.queue) {
            return this.queue.isEmpty();
        }
    }

    /**
     * checks if queue is full.
     *
     * @return true if queue is full, false otherwise.
     */
    public boolean isFull() {
        synchronized (this.queue) {
            return this.queue.size() >= this.capacity;
        }
    }

    /**
     * causes current thread to wait until queue becomes empty.
     *
     * @throws InterruptedException if thread is interrupted while waiting.
     */
    public void waitOnEmpty() throws InterruptedException {
        synchronized (this.EMPTY) {
            this.EMPTY.wait();
        }
    }

    /**
     * causes current thread to wait until queue becomes full.
     *
     * @throws InterruptedException if thread is interrupted while waiting.
     */
    public void waitOnFull() throws InterruptedException {
        synchronized (this.FULL) {
            this.FULL.wait();
        }
    }

    /**
     * notifies all waiting threads that queue is empty.
     */
    public void notifyForEmpty() {
        synchronized (this.EMPTY) {
            this.EMPTY.notifyAll();
        }
    }

    /**
     * notifies all waiting threads that queue is full.
     */
    public void notifyForFull() {
        synchronized (this.FULL) {
            this.FULL.notifyAll();
        }
    }

    /**
     * adds element to queue.
     *
     * @param data element to be added to queue.
     */
    public void add(T data) {
        synchronized (this.queue) {
            this.queue.add(data);
        }
    }

    /**
     * removes and returns element at front of queue.
     *
     * @return element removed from queue.
     */
    public T remove() {
        synchronized (this.queue) {
            return this.queue.poll();
        }
    }
}
