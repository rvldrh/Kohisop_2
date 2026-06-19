/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.*;
import views.ReceiptView;
/**
 *
 * @author rvldrh
 */
public class ReceiptController {

    private ReceiptModel model;
    private ReceiptView view;

    public ReceiptController(ReceiptModel model, ReceiptView view) {
        this.model = model;
        this.view = view;

        loadDataToView();
    }

        private void loadDataToView() {
            view.setItems(model.getItems());

            if (model.getCustomer() != null) {
                view.setCustomer(model.getCustomer());
            }

            view.setSummary(
                    model.getSubtotal(),
                    model.getTax(),
                    model.getMemberDiscount(),
                    model.getAdminFee(),
                    model.getGrandTotal()
            );
        }
}
