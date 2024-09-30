package TheIncredibles.clack.message;

public class HelpMessage extends Message
{

    public HelpMessage(String username)
    {
        super(username, MSGTYPE_HELP);
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

    @Override
    public String[] getData()
    {
        return new String[0];
    }

    @Override
    public boolean equals(Object o)
    {
        return false;
    }
}
