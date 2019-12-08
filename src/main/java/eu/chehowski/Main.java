package eu.chehowski;

import eu.chehowski.handler.AddUserHandler;
import eu.chehowski.handler.ListUsersHandler;
import eu.chehowski.poller.AbstractPoller;
import eu.chehowski.poller.PrinterPoller;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class Main
{
    public static Server createServer(int port)
    {
        Server server = new Server(port);

        final ContextHandler addUserContext = new ContextHandler("/addUser");
        addUserContext.setHandler(new AddUserHandler());

        final ContextHandler listUsersContext = new ContextHandler("/listUsers");
        listUsersContext.setHandler(new ListUsersHandler());

        // add new handlers if necessary

        server.setHandler(new ContextHandlerCollection(
                addUserContext,
                listUsersContext
        ));
        return server;
    }

    public static void main(String[] args) throws Exception
    {
        final AbstractPoller poller = new PrinterPoller(5000);



        final Server server = createServer(8080);
        server.start();
        server.dumpStdErr();
        server.join();
    }
}