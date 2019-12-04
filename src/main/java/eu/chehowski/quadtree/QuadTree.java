package eu.chehowski.quadtree;

public class QuadTree<T>
{
    private final float x;
    private final float y;

    private final float width;
    private final float height;

    private final int maxSubDivX;
    private final int maxSubDivY;


    private QuadTreeNode<T> root = QuadTreeEmptyNode.emptyNode();

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

        this.maxSubDivX = (int)(Math.log(Math.ceil(width)) / Math.log(2));
        this.maxSubDivY = (int)(Math.log(Math.ceil(height)) / Math.log(2));
    }
}
