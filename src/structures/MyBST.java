package structures;

public class MyBST<T extends Comparable<T>> implements BST<T> {
    private MyTreeNode<T> root;

    public T comparePoints(T p1, T p2) {
        return comparePoints(p1, p2, root);
    }

    private T comparePoints(T p1, T p2, MyTreeNode<T> node) {
        if (node == null) return null;
        if (p1.compareTo(node.data) < 0 && p2.compareTo(node.data) < 0)
            return comparePoints(p1, p2, node.leftChild);
        if (p1.compareTo(node.data) > 0 && p2.compareTo(node.data) > 0)
            return comparePoints(p1, p2, node.rightChild);
        return node.data;
    }

    @Override
    public void insert(T x) {
        if (root == null) {
            root = new MyTreeNode<>(x);
            return; // We've inserted now, so exit
        }

        // run through nodes, add at correct location
        insert(x, root);
    }

    private void insert(T x, MyTreeNode<T> node) {
        if (x.compareTo(node.data) < 0) { // less than: go left subtree
            if (node.leftChild == null) {
                node.leftChild = new MyTreeNode<>(x);
                node.leftChild.parent = node;
            } else {
                insert(x, node.leftChild);
            }
        } else if (x.compareTo(node.data) > 0) { // greater than: go right subtree
            if (node.rightChild == null) {
                node.rightChild = new MyTreeNode<>(x);
                node.rightChild.parent = node;
            } else {
                insert(x, node.rightChild);
            }
        }
    }

    @Override
    public void delete(T x) {
        if (!lookup(x)) return; // bail if item doesn't exist

        // Get the correct node
        MyTreeNode<T> node = root;
        while (x.compareTo(node.data) != 0) {
            if (x.compareTo(node.data) < 0)
                node = node.leftChild;
            else if (x.compareTo(node.data) > 0)
                node = node.rightChild;
        }

        delete(x, node);
    }

    private void delete(T x, MyTreeNode<T> node) {
        // if leaf node
        if (node.leftChild == null && node.rightChild == null) {
            MyTreeNode<T> parent = node.parent;
            if (node.parent == null) { // if we're on the root node
                root = null; // reset root
                return;
            }

            if (parent.leftChild != null
                    && parent.leftChild.data.compareTo(x) == 0) {
                parent.leftChild = null;
            } else if (parent.rightChild != null
                    && parent.rightChild.data.compareTo(x) == 0) {
                parent.rightChild = null;
            }
            return;
        }

        // if only has left child
        if (node.leftChild != null && node.rightChild == null) {
            MyTreeNode<T> parent = node.parent;
            if (node.parent == null) { // if we're on the root node
                root = node.leftChild; // reset root
                root.parent = null;
                return;
            }

            if (parent.leftChild != null
                    && parent.leftChild.data.compareTo(x) == 0) {
                parent.leftChild = node.leftChild;
            } else if (parent.rightChild != null
                    && parent.rightChild.data.compareTo(x) == 0) {
                parent.rightChild = node.leftChild;
            }
            return;
        }

        // if only has right child
        if (node.leftChild == null && node.rightChild != null) {
            MyTreeNode<T> parent = node.parent;
            if (node.parent == null) { // if we're on the root node
                root = node.rightChild; // reset root
                root.parent = null;
                return;
            }

            if (parent.leftChild != null
                    && parent.leftChild.data.compareTo(x) == 0) {
                parent.leftChild = node.rightChild;
            } else if (parent.rightChild != null
                    && parent.rightChild.data.compareTo(x) == 0) {
                parent.rightChild = node.rightChild;
            }
            return;
        }

        // if has both children

        // Get leftmost on the right side
        MyTreeNode<T> otherNode = node.rightChild;
        while (otherNode.leftChild != null) {
            otherNode = otherNode.leftChild;
        }

        node.data = otherNode.data; // swap
        delete(otherNode.data, otherNode); // do it again to delete other node
    }

    @Override
    public boolean lookup(T x) {
        if (root == null)
            return false;
        return lookup(x, root);
    }

    private boolean lookup(T x, MyTreeNode<T> node) {
        if (x.compareTo(node.data) == 0)
            return true;

        if (x.compareTo(node.data) < 0) {
            if (node.leftChild == null)
                return false;
            return lookup(x, node.leftChild);
        }
        if (x.compareTo(node.data) > 0) {
            if (node.rightChild == null)
                return false;
            return lookup(x, node.rightChild);
        }
        return false;
    }

    public int getDepth() {
        return depth(root, 0);
    }

    private int depth(MyTreeNode<T> node, int depth) {
        if (node == null) return 0;
        if (node.leftChild == null && node.rightChild == null) return depth;
        return depth(node.leftChild, depth + 1) + depth(node.rightChild, depth + 1);
    }

    public int numExternalNodes() {
        return countExternal(root);
    }

    private int countExternal(MyTreeNode<T> node) {
        if (node == null) return 0;
        if (node.leftChild == null && node.rightChild == null) return 1;
        return countExternal(node.leftChild) + countExternal(node.rightChild);
    }

    public String strDataPreOrder() { return print(-1); }
    public String strDataInOrder() { return print(0); }
    public String strDataPostOrder() { return print(1); }

    private String print(int direction) {
        String str = root.print(direction);
        return "BST [ " + str.substring(0, str.length() - 1) + " ]";
    }
}
