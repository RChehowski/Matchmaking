package eu.chehowski.quadtree;

import java.util.Collection;

public interface QuadTreeNode<T>
{
    /**
     * Checks whether the current node is a leaf node.
     *
     * @return
     */
    boolean isLeaf();

    boolean isEmpty();

    /**
     * Get the NORTH-WEST or an UPPER-LEFT sub-node.
     * Always returns null for leaf nodes, see {@link #isLeaf()}.
     *
     * @return The north-west sub-node.
     */
    QuadTreeNode<T> getNW();

    /**
     * Get the NORTH-EAST or an UPPER-RIGHT sub-node.
     * Always returns null for leaf nodes, see {@link #isLeaf()}.
     *
     * @return The north-east sub-node.
     */
    QuadTreeNode<T> getNE();

    /**
     * Get the SOUTH-WEST or a LOWER-LEFT sub-node.
     *
     * @return The south-west sub-node.
     */
    QuadTreeNode<T> getSW();

    /**
     * Get the SOUTH-EAST or a LOWER-RIGHT sub-node.
     *
     * @return The south-east sub-node.
     */
    QuadTreeNode<T> getSE();

    /**
     * Returns the collection of items in this quad tree node. Always returns an empty collection for non-leaf
     * nodes.
     *
     * @return A collection of items.
     */
    Collection<T> getItems();
}
