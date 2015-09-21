/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori_confidence;
import java.lang.*;
import java.util.*;
import java.io.*;
import sun.util.locale.StringTokenIterator;
/**
 *
 * @author c137202
 */
public class Transactions {
    public void getTransactionList(ArrayList<Integer> a[] , int transactionSize)
    {
      
        try{
         FileReader r = new FileReader("Z:/DWDM/Dataset.txt");
         BufferedReader r1 = new BufferedReader(r);
         String l = r1.readLine();
         StringTokenizer st ;
         int i = 0 ;
         while(transactionSize-- >0)
         {
             l = r1.readLine() ;
             st = new StringTokenizer(l , " ");
             Integer.parseInt(st.nextToken());
                
             while(st.hasMoreTokens())
                 a[i].add(Integer.parseInt(st.nextToken()));
             i++ ;
         }
         r1.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
