package eu.chehowski.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.chehowski.model.PlayerInfo;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class AddUserHandler extends AbstractHandler
{
    public AddUserHandler()
    {
    }

    private static PlayerInfo getPlayerInfoFromRequest(final HttpServletRequest request)
    {
        final String name = request.getParameter("name");
        if (name == null)
            return null;

        final String latencyString = request.getParameter("latency");
        if (latencyString == null)
            return null;

        final String skillString = request.getParameter("skill");
        if (skillString == null)
            return null;

        // Parse
        try {
            final double latency = Double.parseDouble(latencyString);
            final double skill = Double.parseDouble(skillString);

            return new PlayerInfo(name, (float)latency, (float)skill);
        }
        catch (NumberFormatException ignore) { }
        return null;
    }

    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        final PlayerInfo playerInfo = getPlayerInfoFromRequest(request);
        final PrintWriter out = response.getWriter();

        response.setContentType("text/html; charset=utf-8");
        if (playerInfo == null)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("<h1>Bad request (unable to parse player info)</h1>");
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_OK);
            out.println("<h1>Added a user: " + playerInfo + "</h1>");
        }

        baseRequest.setHandled(true);
    }
}