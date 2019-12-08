package eu.chehowski.quadtree;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public final class QuadTreeSubNode<T extends QuadTreeItem<T>> extends AbstractQuadTreeNode<T>
{
    private volatile QuadTreeNode<T> nw = QuadTreeEmptyNode.emptyNode();
    private volatile QuadTreeNode<T> ne = QuadTreeEmptyNode.emptyNode();
    private volatile QuadTreeNode<T> sw = QuadTreeEmptyNode.emptyNode();
    private volatile QuadTreeNode<T> se = QuadTreeEmptyNode.emptyNode();

    @SuppressWarnings("rawtypes")
    private static final AtomicReferenceFieldUpdater<QuadTreeSubNode, QuadTreeNode> nwFU =
            AtomicReferenceFieldUpdater.newUpdater(QuadTreeSubNode.class, QuadTreeNode.class, "nw");

    @SuppressWarnings("rawtypes")
    private static final AtomicReferenceFieldUpdater<QuadTreeSubNode, QuadTreeNode> neFU =
            AtomicReferenceFieldUpdater.newUpdater(QuadTreeSubNode.class, QuadTreeNode.class, "ne");

    @SuppressWarnings("rawtypes")
    private static final AtomicReferenceFieldUpdater<QuadTreeSubNode, QuadTreeNode> swFU =
            AtomicReferenceFieldUpdater.newUpdater(QuadTreeSubNode.class, QuadTreeNode.class, "sw");

    @SuppressWarnings("rawtypes")
    private static final AtomicReferenceFieldUpdater<QuadTreeSubNode, QuadTreeNode> seFU =
            AtomicReferenceFieldUpdater.newUpdater(QuadTreeSubNode.class, QuadTreeNode.class, "se");

    public QuadTreeSubNode()
    {
    }

    @Override
    public boolean isLeaf()
    {
        return false;
    }

    @Override
    public QuadTreeNode<T> getNW()
    {
        return nw;
    }

    @Override
    public QuadTreeNode<T> getNE()
    {
        return ne;
    }

    @Override
    public QuadTreeNode<T> getSW()
    {
        return sw;
    }

    @Override
    public QuadTreeNode<T> getSE()
    {
        return se;
    }


    @SuppressWarnings("unchecked")
    @Override
    public QuadTreeNode<T> getChild(QuadTreeDirection direction)
    {
        switch (direction)
        {
            case NW: return (QuadTreeNode<T>)nwFU.get(this);
            case NE: return (QuadTreeNode<T>)neFU.get(this);
            case SW: return (QuadTreeNode<T>)swFU.get(this);
            case SE: return (QuadTreeNode<T>)seFU.get(this);
        }

        throw new IllegalStateException("Unsupported direction: " + direction);
    }

    @Override
    public final QuadTreeNode<T> subdivide(QuadTreeDirection direction)
    {
        // Check if already subdivided
        QuadTreeNode<T> child = getChild(direction);

        while (!child.isLeaf())
        {
            final QuadTreeSubNode<T> subNode = new QuadTreeSubNode<>();
            switch (direction)
            {
                case NW: nwFU.weakCompareAndSet(this, child, subNode); break;
                case NE: neFU.weakCompareAndSet(this, child, subNode); break;
                case SW: swFU.weakCompareAndSet(this, child, subNode); break;
                case SE: seFU.weakCompareAndSet(this, child, subNode); break;
            }
            child = getChild(direction);
        }

        return child;
    }
}
