package eu.chehowski.quadtree;

import java.util.Collection;

public class QuadTreeLeafNode<T extends QuadTreeItem<T>> extends AbstractQuadTreeNode<T>
{
    @Override
    public boolean isLeaf()
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


    @Override
    public Collection<T> getItems()
    {
        return null;
    }
}
