/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori_confidence;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author c137202
 */
public class Confidence {

    int fileCount ;
    HashMap<ArrayList<Integer>, Integer> data ;
    public Confidence(int fileCount) {
        this.fileCount = fileCount ;
        data = new HashMap<ArrayList<Integer>, Integer>();
    }
    public void getAllData()
    {
        try{
        int i  , count , value ; 
        String l ;
        StringTokenizer st = null ;
        StringTokenizer in = null ;
        for(i=1;i<fileCount;i++)
        {
            FileReader r = new FileReader("Z:/DWDM/Frequency_" + i + ".txt");
            BufferedReader r1 = new BufferedReader(r);
            l = r1.readLine();
            st = new StringTokenizer(l , " ");
            count = Integer.parseInt(st.nextToken());
            
            while(count-->0)
            {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                l = r1.readLine() ;
                st = new StringTokenizer(l , " ");
                String set = st.nextToken();
                in = new StringTokenizer(set , ",");
                while(in.hasMoreTokens())
                    temp.add(Integer.parseInt(in.nextToken()));
                value  = Integer.parseInt(st.nextToken());
                data.put(temp, value);
            }
            r1.close();
        }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //System.out.println(data);
    }
    void generateAllAssociations() 
    {
        try{
            
        File w = new File("Z:/DWDM/Confidence.txt");
        FileWriter w1 = new FileWriter(w);
        int dataSize ;
        dataSize= data.size();
        ArrayList<Integer> temp ;
        int value , size , i , j ;
        double confidence; 
        for(Map.Entry dataMap : data.entrySet())
        {
            temp = (ArrayList<Integer>)dataMap.getKey();
            value = (Integer)dataMap.getValue();
            
            size = temp.size();
            for(i=1;i<Math.pow(2 , size);i++)
            {
                ArrayList<Integer> setTemp = new ArrayList<Integer>();
                ArrayList<Integer> remainTemp = new ArrayList<Integer>();
                for(j=0;j<size;j++)
                {
                    int k =(int) Math.pow(2 , j);
                    if((i & k) != 0)
                        setTemp.add(temp.get(j)); 
                    else
                        remainTemp.add(temp.get(j));
                }
                if(setTemp.size()==0 || remainTemp.size()==0)
                    continue;
                confidence = ((double)(data.get(temp))/(data.get(setTemp)))*100;
                w1.write(setTemp + "\t-->\t" + remainTemp + "\t:\t" + confidence);
                w1.write("\r\n");
                
            }
            w1.flush();
        }}catch(Exception e){
            e.printStackTrace();
        }
    }
}
