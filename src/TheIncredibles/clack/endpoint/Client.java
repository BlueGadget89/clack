package TheIncredibles.clack.endpoint;

import TheIncredibles.clack.message.FileMessage;
import TheIncredibles.clack.message.Message;
import TheIncredibles.clack.message.ListUsersMessage;
import TheIncredibles.clack.message.LogoutMessage;

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
    public void start()
    {
        while (true) {
            messageToSend = readUserInput(); //prompt the user & read their input
            messageReceived = messageToSend;

            int typeOfMsg = messageReceived.getMsgType(); //get the type of message
            switch (typeOfMsg){
                case 0: //encryption
                    //do sth
                    System.out.println("¯_(ツ)_/¯");
                case 10: //file
                    //do sth
                case 20: //list users
                    printMessage(); //calls toString method()
                case 30: //logout
                    break;
                case 40: //text
                    //do sth
            }

            if (typeOfMsg == 30){
                System.out.println("Exiting System. Goodbye. |˶˙ᵕ˙ )ﾉﾞ "); //if logout is pressed, print goodbye
            }

               //figure out what to do with this message
            //listusers message - call tostring method()
            //FILE MESSAGE - call readfile (this will populate the file contents). Client should dump filecontents into fp2  WRITEFILE METHOD
            //TEXT MESSAGE
            //HELP MESSAGE -  use getdata() (list of commands)

            //printMessage();
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
            String userResponse = myScanner.nextLine().toLowerCase().trim();  // Read user input, lowercase & trim

            //save the input to an array, but split the input by spaces
            String[] userResponseArray = userResponse.split("\\s+"); //split by one or more whitespaces

            //TODO: do the case insensitive match at the point you need it

            //logout case
            if (userResponseArray[0].equalsIgnoreCase("logout")) {
                //toContinue = false;
                return new LogoutMessage(username);
                //System.exit(0); //exit the program

                //list users case
            } else if (userResponseArray[0].equals("list") && Objects.equals(userResponseArray[1], "users")) {
                return new ListUsersMessage(username);

                //fp1 & fp2 specified
            } else if (userResponseArray[0].equals("send") && Objects.equals(userResponseArray[1], "file") && Objects.equals(userResponseArray[3], "as")) {
                return new FileMessage(username, fileSaveAsPath, fileSaveAsPath);

                //fp1 is not empty and "AS" was NOT specified
            } else if (userResponseArray[0].equals("send") && Objects.equals(userResponseArray[1], "file") && !Objects.equals(userResponseArray[2], "") && !Objects.equals(userResponseArray[3], "as")) {
                return new FileMessage(username, fileSaveAsPath, fileSaveAsPath);

                //fp1 is empty
            } else if (userResponseArray[0].equals("send") && Objects.equals(userResponseArray[1], "file") && Objects.equals(userResponseArray[2], "")) {
                return new FileMessage(username, fileSaveAsPath, fileSaveAsPath);

                //help case
            } else if (userResponseArray[0].equals("help")) {
                Help();
            } else {
                System.out.println("ERROR");
                //TODO: return to start()
                return;

                //todo: IF IT DOES NOT MATCH ONE OF THE CMDS, MAKE A TEXT MESSAGE OBJECT
            }
        return null; //TODO: ASK PROF: do I need a return statement here?
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

    //TODO: Javadoc
    /**

     */
    public void Help()
    {
        System.out.println("Command Options:");
        System.out.println("LOGOUT");
        System.out.println("LIST USERS");
        System.out.println("SEND FILE");
        System.out.println("ENCRYPTION KEY");
        System.out.println("ENCRYPTION");
        //TODO: return to start()
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
