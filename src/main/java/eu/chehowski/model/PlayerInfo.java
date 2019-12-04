package eu.chehowski.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public final class PlayerInfo
{
    private final String name;
    private final float latency;
    private final float skill;

    private volatile int collectionCyclesField = 0;
    private static final AtomicIntegerFieldUpdater<PlayerInfo> collectionCycles =
            AtomicIntegerFieldUpdater.newUpdater(PlayerInfo.class, "collectionCyclesField");

//    private static final

    public PlayerInfo(final String name, final float latency, final float skill)
    {
        this.name = name;
        this.latency = latency;
        this.skill = skill;


    }

    @JsonGetter("name")
    public final String getName()
    {
        return name;
    }

    @JsonGetter("latency")
    public final float getLatency()
    {
        return latency;
    }

    @JsonGetter("skill")
    public final float getSkill()
    {
        return skill;
    }

    @Override
    public final String toString()
    {
        return "{" +
            "name=\"" + name + "\", " +
            "latency=\"" + latency + "\", " +
            "skill=\"" + skill + "\"" +
        '}';
    }


    final void markSurvivedOnCollectionCycle()
    {
        collectionCycles.incrementAndGet(this);
    }
}