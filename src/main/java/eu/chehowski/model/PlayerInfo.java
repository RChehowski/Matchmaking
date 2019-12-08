package eu.chehowski.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import eu.chehowski.quadtree.QuadTreeItem;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public final class PlayerInfo implements QuadTreeItem<PlayerInfo>
{
    private final String name;
    private final float latency;
    private final float skill;

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

    @Override
    public float getX()
    {
        return latency;
    }

    @Override
    public float getY()
    {
        return skill;
    }
}