
public class Driver {
    public static void main(String[] args) {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        // Let's create a degenerated binary search tree.
        for (int i = 8; i >= 1; --i) {
            bst.insert(i);
        }

        System.out.println("Tree height order:");
        bst.heightOrder();
        System.out.println("Tree in order:");
        bst.inOrder();
        System.out.println("Tree pre order:");
        bst.preOrder();
        System.out.println("Tree post order:");
        bst.postOrder();


        /**
         * A degenerated binary search tree:
         * <pre>
         *               8
         *              /
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
         *
         * After balancing, it should become
         *
         * <pre>
         *          5
         *        /   \
         *       3     7
         *      / \   /  \
         *     2   4 6   8
         *    /
         *   1
         * </pre>
         */

        bst.balanceThisTree();

        System.out.println("===================== After balancing ================");
        System.out.println("Tree height order:");
        bst.heightOrder();
        System.out.println("Tree in order:");
        bst.inOrder();
        System.out.println("Tree pre order:");
        bst.preOrder();
        System.out.println("Tree post order:");
        bst.postOrder();
    }
}
