package eu.chehowski.quadtree;

import java.util.Collection;

public class QuadTreeLeafNode<T> implements QuadTreeNode<T>
{

    @Override
    public boolean isLeaf()
    {
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
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

    @Override
    public Collection<T> getItems()
    {
        return null;
    }
}
