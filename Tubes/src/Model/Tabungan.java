package Model;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CGK
 */
public class Tabungan {
    private int Amount;
    private String jenis_tabungan;

    public Tabungan() {
        this.Amount = 0;
    }
    
    public Tabungan(int Amount, String jenis_tabungan) {
        this.Amount = Amount;
        this.jenis_tabungan = jenis_tabungan;
    }
    
    public int Add_Amount(int amount) {
       return this.Amount += amount;
    }

    public void Withdraw_Amount(int amount) {
        this.Amount -= amount;
    }
    
    public int Get_Amount() {
        return this.Amount;
    }
    
    public void Info_Tabungan() {
        System.out.println("Jumlah Tabungan adalah : "+Amount);
    }

    public void setJenis_tabungan(String jenis_tabungan) {
        this.jenis_tabungan = jenis_tabungan;
    }

    public String getJenis_tabungan() {
        return jenis_tabungan;
    }
    
}
    
