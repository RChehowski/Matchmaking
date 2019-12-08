package eu.chehowski.quadtree;

public interface QuadTreeItem<T>
{
    float getX();
    float getY();

    /**
     * Determines whether this item's X coordinate is less than other's X coordinate.
     *
     * @param a first item
     * @param b second item
     * @return True if this.x < other.x
     */
    static <T extends QuadTreeItem<T>> boolean xLess(final T a, final T b)
    {
        return Float.compare(a.getX(), b.getX()) < 0f;
    }

    /**
     * Determines whether this item's Y coordinate is less than other's Y coordinate.
     *
     * @param a first item
     * @param b second item
     * @return True if this.y < other.y
     */
    static <T extends QuadTreeItem<T>> boolean yLess(final T a, final T b)
    {
        return Float.compare(a.getY(), b.getY()) < 0f;
    }
}
