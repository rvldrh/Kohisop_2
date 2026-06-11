package models;

import java.util.LinkedList;
import java.util.Iterator;

public class MemberModel {
    private LinkedList<Member> daftar = new LinkedList<>();

    // ── CRUD ──────────────────────────────────────────────────────────────────

    /** Tambah member baru; return null jika email duplikat */
    public Member tambah(String nama, String email, String noTelp) {
        for (Member m : daftar)
            if (m.getEmail().equalsIgnoreCase(email)) return null;
        Member m = new Member(nama, email, noTelp);
        daftar.add(m);
        return m;
    }

    public boolean hapus(String kode) {
        Iterator<Member> it = daftar.iterator();
        while (it.hasNext()) {
            if (it.next().getKodeMember().equalsIgnoreCase(kode)) {
                it.remove(); return true;
            }
        }
        return false;
    }

    public Member cariByKode(String kode) {
        for (Member m : daftar)
            if (m.getKodeMember().equalsIgnoreCase(kode)) return m;
        return null;
    }

    public Member cariByNama(String nama) {
        for (Member m : daftar)
            if (m.getNama().equalsIgnoreCase(nama)) return m;
        return null;
    }

    public LinkedList<Member> getDaftar() { return daftar; }
    public int jumlah()                   { return daftar.size(); }

    // ── Data untuk JTable ─────────────────────────────────────────────────────

    public Object[][] toTableData() {
        Object[][] data = new Object[daftar.size()][5];
        int i = 0;
        for (Member m : daftar) {
            data[i][0] = m.getKodeMember();
            data[i][1] = m.getNama();
            data[i][2] = m.getEmail();
            data[i][3] = m.getNoTelp();
            data[i][4] = m.getPoin();
            i++;
        }
        return data;
    }

    public static final String[] TABLE_COLUMNS =
        {"Kode Member", "Nama", "Email", "No. Telp", "Poin"};
}
