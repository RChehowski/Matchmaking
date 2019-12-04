package eu.chehowski.quadtree;

import java.util.Collection;
import java.util.Collections;

public final class QuadTreeEmptyNode<T> implements QuadTreeNode<T>
{
    private static final QuadTreeEmptyNode<?> instance = new QuadTreeEmptyNode<>();

    private QuadTreeEmptyNode()
    {

    }

    @Override
    public boolean isLeaf()
    {
        return true;
    }

    @Override
    public boolean isEmpty()
    {
        return true;
    }

    @Override
    public QuadTreeNode<T> getNW()
    {
        return this;
    }

    @Override
    public QuadTreeNode<T> getNE()
    {
        return this;
    }

    @Override
    public QuadTreeNode<T> getSW()
    {
        return this;
    }

    @Override
    public QuadTreeNode<T> getSE()
    {
        return this;
    }

    @Override
    public Collection<T> getItems()
    {
        return Collections.emptyList();
    }


    @SuppressWarnings("unchecked")
    public static <T> QuadTreeEmptyNode<T> emptyNode()
    {
        return (QuadTreeEmptyNode<T>)instance;
    }
}
