/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.*;
import views.KitchenView;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import views.*;

/**
 *
 * @author rvldrh
 */
public class KitchenController {

    private KitchenModel model;
    private KitchenView view;
    private User userAktif;

    public KitchenController(KitchenModel model, KitchenView view) {
        this.model = model;
        this.view = view;

        loadData();
        view.getBtnOrder().addActionListener(e -> {
            MenuModel menuModel = new MenuModel();
            PemesananModel pesanModel = new PemesananModel(menuModel);
            PemesananFrame orderView = new PemesananFrame();

            new PemesananController(menuModel, pesanModel, orderView, userAktif);

            orderView.setVisible(true);
        });
    }

    private void loadData() {
        model.loadOrders();

        PriorityQueue<KitchenOrder> foodQueue
                = new PriorityQueue<>(
                        (a, b) -> Double.compare(b.getHarga(), a.getHarga())
                );

        Stack<KitchenOrder> drinkStack = new Stack<>();

        for (KitchenOrder order : model.getOrders()) {
            if (order.getKategori().equalsIgnoreCase("Makanan")) {
                foodQueue.add(order);
            } else {
                drinkStack.push(order);
            }
        }

        renderFood(foodQueue);
        renderDrinks(drinkStack);
    }

    private void renderFood(PriorityQueue<KitchenOrder> queue) {
        DefaultTableModel dtm
                = (DefaultTableModel) view.getFoodTable().getModel();

        dtm.setRowCount(0);

        int no = 1;

        while (!queue.isEmpty()) {
            KitchenOrder item = queue.poll();

            String priority;

            if (item.getHarga() >= 100) {
                priority = "Tinggi";
            } else if (item.getHarga() >= 50) {
                priority = "Sedang";
            } else {
                priority = "Rendah";
            }

            dtm.addRow(new Object[]{
                no++,
                item.getNama(),
                item.getHarga(),
                priority
            });
        }
    }

    private void renderDrinks(Stack<KitchenOrder> stack) {
        view.clearDrinkList();

        int no = 1;
        int total = stack.size();

        while (!stack.isEmpty()) {
            KitchenOrder item = stack.pop();

            String status = null;

            if (no == 1) {
                status = "Terakhir\ndipesan";
            } else if (no == total) {
                status = "Pertama\ndipesan";
            }

            view.addDrinkItem(no, item.getNama(), status);
            no++;
        }
    }
}
