/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import models.MenuModel;
import models.PemesananModel;
import views.PemesananFrame;
import controllers.PemesananController;
/**
 *
 * @author Fiqih
 */
public class MainMenu {
    public static void main(String[] args) {
        MenuModel menuModel = new MenuModel();
        PemesananModel pesanModel = new PemesananModel(menuModel);
        PemesananFrame view = new PemesananFrame();
        
        new PemesananController(menuModel, pesanModel, view);
        
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
