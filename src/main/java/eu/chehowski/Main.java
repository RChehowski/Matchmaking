package eu.chehowski;

import eu.chehowski.handler.AddUserHandler;
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


        final ContextHandlerCollection contexts = new ContextHandlerCollection(
                addUserContext
        );

        server.setHandler(contexts);
        return server;
    }

    public static void main(String[] args) throws Exception
    {
        int port = 8080;
        Server server = createServer(port);
        server.start();
        server.dumpStdErr();
        server.join();
    }
}