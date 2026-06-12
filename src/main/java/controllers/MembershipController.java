package controllers;

import models.*;
import views.MembershipFrame;
import javax.swing.JOptionPane;

public class MembershipController {

    private final MemberModel  memberModel  = new MemberModel();
    private final PaymentModel paymentModel = new PaymentModel();
    private MembershipFrame    view;

    public void setView(MembershipFrame view) {
        this.view = view;
        refreshTable();

    }

    // ── Member CRUD ───────────────────────────────────────────────────────────

    public void tambahMember(String nama, String email, String noTelp) {
        if (nama.isBlank() || email.isBlank() || noTelp.isBlank()) {
            view.showPesan("Semua field harus diisi!", "Perhatian", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Member m = memberModel.tambah(nama, email, noTelp);
        if (m == null) {
            view.showPesan("Email sudah terdaftar!", "Duplikat", JOptionPane.ERROR_MESSAGE);
        } else {
            view.showPesan("Member berhasil didaftarkan!\nKode: " + m.getKodeMember(),
                           "Sukses", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
            view.clearInputMember();
        }
    }

    public void hapusMember(String kode) {
        if (kode == null || kode.isBlank()) {
            view.showPesan("Pilih member terlebih dahulu!", "Perhatian", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int konfirmasi = view.showKonfirmasi("Hapus member " + kode + "?");
        if (konfirmasi == JOptionPane.YES_OPTION) {
            memberModel.hapus(kode);
            refreshTable();
            view.clearInputMember();
        }
    }

    public void cariMember(String keyword) {
        Member m = memberModel.cariByKode(keyword);
        if (m == null) m = memberModel.cariByNama(keyword);
        if (m != null) {
            view.isiFormMember(m);
        } else {
            view.showPesan("Member tidak ditemukan.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refreshTable() {
        view.refreshTableMember(memberModel.toTableData(), MemberModel.TABLE_COLUMNS);
    }

    // ── Payment ───────────────────────────────────────────────────────────────

    public void prosesPembayaran(double subtotal, String metodeStr,
                                  String emoneNama, String kodeMember,
                                  int poinDigunakan, String kodeMatauang) {
        PaymentMethod metode;
        switch (metodeStr) {
            case "QRIS":    metode = new QRIS();              break;
            case "E-Money": metode = new EMoney(emoneNama);   break;
            default:        metode = new Tunai();             break;
        }

        Member member = (kodeMember != null && !kodeMember.isBlank())
                        ? memberModel.cariByKode(kodeMember) : null;

        Currency currency = Currencies.get(kodeMatauang);

        PaymentModel.RincianBayar r =
            paymentModel.hitung(subtotal, metode, member, poinDigunakan, currency);

        view.tampilkanRincianBayar(r);
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    public Member getMemberByKode(String kode) {
        return memberModel.cariByKode(kode);
    }

    public MemberModel getMemberModel() { return memberModel; }
}
