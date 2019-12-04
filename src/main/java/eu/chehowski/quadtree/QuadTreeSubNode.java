package eu.chehowski.quadtree;

import java.util.Collection;

public final class QuadTreeSubNode<T> implements QuadTreeNode<T>
{
    private QuadTreeNode nw;
    private QuadTreeNode ne;
    private QuadTreeNode sw;
    private QuadTreeNode se;

    public QuadTreeSubNode()
    {
        nw = QuadTreeEmptyNode.emptyNode();
        ne = QuadTreeEmptyNode.emptyNode();
        sw = QuadTreeEmptyNode.emptyNode();
        se = QuadTreeEmptyNode.emptyNode();
    }

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
