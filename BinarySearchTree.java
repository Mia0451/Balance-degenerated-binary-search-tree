import java.util.Comparator;

public class BinarySearchTree<T extends Comparable<T>> {
    class TreeNode {
        T data;
        TreeNode left;
        TreeNode right;

        TreeNode(T data) {
            this.data = data;
        }
    }

    TreeNode root;

    boolean insert(T data) {
        if (root == null) {
            root = new TreeNode(data);
            return true;
        }

        TreeNode current = root;
        TreeNode parent = root;
        while (current != null) {
            int val = current.data.compareTo(data);
            if (val < 0) {
                parent = current;
                current = current.right;
            } else if (val == 0) {
                // If an existing node has the same value, do not insert.
                return false;
            } else {
                parent = current;
                current = current.left;
            }
        }

        TreeNode added = new TreeNode(data);
        if (parent.data.compareTo(data) < 0) {
            parent.right = added;
        } else {
            parent.left = added;
        }

        return true;
    }

    TreeNode search(T data) {
        TreeNode current = root;
        while (current != null) {
            int val = current.data.compareTo(data);
            if (val < 0) {
                current = current.right;
            } else if (val == 0) {
                return current;
            } else {
                current = current.left;
            }
        }
        return null;
    }

    void inOrder() {
        recursiveInOrder(root, 0);
    }

    private void recursiveInOrder(TreeNode current, int level) {
        if (current == null) {
            return;
        }

        recursiveInOrder(current.left, level + 1);

        for (int i = 0; i < level; ++i) {
            System.out.print("---|");
        }
        System.out.println(current.data);

        recursiveInOrder(current.right, level + 1);
    }

    void preOrder() {
        recursivePreOrder(root, 0);
    }

    private void recursivePreOrder(TreeNode current, int level) {
        if (current == null) {
            return;
        }

        for (int i = 0; i < level; ++i) {
            System.out.print("---|");
        }
        System.out.println(current.data);

        recursivePreOrder(current.left, level + 1);

        recursivePreOrder(current.right, level + 1);
    }

    void postOrder() {
        recursivePostOrder(root, 0);
    }

    private void recursivePostOrder(TreeNode current, int level) {
        if (current == null) {
            return;
        }

        recursivePostOrder(current.left, level + 1);

        recursivePostOrder(current.right, level + 1);

        for (int i = 0; i < level; ++i) {
            System.out.print("---|");
        }
        System.out.println(current.data);
    }

    void heightOrder() {
        Queue<TreeNode> queue = new Queue<>();
        if (root != null) {
            queue.Push(root);
        }

        int level = 0;
        while (!queue.IsEmpty()) {
            int elements = queue.Count();
            while(elements-- > 0) {
                for (int i = 0; i < level; ++i) {
                    System.out.print("---|");
                }
                TreeNode node = queue.Pop();
                System.out.println(node.data);

                if (node.left != null) {
                    queue.Push(node.left);
                }
                if (node.right != null) {
                    queue.Push(node.right);
                }
            }
            level++;
        }
    }


    // ============================================================
    // All functions defined below are for Lab 3 - Tree Balancing.
    // ============================================================

    /**
     * Balance this tree.
     */
    public void balanceThisTree() {
        if (root == null) {
            return; // empty tree, just return.
        }

        TreeNode dummy = new TreeNode(root.data);
        dummy.left = root; // It is also ok to do `dummy.right = root;`

        degenerateRotationInPlace(root, dummy, true);

        // After balanced, the root might have changed, so must reset the root node value.
        root = dummy.left;
    }

    /**
     * Balance binary search tree rooted in {@param cur}. {@param parent} is parent node of {@param
     * cur}, {@param isLeftChild} indicates whether {@param cur} is left child of {@param parent} or
     * not.
     *
     * The idea for balancing is keep balancing {@param cur} as long as tree rooted in {@param cur} is
     * not balanced; once {@param cur} is balanced, we continue call this function for left and right
     * child of {@param cur}.
     *
     * 1) suppose binary search tree starts with
     * <pre>
     *             7
     *            /
     *           6
     *          /
     *         5
     *        /
     *       4
     *      /
     *     3
     *    /
     *   2
     *  /
     * 1
     * </pre>
     * 2) root rotate to right
     * <pre>
     *           6
     *          / \
     *         5   7
     *        /
     *       4
     *      /
     *     3
     *    /
     *   2
     *  /
     * 1
     * </pre>
     * 3) the new root rotate to right
     * <pre>
     *         5
     *        / \
     *       4   6
     *      /     \
     *     3       7
     *    /
     *   2
     *  /
     * 1
     * </pre>
     * 4) the new root rotate to right
     * <pre>
     *       4
     *      / \
     *     3   5
     *    /     \
     *   2       6
     *  /         \
     * 1           7
     * </pre>
     * 4) left subtree rotate to right and right subtree rotate to left
     * <pre>
     *        4
     *      /   \
     *     2     6
     *    / \   / \
     *   1   3 5   7
     * </pre>
     */
    private void degenerateRotationInPlace(TreeNode cur, TreeNode parent, boolean isLeftChild) {
        int balance = checkBalanced(cur);
        while (balance <= -2 || balance >= 2) {
            if (balance <= -2) {
                cur = rotateToLeft(cur, parent, isLeftChild);
            } else if (balance >= 2) {
                cur = rotateToRight(cur, parent, isLeftChild);
            }

            balance = checkBalanced(cur);
        }

        // Now let's continue balance its left subtree
        if (cur.left != null) {
            degenerateRotationInPlace(cur.left, cur, true);
        }
        // Now let's continue balance its right subtree
        if (cur.right != null) {
            degenerateRotationInPlace(cur.right, cur, false);
        }
    }

