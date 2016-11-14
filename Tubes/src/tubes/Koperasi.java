/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Koperasi {
    private Anggota[] anggota = new Anggota[100];
    private int Tabungan_Total; 
    private Admin[] admin;
    private int Tabungan;
    private int Keuntungan;
    private int Pinjaman;

    public Koperasi(Anggota[] anggota, int Tabungan_Total, Admin[] admin, int Tabungan, int Keuntungan, int Pinjaman) {
        this.anggota = anggota;
        this.Tabungan_Total = Tabungan_Total;
        this.admin = admin;
        this.Tabungan = Tabungan;
        this.Keuntungan = Keuntungan;
        this.Pinjaman = Pinjaman;
    }

    public Koperasi() {
    }

    public void TambahAnggota(Anggota a){
        int i = 0;
        while(anggota[i] != null){
            i++;
        }
        anggota[i] = a;
    }
    
    public Anggota getAnggota(int i) {
        return anggota[i];
    }

    public void setKeuntungan(int Keuntungan) {
        this.Keuntungan = Keuntungan;
    }

    public int getKeuntungan() {
        return Keuntungan;
    }

    public void setPinjaman(int Pinjaman) {
        this.Pinjaman = Pinjaman;
    }

    public int getPinjaman() {
        return Pinjaman;
    }

    public void setTabungan(int Tabungan) {
        this.Tabungan = Tabungan;
    }

    public int getTabungan() {
        return Tabungan;
    }

    public void setTabungan_Total(int Tabungan_Total) {
        this.Tabungan_Total = Tabungan_Total;
    }

    public int getTabungan_Total() {
        return Tabungan_Total;
    }           
}
