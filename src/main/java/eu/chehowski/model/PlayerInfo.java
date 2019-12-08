package eu.chehowski.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import eu.chehowski.quadtree.QuadTreeItem;

import java.time.Instant;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public final class PlayerInfo implements QuadTreeItem<PlayerInfo>
{
    private final String name;
    private final float latency;
    private final float skill;

    private final long timeCreated;

    public PlayerInfo(final String name, final float latency, final float skill)
    {
        this.name = name;
        this.latency = latency;
        this.skill = skill;

        this.timeCreated = Instant.now().toEpochMilli();
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

    @JsonGetter("time_created")
    public long getTimeCreated()
    {
        return timeCreated;
    }

    @Override
    public float getX()
    {
        // latency will be `x` characteristic for the quad tree
        return latency;
    }

    @Override
    public float getY()
    {
        // skill will be `y` characteristic for the quad tree
        return skill;
    }
}