    /**
     * Rotate to right based on {@param cur}.
     *
     * 1) Suppose {@param cur} is left child of {@param parent}:
     * <pre>
     *        parent
     *        /
     *      cur
     *     /  \
     *    Z   W
     *  /  \
     * X   Y
     * </pre>
     * After rotate to right, we will get
     * <pre>
     *        parent
     *        /
     *       Z
     *     /  \
     *    X   cur
     *       /  \
     *      Y   W
     * </pre>
     *
     * 2) Suppose {@param cur} is right child of {@param parent}:
     * <pre>
     *  parent
     *       \
     *      cur
     *     /  \
     *    Z   W
     *  /  \
     * X   Y
     * </pre>
     * After rotate to right, we will get
     * <pre>
     * parent
     *      \
     *       Z
     *     /  \
     *    X   cur
     *       /  \
     *      Y   W
     * </pre>
     *
     * @param cur
     * @param parent
     * @param isLeftChild
     * @return the new root, i.e. the new child of {@param parent} node.
     */
    private TreeNode rotateToRight(TreeNode cur, TreeNode parent, boolean isLeftChild) {
        if (isLeftChild) {
            parent.left = cur.left;
            cur.left = cur.left.right;
            parent.left.right = cur;
            return parent.left;
        } else {
            parent.right = cur.left;
            cur.left = cur.left.right;
            parent.right.right = cur;
            return parent.right;
        }
    }

    /**
     * Rotate to left based on {@param cur}.
     *
     * 1) Suppose {@param cur} is left child of {@param parent}:
     * <pre>
     *        parent
     *        /
     *      cur
     *     /  \
     *    Z   W
     *       /  \
     *      X   Y
     * </pre>
     * After rotate to left, we will get
     * <pre>
     *        parent
     *        /
     *       W
     *     /  \
     *    cur  Y
     *   /  \
     *  Z   X
     * </pre>
     *
     * 2) Suppose {@param cur} is right child of {@param parent}:
     * <pre>
     *  parent
     *       \
     *      cur
     *     /  \
     *    Z   W
     *       /  \
     *      X   Y
     * </pre>
     * After rotate to left, we will get
     * <pre>
     * parent
     *      \
     *       W
     *     /  \
     *   cur   Y
     *  /  \
     * Z   X
     * </pre>
     *
     * @param cur
     * @param parent
     * @param isLeftChild
     * @return the new root, i.e. the new child of {@param parent} node.
     */
    private TreeNode rotateToLeft(TreeNode cur, TreeNode parent, boolean isLeftChild) {
        if (isLeftChild) {
            parent.left = cur.right;
            cur.right = cur.right.left;
            parent.left.left = cur;
            return parent.left;
        } else {
            parent.right = cur.right;
            cur.right = cur.right.left;
            parent.right.left = cur;
            return parent.right;
        }
    }

    // return left_subtree_height - right_subtree_height
    private int checkBalanced(TreeNode cur) {
        if (cur == null) {
            return 0;
        }

        int leftSubtreeHeight = height(cur.left);
        int rightSubtreeHeight = height(cur.right);

        return leftSubtreeHeight - rightSubtreeHeight;
    }

    // Return the height of the tree rooted at {@param cur}.
    private int height(TreeNode cur) {
        if (cur == null) {
            return 0;
        }

        // Now use level order traversal to get the height of the tree.
        Queue<TreeNode> tool = new Queue<>();
        tool.Push(cur);
        int height = 0;
        while (!tool.IsEmpty()) {
            height++;
            int size = tool.Count();
            while (size-- > 0) {
                TreeNode tmp = tool.Pop();
                if (tmp.left != null) {
                    tool.Push(tmp.left);
                }
                if (tmp.right != null) {
                    tool.Push(tmp.right);
                }
            }
        }
        return height;
    }
}
