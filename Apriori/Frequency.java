
package apriori_confidence;
import java.lang.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author c137202
 */
public class Frequency {
    int minSupport ; 
    int frequencyFileCount ;

    public Frequency(int minSupport) {
        this.minSupport = minSupport;
    }
    
    public int setStartList()
    { 
        //generates the first time list Frequency_1 for primary usage
        /*General Structure for all the files : 
                //Number of entries//
                4
                //Each entry depicted as a set seperated with comma
                //After the set , seperated with a SPACE is number /frequency of that set in transaction summary
                //Single entry , like in frequncy_1 will only have this---> 0, 5
                    
                0,2,3, 3 //(items 0,2,3 are bought by 3 people)
                2,1,3, 2
        */
        int c=0 ;
        try {
        
        //Change this address to your directory address
            
        FileReader r = new FileReader("Z:/DWDM/Dataset.txt");
        File w = new File("Z:/DWDM/Frequency_1.txt");
        
        BufferedReader r1 = new BufferedReader(r);
        FileWriter w1 = new FileWriter(w);
        
        int n , m , g=0;
        String l;
        l = r1.readLine(); //First line in dataset has 2 numbers : number of dataitems available and no. of transactions
        StringTokenizer st = new StringTokenizer(l , " ");
        m = Integer.parseInt(st.nextToken()); //Get number of items
        n  = Integer.parseInt(st.nextToken()); //get number of transactions
        c = 0 ; 
        int []a = new int[m]; //Initailize the frequency array a for each item as 0 
            for(int i=0;i<m;i++)
                a[i] = 0 ;
            
        while(n-- > 0)
        {
            //For each transaction , read the transaction and increment the frequency of each element found in it
            //each transaction starts with a number depicting number of itms bought . It is stored in count
            int count ;
            l = r1.readLine();
            st = new StringTokenizer(l , " ");
            count = Integer.parseInt(st.nextToken()); //get the number of items bought
                
            for(int i = 0;i<count;i++) //For each item , increment its frequency
                a[Integer.parseInt(st.nextToken())]++;
        }
        for(int i = 0 ;i<m;i++)
        {
            //Get count of those items having greater frequencies than minSupport
            if(a[i]>=minSupport)
                c++;
        }
        w1.write(Integer.toString(c));
        w1.write("\r\n");
        for(int i = 0; i<m;i++)
        {
            if(a[i]<minSupport)
                continue;
            w1.write(i + ", " + Integer.toString(a[i])); //Write comma seperated sets and its Count/Frequency
            w1.write("\r\n");
            
        }
        w1.flush();
        
        r1.close();
        w1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c ;
    }
    public int generateFrequencies(int count , ArrayList<Integer> [] a)
    {
        //this function starts generating frequencies
        try{
        int i , c , j , k , joinCount , l = a.length;
        ArrayList<ArrayList<Integer>> group = null;
        
        //Here i use arraylist of arraylist to ensure complete flexibility
        for(i = 2 ; count >0 ; i++)
        {
            //First join the previous frequency file with iteself and save it in candidate file
            //The function returns the data written in candidate file as arraylist of arraylist so no need to read again
            
            group = aprioriJoin(i-1);
            
            //Create an array to store frequency of each item in group arraylist (items obtained after joining) 
            int frequency[]= new int[group.size()];
            for(j = 0 ; j< group.size();j++)
                frequency[j] = 0 ;
        
            for(j = 0 ; j < l;j++)
            {
                for(k=0;k<group.size();k++)
                {
                    //For each transaction in dataset which is now stored in a[] , check if it contains all 
                    //The elements of a specific group .. ie if a[j] contains all element of group [k]
                    //If so , then that means all elements in group[k] were purchased in one transaction only
                    //Thus increment the frequncy of k item referin to k th group
                    
                    if(a[j].containsAll(group.get(k)))
                        frequency[k]++;
                }
            }
            File w = new File("Z:/DWDM/Frequency_"+ i + ".txt");
            FileWriter w1 = new FileWriter(w);
            c = 0 ;
            
            //Before writting the data , count and write the number of trasactions in the file ,
            //So that it is easy to check if end of algo is reached 
            //If count is 0 , that means no more need to continue the loop
            
            for(j = 0 ; j < group.size();j++)
            {
                if(frequency[j]>=minSupport)
                    c++;
            }
            
            w1.write(Integer.toString(c));
            w1.write("\r\n");
           
            //Finally write all the entries with count > minSupport
           
            for(j= 0; j<group.size();j++)
            {
                if(frequency[j]<minSupport)
                    continue;
                ArrayList<Integer> t = group.get(j);
                for(k= 0; k<t.size();k++)
                    w1.write(Integer.toString(t.get(k)) + ",");
                w1.write(" "+ Integer.toString(frequency[j]));
                w1.write("\r\n");
            }
            w1.flush();
            w1.close();
            
            count = c ;
        }
        frequencyFileCount = i ;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return frequencyFileCount;
        
    }
    
    public ArrayList<ArrayList<Integer>> aprioriJoin(int setlength)
    {
        /*Does the similar operation as given in PDF book.
            Takes each element from the datalist and joined it with all other elements
            Such that first k-2 elements are same and a[k-1] < b[k-1]
            After that it checks if the new entry after joining contains any infrequent subset 
            *MOST IMPORTANT POINT*
        */
        ArrayList<ArrayList<Integer>> joined = new ArrayList<ArrayList<Integer>>();
        int count;
        try {
            FileReader r = new FileReader("Z:/DWDM/Frequency_" + setlength + ".txt");
            BufferedReader r1 = new BufferedReader(r);
            File w = new File("Z:/DWDM/Candidate_" + setlength + ".txt");
            FileWriter w1 = new FileWriter(w);
            int i , j , k  , check =1 ;
            
            ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
            count = getListData(a , r1);
            ArrayList<ArrayList<Integer>> b = a ;
          
            for(i=0;i<count;i++)
            {
                for(j=0;j<count;j++)
                {
                    if(j==i)
                        continue;
                    else
                    {
                        for(k=0;k<setlength-1;k++)
                        {
                            //Check is first k-2 elements are same for a and b
                            if(a.get(i).get(k) != b.get(j).get(k))
                            {
                                check = 0;
                                break ;
                            }
                        }
                        if(check == 1 && (a.get(i).get(setlength-1)< b.get(j).get(setlength-1)))
                        {
                            //If same and if a[k-1] < b[k-1] then join it
                            ArrayList<Integer> temp = new ArrayList<Integer>();
                            temp.addAll(a.get(i));
                            temp.add(b.get(j).get(setlength-1));
                            
                            //After joining check if there exist any infrequent subsets
                            
                            //System.out.println("Checking for : " + temp);
                            //if(hasInfrequentSubsets(temp , new ArrayList<Integer>() , a , 0 , 0 , setlength))
                              if(hasInfrequentSubsets(temp, a))
                                continue;
                            else{
                                //If no infrequent subsets exist , add it to the joined arraylist which will then
                                //Be added to the final frequency file
                                joined.add(temp);
                            }
                        }
                        check = 1 ;
                    }
                }
            }
           // System.out.println(joined);
            mySort(joined);
           // System.out.println("Joined Array : " +joined);
            w1.write(String.valueOf(joined.size()));
            w1.write("\r\n");
            for(ArrayList<Integer> l : joined)
            {
                for(i=0;i<l.size();i++)
                {
                    w1.write(l.get(i) + ",");
                }
                w1.write("\r\n");
            }
            w1.flush();
            r1.close();
            w1.close();
            
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return joined;
    }
    
    public int getListData(ArrayList<ArrayList<Integer>> a , BufferedReader r)
    {
        /*get all the data from the frequency file and store it in araylist of arraylist
        So that it is easy to perform checking actions
        */
        int count = 0 ;
        try{
            String l = r.readLine();
            StringTokenizer st = new StringTokenizer(l, " ");
            StringTokenizer in = null ;
            int num = Integer.parseInt(st.nextToken());
            count = num ;
            while(num-->0)
            {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                l = r.readLine() ;
                st = new StringTokenizer(l , " ");
                String set = st.nextToken();
                in = new StringTokenizer(set , ",");
                while(in.hasMoreTokens())
                    temp.add(Integer.parseInt(in.nextToken()));
                a.add(temp);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public void mySort(ArrayList<ArrayList<Integer>> a)
    {
        //Just to sort the arrayList because we need to add in sorted order for algorithm to function properly
        for (ArrayList<Integer> l : a) {
            Collections.sort(l);
        }
        Collections.sort(a, new Comparator<ArrayList<Integer>>() {
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return o1.get(0).compareTo(o2.get(0));
            }
        });
    }
    
    /*NOTE: 
     * The below commented function is actually a recursive function to calculate all 
     * "size" length subsets .Being recursive , it is inefficient for large values of inputs
     * Thus an effecient method is devised using the knowledge that we only require n-1 length subsets 
     * of n length arraylist. Thus we can simply remove one element each and perform pruning
     */
    
    
//    public boolean hasInfrequentSubsets(ArrayList<Integer>a , ArrayList<Integer> temp , ArrayList<ArrayList<Integer>>main , int currentIndex , int level , int size)
//    {
//        //Recursively checks for all subsets of size : "size" 
//        if(level == size)
//        {
//            for(ArrayList l : main)
//            {
//                if(l.containsAll(temp))
//                    return false ;
//            }
//            return true ;
//        }
//        else
//        {
//            for(int i=currentIndex;i<a.size();i++)
//            {
//                temp.add(a.get(i));
//                boolean check = hasInfrequentSubsets(a, temp, main, currentIndex+1, level+1, size);
//                if(check == true)
//                    return true;
//                //Remove the addedd element because arraylist is passed with reference
//                temp.remove(a.get(i));
//            }
//        }
//        return false;
//    }
    
    public boolean hasInfrequentSubsets(ArrayList<Integer> a , ArrayList<ArrayList<Integer>> main)
    {
        /*Since we know that we have to find and check for all n-1 size subsets , and that length of current
         * arraylist a is n , we simply remove each element and check is the new arraylist exist in 
         * any of the trasactions in main
         */
        int i , j , k , check = 0 ;
        for(i = 0 ; i <a.size();i++)
        {
            //Initialize check as 0 . i.e assume that the n-1 subset is not present
            check = 0 ;
            
            int temp = a.get(i);
            a.remove(i);
            for(ArrayList<Integer> l : main)
            {
                if(l.containsAll(a))
                {
                    check = 1 ;
                    a.add(i , temp );
                    break ;
                }
            }
            if(check==0)
            {
                //If even one such subset occurs which does not exist in previous frequency file ,
                //Simply return a true value for hasInfrequentSubset
                a.add(i ,temp);
                return true;
            }
        }
        //Will only reach here if all n-1 subsets exist in atleast one trasactions each
        return false ;
    }
}