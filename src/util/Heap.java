package util;

public class Heap<T extends Comparable<T>> {
    private Object[] values;
    private int size;

    public Heap(int maxSize) {
        values = new Object[maxSize];
    }

    public void insert(T value) { // todo, temp vars
        values[size] = value;
        int current = size++;
        while (true) {
            int parent = getParent(current);
            if (getValue(current).compareTo(getValue(parent)) >= 0)
                return;
            swap(current, parent);
            current = parent;
        }
    }

    public T removeSmallest() { // todo, temp vars
        T r = getValue(0);
        values[0] = values[--size];
        int current = 0;
        while (true) {
            int left = getLeft(current);
            int right = getRight(current);
            int smallestChild = getValue(left).compareTo(getValue(right)) < 0 ? left : right;
            if (getValue(current).compareTo(getValue(smallestChild)) <= 0)
                return r;
            swap(current, smallestChild);
            current = smallestChild;
        }
    }

    private void swap(int i, int j) {
        Object t = values[i];
        values[i] = values[j];
        values[j] = t;
    }

    private T getValue(int index) {
        return (T) values[index];
    }

    private int getLeft(int index) {
        return index * 2 + 1;
    }

    private int getRight(int index) {
        return index * 2 + 2;
    }

    private int getParent(int index) {
        return (index - 1) / 2;
    }
}