/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import views.LoginFrame;
import controllers.AuthController;
/**
 *
 * @author Fiqih
 */
public class MainMenu {
    public static void main(String[] args) {
        LoginFrame login = new LoginFrame();
        AuthController auth = new AuthController();
        login.setVisible(true);
    }
}
