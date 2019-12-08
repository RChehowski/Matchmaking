package eu.chehowski;

import eu.chehowski.annotations.ThreadSafe;
import eu.chehowski.model.GroupInfo;
import eu.chehowski.model.PlayerInfo;
import eu.chehowski.quadtree.QuadTree;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public final class Matchmaker
{
    private static final float MIN_LATENCY = 0f;
    private static final float MIN_SKILL = 0f;
    private static final float MAX_LATENCY = Float.MAX_VALUE;
    private static final float MAX_SKILL = Float.MAX_VALUE;

    private static volatile Matchmaker instance;

    private final QuadTree<PlayerInfo> quadTree;

    /**
     * Indicates the amount of groups already combined.
     */
    private final AtomicLong currentGroupIndex = new AtomicLong(0L);

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
     *
     * @param playerInfo player info to be added.
     */
    @ThreadSafe
    public void addPlayerInfo(final PlayerInfo playerInfo)
    {
        quadTree.add(playerInfo);
    }

    /**
     * Takes n players for the matchmaking from the most loaded part of space.
     *
     * @param numPlayers Number of players to be removed from the quad tree.
     * @return A newly-constructed group of players.
     */
    @ThreadSafe
    public Optional<GroupInfo> performMatchmaking(final int numPlayers)
    {
        final Collection<PlayerInfo> players = quadTree.take(numPlayers);
        if (!players.isEmpty())
        {
            final GroupInfo groupInfo = new GroupInfo(currentGroupIndex.getAndIncrement(), players);
            return Optional.of(groupInfo);
        }

        return Optional.empty();
    }

    /**
     * Retrieve the singleton instance of the matchmaker.
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
                    instance = localInstance = new Matchmaker(MIN_LATENCY, MIN_SKILL, MAX_LATENCY, MAX_SKILL);
            }
        }
        return localInstance;
    }
}
