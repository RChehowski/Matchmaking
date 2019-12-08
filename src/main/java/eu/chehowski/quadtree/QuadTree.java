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

    private final List<QuadTreeNode<?>> depthSearchStack;


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
        this.depthSearchStack = new ArrayList<>();
    }


    private QuadTreeNode<T> getRoot()
    {
        @SuppressWarnings("unchecked")
        final QuadTreeNode<T> previousRoot = (QuadTreeNode<T>)rootFU.get(this);

        if (previousRoot.isDummy())
        {
            final QuadTreeNode<T> newRoot = new QuadTreeLeafNode<>();

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

        if (x < halfW)
            return y < halfH ? QuadTreeDirection.SW : QuadTreeDirection.NW;
        else
            return y < halfH ? QuadTreeDirection.SE : QuadTreeDirection.NE;
    }

    private boolean takeItemsFromNode(final Queue<T> source, final QuadTreeNode<T> node, final int requiredAmount)
    {
        final Queue<T> drain = node.getItems();

        while (drain.size() < requiredAmount)
        {
            final T item = source.poll();

            if (item != null)
                drain.add(item);
            else
                return false;
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

        for (int i = 0; i < maxSubDiv; i++)
        {
            rangeCheck(item, x, y, w, h);

            final float halfW = w * .5f;
            final float halfH = h * .5f;

            final QuadTreeDirection direction = getQuadTreeDirection(item, x, y, halfW, halfH);
            currentNode = currentNode.subdivide(direction);

            // move
            if (direction.isE())
                x += halfW;

            if (direction.isN())
                y += halfH;

            w = halfW;
            h = halfH;
        }
    }

    public final Collection<T> take(final int maxNumItems)
    {
        @SuppressWarnings("unchecked")
        final QuadTreeNode<T> currentRoot = (QuadTreeNode<T>)rootFU.get(this);

        // Fast path (no elements in the tree)
        if (currentRoot.getItems().isEmpty())
            return Collections.emptyList();

        depthSearchStack.add(root);


        final List<T> items = new ArrayList<>(maxNumItems);
        return items;
    }
}
