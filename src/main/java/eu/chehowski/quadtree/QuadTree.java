package eu.chehowski.quadtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class QuadTree<T extends QuadTreeItem<T>>
{
    private static final int THRESH_TO_SUBDIVIDE = 10;


    private final float x;
    private final float y;

    private final float width;
    private final float height;

    private final int maxSubDiv;

    private volatile QuadTreeNode<T> root = QuadTreeEmptyNode.emptyNode();

    private static final AtomicReferenceFieldUpdater<QuadTree, QuadTreeNode> rootFU =
            AtomicReferenceFieldUpdater.newUpdater(QuadTree.class, QuadTreeNode.class, "root");



    public QuadTree(
            final float x,
            final float y,

            final float width,
            final float height
    )
    {
        this.x = x;
        this.y = y;

        this.width = Math.min(Float.MAX_VALUE - x, width);
        this.height = Math.min(Float.MAX_VALUE - y, width);

        final int maxSubDivX = (int) (Math.log(Math.ceil(width)) / Math.log(2));
        final int maxSubDivY = (int) (Math.log(Math.ceil(height)) / Math.log(2));

        this.maxSubDiv = Math.max(maxSubDivX, maxSubDivY);
    }


    private QuadTreeNode<T> getRoot()
    {
        @SuppressWarnings("unchecked")
        final QuadTreeNode<T> previousRoot = (QuadTreeNode<T>)rootFU.get(this);

        if (previousRoot.isDummy())
        {
            final QuadTreeNode<T> newRoot = new QuadTreeSubNode<>();

            // root is already updated if the check failed
            rootFU.compareAndSet(this, previousRoot, newRoot);
        }

        return root;
    }

    private void rangeCheck(T item, float x, float y, float w, float h)
    {
        final float itemX = item.getX();
        final float itemY = item.getY();

        if (itemX < x)
            throw new UnsupportedOperationException("Unable to insert a " + item + " it's [X] property " + itemX +
                    " is below the lower bound " + x);

        if (itemY < y)
            throw new UnsupportedOperationException("Unable to insert a " + item + " it's [Y] property " + itemX +
                    " is below the lower bound " + x);

        if (itemX > (x + w))
            throw new UnsupportedOperationException("Unable to insert a " + item + " it's [X] property " + itemX +
                    " is above the upper bound " + (x + w));

        if (itemY > (y + h))
            throw new UnsupportedOperationException("Unable to insert a " + item + " it's [Y] property " + itemX +
                    " is above the upper bound " + (y + h));
    }

    private QuadTreeDirection getQuadTreeDirection(T item, float x, float y, float halfW, float halfH)
    {
        final float itemX = item.getX();
        final float itemY = item.getY();

        if (itemX < (x + halfW))
        {
            return (itemY < (y + halfW)) ? QuadTreeDirection.SW : QuadTreeDirection.NW;
        }
        else
        {
            return (itemY < (y + halfW)) ? QuadTreeDirection.SE : QuadTreeDirection.NE;
        }

//        if (x < halfW)
//            return y < halfH ? QuadTreeDirection.SW : QuadTreeDirection.NW;
//        else
//            return y < halfH ? QuadTreeDirection.SE : QuadTreeDirection.NE;
    }

    private boolean takeItemsFromNode(final Queue<T> source, final Collection<T> drain, final int requiredAmount,
                                      final List<QuadTreeNode<T>> backTraceStack)
    {
        while (drain.size() < requiredAmount)
        {
            final T item = source.poll();

            if (item == null)
                return false;

            drain.add(item);

            for (QuadTreeNode<T> node : backTraceStack)
                node.decrementNumItems();
        }

        return drain.size() == requiredAmount;
    }

    public final void add(final T item)
    {
        float x = this.x;
        float y = this.y;
        float w = this.width;
        float h = this.height;

        QuadTreeNode<T> currentNode = getRoot();
        currentNode.incrementNumItems();

        for (int i = 0; i < maxSubDiv; i++)
        {
            rangeCheck(item, x, y, w, h);

            final float halfW = w * .5f;
            final float halfH = h * .5f;

            final QuadTreeDirection direction = getQuadTreeDirection(item, x, y, halfW, halfH);
            currentNode = currentNode.subdivide(direction);
            currentNode.incrementNumItems();

            // move
            if (direction.isE())
                x += halfW;

            if (direction.isN())
                y += halfH;

            w = halfW;
            h = halfH;
        }

        final QuadTreeDirection direction = getQuadTreeDirection(item, x, y, w * .5f, h * .5f);
        final QuadTreeNode<T> leafNode = currentNode.getLeaf(direction);
        leafNode.incrementNumItems();
        leafNode.getItems().add(item);
    }

    private QuadTreeNode<T> getMaxNumItemsNode(final QuadTreeNode<T> node)
    {
        final QuadTreeNode<T> nw = node.getChild(QuadTreeDirection.NW);
        final QuadTreeNode<T> ne = node.getChild(QuadTreeDirection.NE);
        final QuadTreeNode<T> sw = node.getChild(QuadTreeDirection.SW);
        final QuadTreeNode<T> se = node.getChild(QuadTreeDirection.SE);

        final QuadTreeNode<T> max1 = (nw.getNumItems() > ne.getNumItems()) ? nw : ne;
        final QuadTreeNode<T> max2 = (sw.getNumItems() > se.getNumItems()) ? sw : se;

        return (max1.getNumItems() > max2.getNumItems()) ? max1 : max2;
    }

    public final Collection<T> take(final int maxNumItems)
    {
        @SuppressWarnings("unchecked")
        final QuadTreeNode<T> currentRoot = (QuadTreeNode<T>)rootFU.get(this);

        if (currentRoot.getNumItems() == 0)
            return Collections.emptyList();

        final List<QuadTreeNode<T>> backTraceStack = new ArrayList<>(maxSubDiv);
        backTraceStack.add(currentRoot);


        final List<T> items = new ArrayList<>(maxNumItems);

        QuadTreeNode<T> currentNode = currentRoot;
        while (!backTraceStack.isEmpty())
        {
            while (!(currentNode.isLeaf()))
            {
                currentNode = getMaxNumItemsNode(currentNode);
                backTraceStack.add(currentNode);
            }

            if (takeItemsFromNode(currentNode.getItems(), items, maxNumItems, backTraceStack))
            {
                // Search complete
                return items;
            }

            while(!backTraceStack.isEmpty() &&
                    (currentNode = backTraceStack.remove(backTraceStack.size() - 1)).getNumItems() == 0)
            {
            }
        }

        return items;
    }
}
