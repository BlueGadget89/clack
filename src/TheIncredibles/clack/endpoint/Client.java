package TheIncredibles.clack.endpoint;

import TheIncredibles.clack.message.Message;

public class Client
{
    /**
     * Default port for connecting to server. This should be
     * a port listed as "unassigned" in
     * <a href="https://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.txt">IANA</a>.
     */
    public static final int DEFAULT_SERVER_PORT = 6; //TODO: choose an unassigned port (NOT 0!!)

    /**
     *  The server to connect to if one is not explicitly given.
     */
    public static final String DEFAULT_SERVER_NAME = "localhost";

    private final String username;
    private final String serverName;
    private final int serverPort;
    private final String saveDirectory;
    private Message messageToSend;
    private Message messageReceived;

    /**
     *Client constructor
     * @param username the name of the user of the Client
     * @param serverName the name of the serve being used
     * @param serverPort the portnumber to be connected to
     */
    public Client(String username, String serverName, int serverPort) {
        this.username = username;
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    /**
     *Client constructor
     * @param username the name of the user of the Client
     * @param serverName the name of the serve being used
     */
    public Client(String username, String serverName) {
        this(username, serverName, DEFAULT_SERVER_PORT);
    }

    /**
     *Client constructor
     * @param username the name of the user of the Client
     * @param serverPort the portnumber to be connected to
     */
    public Client(String username, int serverPort) {
        this(username, DEFAULT_SERVER_NAME, serverPort);
    }

    /**
     *Client constructor
     * @param username the name of the user of the Client
     */
    public Client(String username) {
        this(username, DEFAULT_SERVER_NAME, DEFAULT_SERVER_PORT);
    }

    /**
     * The client's REPL loop. Prompt for input, build
     * message from it, send message and receive/process
     * the reply, print info for user; repeat until
     * user enters "LOGOUT".
     */
    public void start() {
        //TODO: Implement this.
    }

    /**
     * Parse the line of user input and create the appropriate
     * message.
     * @return an object of the appropriate Message subclass.
     */
    public Message readUserInput() {
        //TODO: implement this.
        return null;
    }

    /**
     * Print the current messageReceived object to System.out.
     * What is printed is the result of calling toString()
     * on the messageReceived object.
     */
    public void printMessage() {
        //TODO: implement this.
    }

    /**
     * Gets the username of the user in use of the client
     * @return A string representing the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the name of the server being used
     * @return A string representing the name of the server being used.
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * Client parameters
     * @return A string with the definitions of the various client parameters
     */
    public String toString() {
        return "{class=Client|"
                + "|DEFAULT_SERVER_NAME=" + DEFAULT_SERVER_NAME
                + "|DEFAULT_SERVER_PORT=" + DEFAULT_SERVER_PORT
                + "|username=" + this.username
                + "|serverName=" + this.serverName
                + "|serverPort=" + this.serverPort
                + "|messageToSend={" + this.messageToSend.toString() + "}"
                + "|messageReceived={" + this.messageReceived.toString() + "}"
                + "}";
    }
}
