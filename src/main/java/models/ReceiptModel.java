/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.util.*;
/**
 *
 * @author rvldrh
 */
public class ReceiptModel {

    private List<ReceiptItem> items;
    private CustomerInfo customer;
    private double taxRate = 0.07;
    private double memberDiscountRate = 0.10;
    private double adminFee = 2.0;

    public ReceiptModel() {
        items = new ArrayList<>();
    }

    public void addItem(ReceiptItem item) {
        items.add(item);
    }

    public List<ReceiptItem> getItems() {
        return items;
    }

    public void setCustomer(CustomerInfo customer) {
        this.customer = customer;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public double getSubtotal() {
        double total = 0;
        for (ReceiptItem item : items) {
            total += item.getTotal();
        }
        return total;
    }

    public double getTax() {
        return getSubtotal() * taxRate;
    }

    public double getMemberDiscount() {
        if (customer == null) {
            return 0;
        }

        if (customer.getStatus().toLowerCase().contains("member")) {
            return getSubtotal() * memberDiscountRate;
        }

        return 0;
    }

    public double getGrandTotal() {
        return getSubtotal()
                + getTax()
                + adminFee
                - getMemberDiscount();
    }

    public double getAdminFee() {
        return adminFee;
    }
}
