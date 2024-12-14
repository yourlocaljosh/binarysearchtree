public class BinarySearchTree<T extends Comparable<T>> {
  
    private Node root;

    private static class Node {
        T datum;
        Node left;
        Node right;

        Node(T datum, Node left, Node right) {
            this.datum = datum;
            this.left = left;
            this.right = right;
        }
    }

    public BinarySearchTree() {
        root = null;
    }

    public BinarySearchTree(BinarySearchTree<T> other) {
        root = copyNodes(other.root);
    }

    public BinarySearchTree<T> clone() throws CloneNotSupportedException {
        BinarySearchTree<T> clone = (BinarySearchTree<T>) super.clone();
        clone.root = copyNodes(this.root);
        return clone;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    public int height() {
        return height(root);
    }

    public void inorderTraversal(TraversalAction<T> action) {
        inorderTraversal(root, action);
    }

    public void preorderTraversal(TraversalAction<T> action) {
        preorderTraversal(root, action);
    }

    public boolean checkSortingInvariant() {
        return checkSortingInvariant(root, null, null);
    }

    public T findMinElement() {
        Node minNode = findMin(root);
        return minNode == null ? null : minNode.datum;
    }

    public T findMaxElement() {
        Node maxNode = findMax(root);
        return maxNode == null ? null : maxNode.datum;
    }

    public T findGreaterThan(T value) {
        Node greaterNode = findGreaterThan(root, value);
        return greaterNode == null ? null : greaterNode.datum;
    }

    public void insert(T item) {
        root = insert(root, item);
    }

    private Node copyNodes(Node node) {
        if (node == null) {
            return null;
        }
        return new Node(node.datum, copyNodes(node.left), copyNodes(node.right));
    }

    private int size(Node node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    private int height(Node node) {
        return node == null ? 0 : 1 + Math.max(height(node.left), height(node.right));
    }

    private void inorderTraversal(Node node, TraversalAction<T> action) {
        if (node != null) {
            inorderTraversal(node.left, action);
            action.perform(node.datum);
            inorderTraversal(node.right, action);
        }
    }

    private void preorderTraversal(Node node, TraversalAction<T> action) {
        if (node != null) {
            action.perform(node.datum);
            preorderTraversal(node.left, action);
            preorderTraversal(node.right, action);
        }
    }

    private boolean checkSortingInvariant(Node node, T min, T max) {
        if (node == null) {
            return true;
        }
        if ((min != null && node.datum.compareTo(min) <= 0) || (max != null && node.datum.compareTo(max) >= 0)) {
            return false;
        }
        return checkSortingInvariant(node.left, min, node.datum) && checkSortingInvariant(node.right, node.datum, max);
    }

    private Node findMin(Node node) {
        return node.left == null ? node : findMin(node.left);
    }

    private Node findMax(Node node) {
        return node.right == null ? node : findMax(node.right);
    }

    private Node findGreaterThan(Node node, T value) {
        if (node == null) {
            return null;
        }
        if (value.compareTo(node.datum) < 0) {
            Node left = findGreaterThan(node.left, value);
            return left != null ? left : node;
        } else {
            return findGreaterThan(node.right, value);
        }
    }

    private Node insert(Node node, T item) {
        if (node == null) {
            return new Node(item, null, null);
        }
        if (item.compareTo(node.datum) < 0) {
            node.left = insert(node.left, item);
        } else if (item.compareTo(node.datum) > 0) {
            node.right = insert(node.right, item);
        }
        return node;
    }

    @FunctionalInterface
    public interface TraversalAction<T> {
        void perform(T item);
    }
}
