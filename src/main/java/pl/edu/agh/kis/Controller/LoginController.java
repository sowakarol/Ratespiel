package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.Model.LoginModel;
import pl.edu.agh.kis.View.LoginFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 14.01.2017.
 */
public class LoginController implements ActionListener {
    LoginModel loginModel;
    LoginFrame loginFrame;
    String username;
    int portNumber;
    boolean initialized;

    LoginController() {
        loginFrame = new LoginFrame(this);
        loginModel = new LoginModel();
    }

    public LoginController(LoginModel loginModel, LoginFrame loginFrame) {

        this.loginModel = loginModel;
        this.loginFrame = loginFrame;
    }

    public static void main(String[] args) {
        LoginController loginController = new LoginController();
        LoginFrame loginFrame = new LoginFrame(loginController);
        loginFrame.set();
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public LoginFrame getLoginFrame() {
        return loginFrame;
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        username = loginFrame.getUserLabel().getText();
        String tmp = loginFrame.getPortLabel().getText();
        portNumber = Integer.parseInt(tmp);
        initialized = true;
    }
}
