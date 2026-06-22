/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
/**
 *
 * @author Jiyyn
 */
public interface Currency {
    String getKode();
    String getSimbol();
    double kursKeIDR();
    default double dariIDR(double idr) { return idr / kursKeIDR(); }
    default double keIDR(double val)   { return val * kursKeIDR(); }
}
