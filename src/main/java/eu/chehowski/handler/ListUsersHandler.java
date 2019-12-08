package eu.chehowski.handler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListUsersHandler extends AbstractHandler
{
    @Override
    public void handle(final String s,
                       final Request request,
                       final HttpServletRequest httpServletRequest,
                       final HttpServletResponse httpServletResponse)
            throws IOException, ServletException
    {

    }
}
