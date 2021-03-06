package eu.chehowski.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.chehowski.Matchmaker;
import eu.chehowski.model.PlayerInfo;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
    public void handle(final String target,
                       final Request baseRequest,
                       final HttpServletRequest request,
                       final HttpServletResponse response)
            throws IOException
    {
        final PlayerInfo playerInfo = getPlayerInfoFromRequest(request);
        final PrintWriter out = response.getWriter();

        response.setContentType("text/html; charset=utf-8");

        if (playerInfo != null)
        {
            response.setStatus(HttpServletResponse.SC_OK);
            out.println(playerInfo.toString());

            // register the player info for the matchmaking
            Matchmaker.getInstance().addPlayerInfo(playerInfo);
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("<h1>Bad request (unable to parse player info)</h1>");
        }

        baseRequest.setHandled(true);
    }
}