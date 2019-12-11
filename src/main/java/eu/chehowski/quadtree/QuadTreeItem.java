package eu.chehowski.quadtree;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * An interface for each item that could be placed in the quad tree.
 */
public interface QuadTreeItem
{
    float getX();

    float getY();
}
