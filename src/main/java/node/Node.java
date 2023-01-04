package node;

public class Node<T> {
    public T value;
    public Node<T> left;
    public Node<T> right;

    private boolean isNodeExist(Node<T> node) {
        return node != null && node.value != null;
    }

    private void createNode(Node<T> node, T value) {
        node.left = new Node<>();
        node.right = new Node<>();
        node.value = value;
    }

    private void insert(Node<T> node, T value) {
        if (value instanceof Comparable) {
            if (!isNodeExist(node)) {
                createNode(node, value);
            } else if ((comparator(value, node.value)) < 0) {
                insert(node.left, value);
            } else insert(node.right, value);
        }
    }

    private int comparator(T value1, T value2) {
        if (!(value1 instanceof Comparable<?>)) throw new ClassCastException();
        return ((Comparable<T>) value1).compareTo(value2);
    }

    private Node<T> search(Node<T> node, T value) {
        if (!isNodeExist(node)) return null;
        if (comparator(value, node.value) == 0) return node;
        if ((comparator(value, node.value)) < 0) {
            return search(node.left, value);
        } else
            return search(node.right, value);
    }

    private Node<T> getMin(Node<T> node) {
        if (!isNodeExist(node)) {
            return null;
        }
        if (!isNodeExist(node.left)) {
            return node;
        }
        return getMin(node.left);
    }

    private Node<T> getMax(Node<T> node) {
        if (!isNodeExist(node)) {
            return null;
        }
        if (!isNodeExist(node.right)) {
            return node;
        }
        return getMax(node.right);
    }

    private void inOrderTraversal(Node<T> node) {
        if (!isNodeExist(node)) {
            return;
        }
        inOrderTraversal(node.left);
        System.out.println("[ " + node.value + " ]" + getChildrenCount(node));
        inOrderTraversal(node.right);
    }

    private void postOrderTraversal(Node<T> node) {
        if (!isNodeExist(node)) {
            return;
        }
        inOrderTraversal(node.left);
        inOrderTraversal(node.right);
        System.out.println("[ " + node.value + " ]");
    }

    private void directOrderTraversal(Node<T> node) {
        if (!isNodeExist(node)) {
            return;
        }
        System.out.println("[ " + node.value + " ]");
        inOrderTraversal(node.left);
        inOrderTraversal(node.right);
    }

    private void moveNode(Node<T> toNode, Node<T> fromNode) {
        if (!isNodeExist(toNode) && !isNodeExist(fromNode)) return;
        if (isNodeExist(toNode)) {
            toNode.value = (isNodeExist(fromNode)) ? fromNode.value : null;
        }
    }

    private int getChildrenCount(Node<T> node) {
        return ((node.left.value == null) ? 0 : 1) + ((node.right.value == null) ? 0 : 1);
    }

    private Node<T> getChildOrNull(Node<T> node) {
        return (isNodeExist(node.left)) ? node.left : (isNodeExist(node.right)) ? node.right : null;
    }

    private void removeNodeWithOneOrZeroChild(Node<T> nodeToDelete) {
        Node<T> childOrNull = getChildOrNull(nodeToDelete);
        moveNode(nodeToDelete, childOrNull);
        nodeToDelete.left = (childOrNull != null) ? childOrNull.left : null;
        nodeToDelete.right = (childOrNull != null) ? childOrNull.right : null;
    }

    //todo
    private boolean remove(Node<T> root, T value) {
        Node<T> node = search(root, value);
        boolean isRemove;
        if (isNodeExist(node)) {
            if (getChildrenCount(node) < 2) {
                removeNodeWithOneOrZeroChild(node);
            } else {
                moveNode(node, getMin(node.right));
                removeNodeWithOneOrZeroChild(getMin(node.right));
            }
            isRemove = true;
        } else isRemove = false;
        return isRemove;
    }

    public static void main(String[] args) {

        Integer[] digit = {9, 2, 5, 13, 6, 10, 14, 7, 33, 44, 3, 4, 22, 19, 25, 18, 20};
        Node<Integer> node = new Node<>();
        node.createNode(node, 9);
        for (int i = 1; i < digit.length; i++) {
            node.insert(node, digit[i]);
        }
        node.inOrderTraversal(node);
        node.postOrderTraversal(node);
        node.directOrderTraversal(node);

        boolean isRemove = node.remove(node, 10);
        System.out.println("Removing is " + isRemove);
        node.inOrderTraversal(node);
    }
}
