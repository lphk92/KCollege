package dkl.kshare;

public class User
{
    private int id;
    private String password;
    private String email;

    /* Blank constructor* */
    public User()
    {
        // blank!
    }

    /* Constructor without id* */
    public User(String password, String email)
    {
        this.password = password;
        this.email = email;
    }

    /* Full constructor (with id)* */
    public User(String password, String email, int id)
    {
        this.password = password;
        this.email = email;
        this.id = id;
    }

    // Getters
    public String getpassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public int getId()
    {
        return id;
    }

    // Setters
    public void setpassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
