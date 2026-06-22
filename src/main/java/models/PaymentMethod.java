/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
/**
 *
 * @author Jiyyn
 */
public interface PaymentMethod {
    String getNama();
    double adminFee(double total);
    double diskonChannel(double total);
}
