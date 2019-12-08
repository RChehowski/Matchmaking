package eu.chehowski.quadtree;

/**
 * Makes navigation in the {@link QuadTree} a lot easier.
 */
public enum QuadTreeDirection
{
    NW,
    NE,
    SW,
    SE;

    public int getPriority()
    {
        // by default, the priority is the same as the current value's ordinal in the enum
        return ordinal();
    }

    public boolean isN()
    {
        return (this == NW) || (this == NE);
    }

    public boolean isS()
    {
        return (this == SW) || (this == SE);
    }

    public boolean isW()
    {
        return (this == NW) || (this == SW);
    }

    public boolean isE()
    {
        return (this == NE) || (this == SE);
    }
}
