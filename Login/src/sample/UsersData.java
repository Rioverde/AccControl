package sample;

public class UsersData {
    String Account;
    String Username;
    String Password;

    public UsersData(String Account, String Username, String Password)
    {
        this.Account = Account;
        this.Username = Username;
        this.Password = Password;

    }

    public String getAccount() {
        return Account;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }
}
