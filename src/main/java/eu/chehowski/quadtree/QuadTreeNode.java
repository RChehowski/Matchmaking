package eu.chehowski.quadtree;

import eu.chehowski.annotations.ThreadSafe;
import eu.chehowski.util.EmptyQueue;

import java.util.Queue;

/**
 * Cell arrangement:
 * | NW | NE |
 * | SW | SE |
 *
 * @param <T>
 */
public interface QuadTreeNode<T extends QuadTreeItem>
{

    /**
     * Checks whether the current node is a leaf node.
     * - Leaf nodes has items, so {@link #getItems()} has items in it
     * - Leaf nodes can not subdivide.
     *
     * @return True for leaf, non-subdivide nodes.
     */
    boolean isLeaf();

    boolean isDummy();


    QuadTreeNode<T> getChild(final QuadTreeDirection direction);

    QuadTreeNode<T> subdivide(final QuadTreeDirection direction);

    QuadTreeNode<T> getLeaf(final QuadTreeDirection direction);

    /**
     * Returns the collection of items in this quad tree node. Always returns an empty collection for non-leaf
     * nodes.
     *
     * @return A collection of items.
     */
    default Queue<T> getItems()
    {
        return EmptyQueue.emptyQueue();
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
