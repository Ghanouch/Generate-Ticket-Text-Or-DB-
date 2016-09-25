/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import BuildTicketText.StructProductTicket;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author l.IsSaM.l
 */
public class Service {
    
    
    public static ArrayList<StructProductTicket> clone(ArrayList<StructProductTicket> maliste)
    {
        ArrayList<StructProductTicket> li = new ArrayList<StructProductTicket>();
        for ( StructProductTicket P : maliste )
           li.add(new StructProductTicket(P.code, P.nameProduct, P.qte, P.promotion, P.price));
        
           return li ;
    }
    
      public static float round(float value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue();
    }
    
    public static void showMap (  Map<String , ArrayList<StructProductTicket> > map)
    {
        try{
        String key = "" ;
         for (Map.Entry<String, ArrayList<StructProductTicket>> entry : map.entrySet()) 
        {
            key  =entry.getKey() ;
            System.out.println(" ________ CLE _________:"+key);
            for (StructProductTicket li : entry.getValue())
                System.out.println(" Valeur :" +li);
           
        }
        }catch(Exception e )
        {
            e.getMessage();
        }
        
    }
    
       public static void showMapInteger(  Map<Integer , Integer > map)
    {
        try{
        int key  ;
         for (Map.Entry<Integer, Integer> entry : map.entrySet()) 
        {
            key  =entry.getKey() ;
            System.out.println(" ________ Id_product _________:"+key);
                System.out.println(" Numbre Of Sales   :" +entry.getValue());
           
        }
        }catch(Exception e )
        {
            e.getMessage();
        }
        
    }
    
    
    
    public static int indiceFromTable( String tab[] , String searchName)
    {
        for ( int i =0 ; i<tab.length ; i++ ) 
        {
            if ( tab[i].equals(searchName))
                return i ;
        }
        return -1 ;
    }
}
