package eu.chehowski.poller;

import eu.chehowski.model.GroupInfo;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractPoller
{
    private final Timer pollerTimer = new Timer("Poll Timer " + , true);

    private int groupSize;

    public AbstractPoller(final long pollingPeriod)
    {
        pollerTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                AbstractPoller.this.onTimerTick();
            }
        }, pollingPeriod, pollingPeriod);
    }

    abstract void onTimerTick();
}
