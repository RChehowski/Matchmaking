package eu.chehowski.quadtree;

import eu.chehowski.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Basic operations for all {@link QuadTreeNode} except for {@link QuadTreeEmptyNode}
 *
 * @param <T> Type of the item.
 */
public abstract class AbstractQuadTreeNode<T extends QuadTreeItem<T>> implements QuadTreeNode<T>
{
    /**
     * There are too many instances of the {@link AbstractQuadTreeNode} could exist in the program
     * simultaneously, so {@link java.util.concurrent.atomic.AtomicInteger} instance overhead will be too
     * significant.
     */
    @SuppressWarnings("rawtypes")
    private static final AtomicIntegerFieldUpdater<AbstractQuadTreeNode> numItemsFU =
            AtomicIntegerFieldUpdater.newUpdater(AbstractQuadTreeNode.class, "numItems");

    private volatile int numItems = 0;


    @Override
    public boolean isDummy()
    {
        return false;
    }

    // num items management
    @ThreadSafe
    public final void incrementNumItems()
    {
        numItemsFU.incrementAndGet(this);
    }

    @ThreadSafe
    public final void decrementNumItems()
    {
        numItemsFU.decrementAndGet(this);
    }

    @ThreadSafe
    public final int getNumItems()
    {
        return numItemsFU.get(this);
    }
}
