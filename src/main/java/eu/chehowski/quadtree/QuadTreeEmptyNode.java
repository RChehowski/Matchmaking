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
    public QuadTreeNode<T> getNW()
    {
        return null;
    }

    @Override
    public QuadTreeNode<T> getNE()
    {
        return null;
    }

    @Override
    public QuadTreeNode<T> getSW()
    {
        return null;
    }

    @Override
    public QuadTreeNode<T> getSE()
    {
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T extends QuadTreeItem<T>> QuadTreeNode<T> emptyNode()
    {
        return (QuadTreeNode<T>)instance;
    }
}
