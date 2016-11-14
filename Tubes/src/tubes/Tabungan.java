package tubes;
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
    private Date Amount_Date;
    private Date Withdraw_Amount_Date;
    
    public int Add_Amount(int amount) {
       return this.Amount += Amount;
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
}
    
