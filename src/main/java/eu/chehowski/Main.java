package eu.chehowski;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.chehowski.handler.AddUserHandler;
import eu.chehowski.model.GroupInfo;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Main
{
    private static final int POLL_PERIOD = 5000;
    private static final int PLAYERS_IN_GROUP = 10;

    public static Server createServer(int port)
    {
        Server server = new Server(port);

        final ContextHandler addUserContext = new ContextHandler("/addUser");
        addUserContext.setHandler(new AddUserHandler());

        // add new handlers if necessary

        server.setHandler(new ContextHandlerCollection(
                addUserContext
        ));
        return server;
    }

    private static void pollThreadRunnable()
    {
        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                pollRunnable();
            }
        }, POLL_PERIOD, POLL_PERIOD);
    }

    private static void pollRunnable()
    {
        final ObjectMapper mapper = new ObjectMapper();

        // everything is to be printed into the standard output
        final PrintStream printStream = System.out;

        Optional<GroupInfo> optionalGroupInfo =
                Matchmaker.getInstance().performMatchmaking(PLAYERS_IN_GROUP);

        long numGroupsFormed = 0;
        while (optionalGroupInfo.isPresent())
        {
            // Just print to the stdout
            try {
                final String valueAsString = mapper.writeValueAsString(optionalGroupInfo.get());

                // print value to the stream
                printStream.println(valueAsString);
            }
            catch (JsonProcessingException jpe) {
                // normally this exception should be handled, but it's just a demo writer.
                throw new RuntimeException(jpe);
            }

            optionalGroupInfo = Matchmaker.getInstance().performMatchmaking(PLAYERS_IN_GROUP);
            ++numGroupsFormed;
        }

        if (numGroupsFormed > 0)
        {
            printStream.println(">>> total " + numGroupsFormed + " formed per iteration");
        }
        else
        {
            printStream.println("Not enough players yet enqueued to form a group");
        }

        printStream.flush();
    }

    public static void main(String[] args) throws Exception
    {
        final Thread pollThread = new Thread(Main::pollThreadRunnable, "poll thread");
        pollThread.setDaemon(true); // exit the matchmaker when the server is stopped.
        pollThread.start();

        final Server server = createServer(8080);
        server.start();
        server.dumpStdErr();
        server.join();
    }
}