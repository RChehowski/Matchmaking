package eu.chehowski.poller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.chehowski.Matchmaker;
import eu.chehowski.model.GroupInfo;

import java.io.PrintStream;

/**
 * Dummy poller that simply prints out the formed group to the stdout.
 */
public final class PrinterPoller extends AbstractPoller
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PrinterPoller(final long pollingPeriod)
    {
        super(pollingPeriod);
    }


    @Override
    final void onTimerTick()
    {
        Matchmaker.getInstance().performMatchmaking()
    }

    private void onGroupCreated(final GroupInfo groupInfo)
    {
        try
        {
            final String valueAsString = objectMapper.writeValueAsString(groupInfo);

            // everything is to be printed into the standard output
            final PrintStream printStream = System.out;

            // print value to the stream
            printStream.println(valueAsString);
            printStream.flush();
        }
        catch (JsonProcessingException jpe)
        {
            // normally this exception should be handled, but it's just a demo writer.
            throw new RuntimeException(jpe);
        }
    }
}
