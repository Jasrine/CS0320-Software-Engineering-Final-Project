package edu.brown.cs.jkst.socket;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * file that contains websocket functionality.
 */
public class UserWebSocket {
  private static final Gson GSON = new Gson();
  private static final Queue<Session> SESSIONS = new ConcurrentLinkedQueue<>();
  private static int nextId = 0;

  /**
   * message types.
   */
  private static enum MESSAGE_TYPE {
    CONNECT, LOGIN, LOGOUT, UPDATE
  }

  /**
   * connects to web socket.
   *
   * @param session
   *          which we want to store/connect.
   * @throws IOException
   *           in case some problem is encountered.
   */
  @OnWebSocketConnect
  public void connected(Session session) throws IOException {
    System.out.println("connected!!!");
    nextId++;

    // TODO Add the session to the queue
    SESSIONS.add(session);
    // TODO Build the CONNECT message
    JsonObject message = new JsonObject();
    message.addProperty("type", MESSAGE_TYPE.CONNECT.ordinal());

    JsonObject payload = new JsonObject();
    payload.addProperty("id", nextId);

    message.add("payload", payload);
    String connectMessage = GSON.toJson(message);
    // TODO Send the CONNECT message
    session.getRemote().sendString(connectMessage);
  }

  /**
   * method that handles closing the session.
   *
   * @param session
   *          to be removed from current sessions.
   * @param statusCode
   *          int for exit.
   * @param reason
   *          String for exiting.
   */
  @OnWebSocketClose
  public void closed(Session session, int statusCode, String reason) {
    // TODO Remove the session from the queue
    SESSIONS.remove(session);
    // also update any information on the user in the database.
  }

}
