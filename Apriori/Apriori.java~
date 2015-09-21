
package apriori_confidence;
import java.lang.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author c137202
 */
public class Apriori {

    //WARNING : When you run the code in any other computer , 
                //CHANGE THE ADDRESS OF FILES IN rest of classes to your directory address
    public static void main(String[] args) {
        
        //Get the input about trasactions data
        int frequencyFileCount ;
        Scanner X = new Scanner(System.in);
        System.out.println("Enter no. of data items : " );
        int numElements = X.nextInt();
        System.out.println("Enter no. of transactions : ");
        int transactionSize = X.nextInt();
        System.out.println("Enter the minimum support : ");
        int support = X.nextInt(); 
       
        //Generate the dataset
        
        Dataset ds = new Dataset();
        ds.generateDataset(numElements , transactionSize);
        
       //create a table form array of arraylist to store the trasactions data
       //Created an array of array list depicting each element of an array as a transaction 
       //eg. a[0] is the first trasaction with details of purchases as a[0] = {1 , 2  ,3}
        
        ArrayList<Integer> []a = (ArrayList<Integer>[])new ArrayList[transactionSize] ;
        for(int i=0;i<transactionSize;i++)
            a[i] = new ArrayList<Integer>();
        Transactions t = new Transactions() ;
       
        //Get all the trasaction data from the dataset file into program memory
        t.getTransactionList(a , transactionSize);
        
        //find frequency_1 set because this is manual . After this its a automatic process untill the count of entries 
        //In frequency file is 0
        
        Frequency f = new Frequency(support);
        
        //Count is initial number of entries with minimum support in frequecy_1 table
        int count = f.setStartList();
        
        //Finally call the function to find rest of the frequencies
        frequencyFileCount = f.generateFrequencies(count , a);
        
        Confidence c = new Confidence(frequencyFileCount);
        c.getAllData();
        c.generateAllAssociations();
    }
}
