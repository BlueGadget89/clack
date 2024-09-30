package TheIncredibles.clack.message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Objects;

/**
 * This class represents messages containing the name and
 * contents of a text file.
 *
 * @author D. Tuinstra, adapted from work by Soumyabrata Dey.
 */
public class FileMessage extends Message
{

    private String filePath;
    private String fileSaveAsName;
    private String fileContents;

    /**
     * Constructs a FileMessage object with a given username
     * and file paths. It does not read in the file contents --
     * that must be done with readFile(). The fileSaveAsPath is
     * not used in its entirety: only the filename portion of
     * is kept and used when saving the message's file contents.
     *
     * @param username name of user for this message.
     * @param filePath where to find the file to read.
     * @param fileSaveAsPath the filename portion of this is used when saving the file.
     */
    public FileMessage(String username, String filePath, String fileSaveAsPath)
    {
        super(username, MSGTYPE_FILE);
        this.filePath = filePath;
        //parse the filename portion of fileSaveAsPath
        Path path = Paths.get(fileSaveAsPath);
        this.fileSaveAsName = path.getFileName().toString();
        // This really should be null when object is created.
        this.fileContents = null;
    }

    /**
     * Constructs a FileMessage object with a given username,
     * and a given filePath to give both the reading and saving
     * location of the file. It does not read in the file contents --
     * that must be done with readFile().
     *
     * @param username name of user for this message.
     * @param filePath where to find the file to read, and where to write it.
     */
    public FileMessage(String username, String filePath)
    {
       this(username, filePath, filePath);
    }

    /**
     * Get the path, on the local file system, of the file to read.
     *
     * @return the path to the file to read.
     */
    public String getFilePath()
    {
        return filePath;
    }

    /**
     * Get the path where the file is to be written.
     *
     * @return the path where the file is to be written.
     */
    public String getFileSaveAsName() {
        return this.fileSaveAsName;
    }

    /**
     * Set the path where the file-to-read is located. This does not
     * cause the file to be read -- that must be done with readFile().
     *
     * @param filePath new file name to associate with this message.
     */
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
        //might be nice to add some validation
    }

    /**
     * Set the name for the file to be written. This does not
     * cause the file to be written -- that must be done with
     * writeFile(). It is an IllegalArgument exception if the
     * fileSaveAsName contains path components.
     *
     * @throws IllegalArgumentException if fileSaveAsName contains path components
     */
    public void setFileSaveAsName(String fileSaveAsName)
    {
        Path path = Paths.get(fileSaveAsName);
        if(path.getNameCount() > 1)
        {
            throw new IllegalArgumentException("fileSaveAsName contains path components");
        }
        this.fileSaveAsName = fileSaveAsName;
    }

    /**
     * Returns a three-element array of String. The first element is
     * the current filePath value, the second is the current
     * fileSaveAsName value, and the third is the current
     * fileContents value. The method does <b><em>not</em></b> read
     * the file named by filename -- that must be done with readFile().
     *
     * @return the current values of filePath, fileSaveAsName, and fileContents.
     */
    @Override
    public String[] getData()
    {
        return new String[]{filePath,fileSaveAsName,fileContents};
    }

    /**
     * Read contents of file 'filePath' into this message's fileContents.
     *
     * @throws IOException if the file named by this.filPath does
     * not exist or cannot be opened for reading.
     */
    /* Since Java 11, there's an easy way to do this. It even handles
     * closing the files when done, whether normally or by Exception
     * (so we don't need to use try-with-resources). See
       https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
     */
    public void readFile() throws IOException
    {
        Path file = Paths.get(this.filePath);
        if(!Files.exists(file))
        {
            throw new FileNotFoundException("File does not exist: " + this.filePath);
        }
        if(!Files.isReadable(file))
        {
            throw new FileNotFoundException("File is not readable: " + this.filePath);
        }
        this.fileContents = Files.readString(file);
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

    @Override
    public String toString()
    {
        return "{class=FileMessage|" + super.toString() +
                "|filePath=" + this.filePath +
                "|fileSaveAsName=" + this.fileSaveAsName +
                "|fileContents=" + this.fileContents + "}";
    }

    @Override
    public boolean equals(Object o)
    {
        //check if object is being compared to itself
        if (this == o) return true;
        //check if object is null or not of the same class
        if (o == null || getClass() != o.getClass()) return false;
        //Cast to FileMessage
        FileMessage that = (FileMessage) o;
        //Compare all relevant fields
        return Objects.equals(filePath, that.filePath) &&
                Objects.equals(fileSaveAsName, that.fileSaveAsName) &&
                Objects.equals(fileContents, that.fileContents) &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getTimestamp(), that.getTimestamp());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(filePath, fileSaveAsName, fileContents, getUsername(), getTimestamp());
    }
}