/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author rvldrh
 */
public class MemberRepository {

    private static final String PATH = "members.txt";

    /**
     * Muat semua member dari file, return LinkedList
     */
    public LinkedList<Member> loadAll() {
        LinkedList<Member> list = new LinkedList<>();
        File f = new File(PATH);
        if (!f.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length >= 5) {
                    Member m = Member.fromFile(p[0], p[1], p[2], p[3], Integer.parseInt(p[4]));
                    list.add(m);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Tulis ulang seluruh file dari daftar yang ada
     */
    public void saveAll(LinkedList<Member> daftar) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, false))) {
            for (Member m : daftar) {
                bw.write(m.getKodeMember() + "|"
                        + m.getNama() + "|"
                        + m.getEmail() + "|"
                        + m.getNoTelp() + "|"
                        + m.getPoin());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
