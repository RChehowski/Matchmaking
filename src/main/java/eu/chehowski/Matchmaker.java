package eu.chehowski;

import eu.chehowski.annotations.ThreadSafe;
import eu.chehowski.model.GroupInfo;
import eu.chehowski.model.PlayerInfo;
import eu.chehowski.quadtree.QuadTree;

public final class Matchmaker
{
    private static volatile Matchmaker instance;

    private final QuadTree<PlayerInfo> quadTree;

    public Matchmaker(
            final float minLatency,
            final float minSkill,
            final float maxLatency,
            final float maxSkill)
    {
        final float latencyRange = maxLatency - minLatency;
        final float skillRange = maxSkill - minSkill;

        quadTree = new QuadTree<>(minLatency, minSkill, latencyRange, skillRange);
    }


    /**
     * Add new player info to the matchmaker.
     * @param playerInfo player info to be added.
     */
    @ThreadSafe
    public void addPlayerInfo(final PlayerInfo playerInfo)
    {
        quadTree.add(playerInfo);
    }

    @ThreadSafe
    public GroupInfo performMatchmaking(final int numPlayers)
    {
        return null;
    }


    /**
     * Retrieve the instance of the matchmaker.
     *
     * @implNote sure, in real life the matchmaker will be a part of an other system,
     *  but for the sake of the demonstration, I made it a 'root', so it's globally
     *  reachable from the entire module.
     * @return The lazily instantiated matchmaker.
     */
    public static Matchmaker getInstance()
    {
        Matchmaker localInstance = instance;
        if (localInstance == null)
        {
            synchronized (Matchmaker.class)
            {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new Matchmaker();
            }
        }
        return localInstance;
    }
}
