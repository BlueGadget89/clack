package TheIncredibles.clack.message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Abstract base class for Clack messages.
 *
 * @author D. Tuinstra, adapted from work by Soumyabrata Dey.
 */
public abstract class Message
{
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

    private final int msgType;
    private final LocalDateTime timestamp;
    private final String username;
    private String filePath;
    private String fileSaveAsName;
    private String fileContents;

    /**
     * Constructs a Message object with a given username.
     *
     * <b><i>NOTE that the access level is "protected".</i></b></B>
     * This means that only subclasses of this class can call this
     * constructor. This is (in part) because only a concrete
     * subclass will know what argument to give for the msgType
     * parameter.
     *
     * @param username name of user creating this message.
     */
    protected Message(String username, int msgType)
    {
        this.timestamp = LocalDateTime.now();
        this.username = username;
        this.msgType = msgType;
    }

    /**
     * Get the message's msgType. This allows the possessor
     * of a Message object to know the concrete subclass the
     * object belongs to. This in turn allows the possessor
     * to cast the Message object to the appropriate subclass.
     * The constructor of each concrete Message subclass
     * should set this to the proper value for the subclass.
     *
     * @return the msgType for the object's concrete subclass.
     */
    public int getMsgType()
    {
        return msgType;
    }

    /**
     * Gets the message's timestamp (as a LocalDate).
     *
     * @return the message's timestamp (as a LocalDate).
     */
    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    /**
     * Gets the message's username.
     *
     * @return the message's username.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Return this object's data in a String array. For objects
     * with no data, return an empty array.
     * This is an abstract method; each concrete subclass must
     * override this with a subclass-appropriate implementation.
     *
     * @return object data, in a String array.
     */
    public abstract String[] getData();

    /**
     * Equality comparison. Returns true iff the other object is of
     * the same class and all fields (including those inherited from
     * superclasses) are equal.
     *
     * This is an abstract method; each concrete subclass must
     * override this with a subclass-appropriate implementation.
     *
     * @param o the object to test for equality.
     * @return whether o is of the same class as this, and all fields
     * are equal.
     */
    public abstract boolean equals(Object o);

    /**
     * Return this object's hash. In Message objects, this is simply the
     * hash of the string returned by this.toString().
     *
     * This is an abstract method; each concrete subclass must
     * override this with a subclass-appropriate implementation.

     * @return hash of this object.
     */
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Return a string representation of this object (note no spaces):
     * <br>
     *   <code>"timestamp=<i>the_timestamp</i>|username=<i>the_username</i>"</code>
     * <br>
     * No class name or enclosing brackets are included in the returned
     * string, to allow subclasses to use this method in their
     * toString() implementations.
     * <br><br>
     * @return String showing fields and field contents
     */
    public String toString()
    {
        return "timestamp=" + timestamp + "|username=" + username;
    }

    public void callforhelp()
    {
        System.out.println("Command Options:");
        System.out.println("LOGOUT");
        System.out.println("LIST USERS");
        System.out.println("SEND FILE");
        System.out.println("ENCRYPTION KEY");
        System.out.println("ENCRYPTION");
    }

    /**
     * Write this message's fileContents to the local Clack directory.
     *
     * @throws FileNotFoundException if file cannot be found or created,
     * or opened for writing.
     */
    public void writeFile() throws FileNotFoundException
    {
        if (this.fileContents == null || this.fileSaveAsName == null) {
            throw new FileNotFoundException("File contents or save file name is null.");
        }

        Path outputPath = Paths.get(this.fileSaveAsName);

        try {
            Files.writeString(outputPath, this.fileContents);
        } catch (IOException e) {
            throw new FileNotFoundException("Error writing to file: " + this.fileSaveAsName);
        }
    }
}