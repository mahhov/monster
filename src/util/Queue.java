package util;

public class Queue<T> {
    private Object[] values;
    private int last, current;

    public Queue(int maxSize) {
        values = new Object[maxSize];
    }

    public void insert(T value) {
        values[last++] = value;
    }

    public T remove() {
        return (T) values[current++];
    }

    public boolean isEmpty() {
        return last == current;
    }
}