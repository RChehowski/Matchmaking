package eu.chehowski.quadtree;

import java.util.Collection;
import java.util.Collections;

/**
 *
 * @param <T>
 */
public final class QuadTreeEmptyNode<T extends QuadTreeItem<T>> implements QuadTreeNode<T>
{
    private static final QuadTreeEmptyNode<?> instance = new QuadTreeEmptyNode<>();

    private QuadTreeEmptyNode()
    {
    }

    @Override
    public boolean isLeaf()
    {
        // empty nodes are always leaves
        return true;
    }

    @Override
    public boolean isDummy()
    {
        return true;
    }

    @Override
    public QuadTreeNode<T> getChild(QuadTreeDirection direction)
    {
        return this;
    }

    @Override
    public QuadTreeNode<T> subdivide(QuadTreeDirection direction)
    {
        return null;
    }


    @SuppressWarnings("unchecked")
    public static <T extends QuadTreeItem<T>> QuadTreeNode<T> emptyNode()
    {
        return (QuadTreeNode<T>)instance;
    }
}
