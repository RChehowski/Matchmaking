package eu.chehowski.quadtree;

import eu.chehowski.annotations.ThreadSafe;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

/**
 * Cell arrangement:
 * | NW | NE |
 * | SW | SE |
 *
 * @param <T>
 */
public interface QuadTreeNode<T extends QuadTreeItem<T>>
{

    /**
     * Checks whether the current node is a leaf node.
     * - Leaf nodes has items, so {@link #getItems()} has items in it
     * - Leaf nodes can not subdivide, {@link #getNW()}, {@link #getNE()}, {@link #getSW()}, {@link #getSE()}
     *      so they return null.
     *
     * @return True for leaf, non-subdivide nodes.
     */
    boolean isLeaf();

    boolean isDummy();


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


    QuadTreeNode<T> getChild(final QuadTreeDirection direction);

    QuadTreeNode<T> subdivide(final QuadTreeDirection direction);

    /**
     * Returns the collection of items in this quad tree node. Always returns an empty collection for non-leaf
     * nodes.
     *
     * @return A collection of items.
     */
    default Collection<T> getItems()
    {
        return Collections.emptyList();
    }


    // num items management
    @ThreadSafe
    default void incrementNumItems()
    {
    }

    @ThreadSafe
    default void decrementNumItems()
    {
    }

    @ThreadSafe
    default int getNumItems()
    {
        return 0;
    }
}
