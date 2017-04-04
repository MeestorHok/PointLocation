package structures;

public interface BST<T extends Comparable<T>> {
    void insert(T x);
    void delete(T x);
    boolean lookup(T x);
}
