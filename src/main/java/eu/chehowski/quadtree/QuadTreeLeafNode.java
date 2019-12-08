package eu.chehowski.quadtree;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QuadTreeLeafNode<T extends QuadTreeItem<T>> extends AbstractQuadTreeNode<T>
{
    private final Queue<T> items = new ConcurrentLinkedQueue<>();


    @Override
    public boolean isLeaf()
    {
        return true;
    }

    @Override
    public QuadTreeNode<T> getChild(QuadTreeDirection direction)
    {
        return QuadTreeEmptyNode.emptyNode();
    }

    @Override
    public QuadTreeNode<T> subdivide(QuadTreeDirection direction)
    {
        return QuadTreeEmptyNode.emptyNode();
    }

    @Override
    public Queue<T> getItems()
    {
        return items;
    }
}
