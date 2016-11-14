/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes;


public class Anggota extends Orang{
    private String No_anggota;
    private Tabungan tabungan;
    private boolean Pinjam_Uang;
    private boolean Status_Pinjaman;
    private Peminjaman peminjaman;

    public Anggota() {
    }

    public Anggota(String No_anggota, Tabungan tabungan, boolean Pinjam_Uang, boolean Status_Pinjaman, Peminjaman peminjaman) {
        this.No_anggota = No_anggota;
        this.tabungan = tabungan;
        this.Pinjam_Uang = Pinjam_Uang;
        this.Status_Pinjaman = Status_Pinjaman;
        this.peminjaman = peminjaman;
    }

    public void setTabungan(Tabungan tabungan) {
        this.tabungan = tabungan;
    }

    public Tabungan getTabungan() {
        return tabungan;
    }

    public void Set_Pinjam_Uang(boolean Pinjam_Uang) {
        this.Pinjam_Uang = Pinjam_Uang;
    }

    public boolean Get_Pinjam_Uang(){
        return this.Pinjam_Uang;
    }
    
    public Peminjaman getPeminjaman() {
        return peminjaman;
    }

    public void Set_Peminjaman(Peminjaman peminjaman) {
        this.peminjaman = peminjaman;
    }

    public String getNo_anggota() {
        return No_anggota;
    }

    public void setNo_anggota(String No_anggota) {
        this.No_anggota = No_anggota;
    }            
    
    public Tabungan Get_Tabungan(){
        return this.tabungan;
    }
    
    public Peminjaman Get_Peminjaman(){
        return this.peminjaman;
    }
}
