/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
/**
 *
 * @author Jiyyn
 */
public class Tunai implements PaymentMethod {
    @Override public String getNama()              { return "Tunai"; }
    @Override public double adminFee(double t)     { return 0; }
    @Override public double diskonChannel(double t){ return 0; }
}
