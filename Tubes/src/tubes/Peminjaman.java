/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes;

import java.util.ArrayList;

/**
 *
 * @author IIS
 */
public class Peminjaman {
    private int Amount;
    private int Time;
    private ArrayList<Angsuran> Bayar_Angsuran;
    
    public Peminjaman(int Amount, int Time, ArrayList<Angsuran> Bayar_Angsuran) {
        this.Amount = Amount;
        this.Time = Time;
        this.Bayar_Angsuran = Bayar_Angsuran;
    }

    public void Bayar_Angsuran(ArrayList<Angsuran> Bayar_Angsuran) {
        this.Bayar_Angsuran = Bayar_Angsuran;
    }

    public String info_Pinjaman() {
        return info_Pinjaman();
    }
    
    public int Get_Amount(){
        return Amount;
    }
}
