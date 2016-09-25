/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuildTicketText;

import java.text.DecimalFormat;

/**
 *
 * @author l.IsSaM.l
 */
public class StructProductTicket {
   public String code ; 
   public String nameProduct ; 
   public int classTVA ;
   public int qte ;
   public boolean promotion ;
   public float price ;

    public StructProductTicket(String code, String nameProduct, int qte, boolean promotion, float price) {
        this.code = code;
        this.nameProduct = nameProduct;
        this.classTVA = classTVA;
        this.qte = qte;
        this.promotion = promotion;
        this.price = price;
    }

    public StructProductTicket(String code, String nameProduct, int classTVA, float price) {
        this.code = code;
        this.nameProduct = nameProduct;
        this.classTVA = classTVA;
        this.price = price;
    }

    
    
    public StructProductTicket()
    {
    }
    
    public String describeCarrefour()
    {
        DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);	
                String Str = "";
        if ( qte > 1)
        {
            Str = nameProduct + "   "+df.format(price)+ "x" +qte+ "    ";
            price=qte*price ;
            Str += df.format(price);
            return Str;
        }
        else
        {
            return nameProduct + "       "+df.format(price) ;
        }
    }

    @Override
    public String toString() {
        String str ="";
         DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);	
        if( promotion )
        {         price = price - 1 ;
         str = code + " " + nameProduct + " \n3 PRIX PROMOTION  "+ df.format(price) ; 
        }
        else 
        {
        if ( qte > 1)
        {
         
             //   System.out.println(" Price : ---------------"+price);
		str = code + " " + nameProduct + "\n3 " +qte+ " x "+price+ " " + df.format(price * qte) ;  
                //price  *=  qte ;
        }
        else 
        {
             str =   code + " " + nameProduct + " " +df.format(price);
        }
        }
        return str ;
        
    }

    public StructProductTicket(String nameProduct) {
        
        
        this.nameProduct = nameProduct;
    }
   
    
    
   
   
   
   
}
