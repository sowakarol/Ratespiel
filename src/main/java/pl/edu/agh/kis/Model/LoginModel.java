package pl.edu.agh.kis.Model;

/**
 * Created by Karl on 14.01.2017.
 */
public class LoginModel {
    String username;
    int portNumber;
    String hostname;

    public LoginModel() {

    }

    public String getHostname() {
        return hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
}
