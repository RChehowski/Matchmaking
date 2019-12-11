package eu.chehowski.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import eu.chehowski.quadtree.QuadTreeItem;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public final class PlayerInfo implements QuadTreeItem
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

    public final String getName()
    {
        return name;
    }

    public final float getLatency()
    {
        return latency;
    }

    public final float getSkill()
    {
        return skill;
    }

    public long getTimeCreated()
    {
        return timeCreated;
    }

    @Override
    public float getX()
    {
        // latency will be `x` parameter for the quad tree
        return latency;
    }

    @Override
    public float getY()
    {
        // skill will be `y` parameter for the quad tree
        return skill;
    }

    @Override
    public String toString() {
        final Date date = new Date(getTimeCreated());

        return "PlayerInfo{" +
                "name='" + name + '\'' +
                ", latency=" + latency +
                ", skill=" + skill +
                ", timeCreated=" + date.toString() +
                '}';
    }
}