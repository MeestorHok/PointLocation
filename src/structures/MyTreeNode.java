package structures;

public class MyTreeNode<T extends Comparable<T>> {
    public T data;
    public MyTreeNode(T x) { data = x; }
    public MyTreeNode<T> leftChild;
    public MyTreeNode<T> rightChild;
    public MyTreeNode<T> parent;

    public String print(int direction) {
        String str = "";
        // if Pre-Order
        if (direction < 0)
            str += data.toString() + ",";

        // Left subtree
        if (leftChild != null)
            str += leftChild.print(direction);

        // if In-Order
        if (direction == 0)
            str += data.toString() + ",";

        // Right subtree
        if (rightChild != null)
            str += rightChild.print(direction);

        // if Post-Order
        if (direction > 0)
            str += data.toString() + ",";

        return str;
    }
}
