package TheIncredibles.clack.endpoint;

import TheIncredibles.clack.message.*;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class Client
{
    /**
     * Default port for connecting to server. This should be
     * a port listed as "unassigned" in
     * <a href="https://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.txt">IANA</a>.
     */
    public static final int DEFAULT_SERVER_PORT = 6; //TODO: choose an unassigned port (NOT 0!!)

    /**
     * The server to connect to if one is not explicitly given.
     */
    public static final String DEFAULT_SERVER_NAME = "localhost";

    private final String username;
    private String filePath;
    private String fileSaveAsPath;
    private final String serverName;
    private final int serverPort;
    private Message messageToSend;
    private Message messageReceived;
    private boolean toContinue = true;
    //private String userResponse;

    /**
     * Client constructor
     *
     * @param username   the name of the user of the Client
     * @param serverName the name of the serve being used
     * @param serverPort the portnumber to be connected to
     */
    public Client(String username, String serverName, int serverPort)
    {
        this.username = username;
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    /**
     * Client constructor
     *
     * @param username   the name of the user of the Client
     * @param serverName the name of the serve being used
     */
    public Client(String username, String serverName)
    {
        this(username, serverName, DEFAULT_SERVER_PORT);
    }

    /**
     * Client constructor
     *
     * @param username   the name of the user of the Client
     * @param serverPort the portnumber to be connected to
     */
    public Client(String username, int serverPort)
    {
        this(username, DEFAULT_SERVER_NAME, serverPort);
    }

    /**
     * Client constructor
     *
     * @param username the name of the user of the Client
     */
    public Client(String username)
    {
        this(username, DEFAULT_SERVER_NAME, DEFAULT_SERVER_PORT);
    }

    /**
     * The client's REPL loop. Prompt for input, build
     * message from it, send message and receive/process
     * the reply, print info for user; repeat until
     * user enters "LOGOUT".
     */

    /**
     * Message type for encrypted messages
     */
    public static int MSGTYPE_ENCRYPTION = 0;
    /**
     * Message type for file transfer messages
     */
    public static int MSGTYPE_FILE = 10;
    /**
     * Message type for requesting a list of active users on the server.
     */
    public static int MSGTYPE_LISTUSERS = 20;
    /**
     * Message type for logging out from the server
     */
    public static int MSGTYPE_LOGOUT = 30;
    /**
     * Message type for text messages sent between users
     */
    public static int MSGTYPE_TEXT = 40;

    /**
     * Message type for help messages
     */
    public static int MSGTYPE_HELP = 50;

    public void start()
    {
        outsideWhileloop:
        while (true) {
            messageToSend = readUserInput(); //prompt the user & read their input
            messageReceived = messageToSend;

            int typeOfMsg = messageReceived.getMsgType(); //get the type of message
            switch (typeOfMsg) {
                case MSGTYPE_ENCRYPTION: //encryption
                    System.out.println("Do some encryption"); //TODO: encryption
                    break;

                case 10: //send file
                    //use the write file method
                    System.out.println("Use writefile() to dump filecontents into fp2"); //TODO: writefile
                    messageReceived.writeFile();
                    break;

                case 20: //list users
                    printMessage(); //calls toString method() //TODO: should query the server for all connected users
                    break;

                case 30: //logout
                    break; //exit out of this loop, then print the goodbye message

                case 40: //text
                    String[] out = messageReceived.getData(); //print text??
                    System.out.println(out[0]);
                    break;

                case 50: //help
                    messageReceived.callforhelp(); //print text??
                    break;
            }

            if (typeOfMsg == 30) {
                System.out.println("Exiting System. Goodbye. |˶˙ᵕ˙ )ﾉﾞ "); //if logout is pressed, print goodbye
                break outsideWhileloop; //breakout of while loop
            }
            ;
            //HELP MESSAGE -  use getdata() (list of commands)
        }
    }

    /**
     * Parse the line of user input and create the appropriate
     * message.
     *
     * @return an object of the appropriate Message subclass.
     */
    public Message readUserInput() //reads AND evaluates
    {
        Scanner myScanner = new Scanner(System.in);  // Create a Scanner object
        System.out.print("What would you like to do?: "); //prompt user
        String userResponse = myScanner.nextLine().trim();  // Read user input & trim for whitespaces

        //save the input to an array, but split the input by spaces
        String[] userResponseArray = userResponse.split("\\s+"); //split by one or more whitespaces

        //logout case
        if (userResponseArray[0].equalsIgnoreCase("logout")) {
            return new LogoutMessage(username);

            //list users case
        } else if (userResponseArray[0].equalsIgnoreCase("list") &&
                userResponseArray[1].equalsIgnoreCase("users")) {
            return new ListUsersMessage(username);

            //send file case
            //fp1 & fp2 specified [SEND FILE fp1 AS fp2]
        } else if (userResponseArray.length == 5) {
            if (userResponseArray[0].equalsIgnoreCase("send") &&
                    userResponseArray[1].equalsIgnoreCase("file") &&
                    userResponseArray[3].equalsIgnoreCase("as")) {
                filePath = userResponseArray[2];
                fileSaveAsPath = userResponseArray[4];
                System.out.println("inside send 'as'");
                return new FileMessage(username, filePath, fileSaveAsPath);
            }

            //send file case
            //fp1 is not empty and "AS" was NOT specified [SEND FILE fp1]
        } else if (userResponseArray.length == 3) {
            if (userResponseArray[0].equalsIgnoreCase("send") &&
                    userResponseArray[1].equalsIgnoreCase("file")) { //if "fp1" is present
                filePath = userResponseArray[2];
                fileSaveAsPath = userResponseArray[2];
                System.out.println("inside send file");
                return new FileMessage(username, filePath, fileSaveAsPath);

                // encryption key case
            } else if (userResponseArray[0].equalsIgnoreCase("encryption") &&
                    userResponseArray[1].equalsIgnoreCase("key")) {
                System.out.println("encryption with key");
                //TODO: return something
            }
            //send file case
            //fp1 is empty [SEND FILE ]
        } else if (userResponseArray.length == 2) {
            if (userResponseArray[0].equalsIgnoreCase("send") &&
                    userResponseArray[1].equalsIgnoreCase("file")) {
                return new HelpMessage(username);
            } //return help message

            //encryption on off case
            else if (userResponseArray[0].equalsIgnoreCase("encryption") &&
                    userResponseArray[1].equalsIgnoreCase("ON") ||
                    userResponseArray[1].equalsIgnoreCase("OFF")) {
                System.out.println("on off encryption");
            //TODO: return something
                //help case
            }
        } else if (userResponseArray[0].equalsIgnoreCase("help")) {
            return new HelpMessage(username);
        } else {
            return new HelpMessage(username);
        }
        return new TextMessage(username, userResponse);
    }

    /**
     * Print the current messageReceived object to System.out.
     * What is printed is the result of calling toString()
     * on the messageReceived object.
     */
    public void printMessage()
    {
        System.out.println(messageReceived.toString());
    }


    /**
     * Gets the username of the user in use of the client
     *
     * @return A string representing the username of the user.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Gets the name of the server being used
     *
     * @return A string representing the name of the server being used.
     */
    public String getServerName()
    {
        return serverName;
    }

    /**
     * Client parameters
     *
     * @return A string with the definitions of the various client parameters
     */
    public String toString()
    {
        return "{class=Client|"
                + "|username=" + this.username
                + "|serverName=" + this.serverName
                + "|serverPort=" + this.serverPort
                + "|messageToSend={" + this.messageToSend.toString() + "}"
                + "|messageReceived={" + this.messageReceived.toString() + "}"
                + "}";
    }
}
