package eu.chehowski.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import eu.chehowski.util.KahanAverage;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * A model class defining matchmaking result.
 */
@SuppressWarnings("unused")
public final class GroupInfo
{

    // group number
    private final long groupNumber;

    // num players in group

    // min skill in group
    private final float minSkill;

    // max skill in group
    private final float maxSkill;

    // avg skill in group
    private final float avgSkill;

    // min latency in group
    private final float minLatency;

    // max latency in group
    private final float maxLatency;

    // avg latency in group
    private final float avgLatency;

    // min time spent in queue
    private final long minTimeInQueue;

    // max time spent in queue
    private final long maxTimeInQueue;

    // avg time spent in queue
    private final long avgTimeInQueue;

    // unmodifiable list of player names
    private final Collection<String> players;

    public GroupInfo()
    {
        this.groupNumber = 0;
        this.minSkill = Float.NaN;
        this.maxSkill = Float.NaN;
        this.avgSkill = Float.NaN;
        this.minLatency = Float.NaN;
        this.maxLatency = Float.NaN;
        this.avgLatency = Float.NaN;
        this.minTimeInQueue = Long.MIN_VALUE;
        this.maxTimeInQueue = Long.MAX_VALUE;
        this.avgTimeInQueue = 0L;
        this.players = Collections.emptyList();
    }

    public GroupInfo(final long groupNumber, final Collection<PlayerInfo> players)
    {
        this.groupNumber = groupNumber;

        // Stream.average() uses Kahan's algorithm to reduce numerical error while computing an average,
        // thus it is unwanted to get rid of there streams (unless we implement a reliable avg algorithm)
        // @see https://en.wikipedia.org/wiki/Kahan_summation_algorithm
        this.minSkill = (float)players.stream().mapToDouble(PlayerInfo::getSkill).min().orElse(-1.0);
        this.maxSkill = (float)players.stream().mapToDouble(PlayerInfo::getSkill).max().orElse(-1.0);
        this.avgSkill = (float)players.stream().mapToDouble(PlayerInfo::getSkill).average().orElse(-1.0);

        this.minLatency = (float)players.stream().mapToDouble(PlayerInfo::getLatency).min().orElse(-1.0);
        this.maxLatency = (float)players.stream().mapToDouble(PlayerInfo::getLatency).max().orElse(-1.0);
        this.avgLatency = (float)players.stream().mapToDouble(PlayerInfo::getLatency).average().orElse(-1.0);

        final long timeNow = Instant.now().toEpochMilli();
        this.minTimeInQueue = players.stream().mapToLong(x -> timeNow - x.getTimeCreated()).min().orElse(-1L);
        this.maxTimeInQueue = players.stream().mapToLong(x -> timeNow - x.getTimeCreated()).max().orElse(-1L);
        this.avgTimeInQueue = Math.round(players.stream().mapToDouble(x -> (double)(timeNow - x.getTimeCreated()))
                .average().orElse(-1.0));

        // retrieve players
        this.players = Collections.unmodifiableCollection(players.stream().map(PlayerInfo::getName)
                .collect(Collectors.toList()));
    }

    @JsonGetter("group_number")
    public long getGroupNumber()
    {
        return groupNumber;
    }

    @JsonGetter("num_players")
    public int getNumPlayers()
    {
        return players.size();
    }

    @JsonGetter("min_skill")
    public float getMinSkill()
    {
        return minSkill;
    }

    @JsonGetter("max_skill")
    public float getMaxSkill()
    {
        return maxSkill;
    }

    @JsonGetter("avg_skill")
    public float getAvgSkill()
    {
        return avgSkill;
    }

    @JsonGetter("min_latency")
    public float getMinLatency()
    {
        return minLatency;
    }

    @JsonGetter("max_latency")
    public float getMaxLatency()
    {
        return maxLatency;
    }

    @JsonGetter("avg_latency")
    public float getAvgLatency()
    {
        return avgLatency;
    }

    @JsonGetter("min_time_in_queue")
    public long getMinTimeInQueue()
    {
        return minTimeInQueue;
    }

    @JsonGetter("max_time_in_queue")
    public long getMaxTimeInQueue()
    {
        return maxTimeInQueue;
    }

    @JsonGetter("avg_time_in_queue")
    public long getAvgTimeInQueue()
    {
        return avgTimeInQueue;
    }

    @JsonGetter("players")
    public Collection<String> getPlayers()
    {
        return players;
    }

    public String toJson()
    {
        return "";
    }
}
