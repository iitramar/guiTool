
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class BitScanning 
{
    double reactant, waste, buffer ;
    int operations ;
    
    public double inputConversion(String s_c_t){
            double res = 0;
            for(int i=0;i<s_c_t.length();i++){
                    res = res*10 + (int)s_c_t.charAt(i)-48;
            }
            return res;
    }

    public String toBinary(double d, int precision)
    {
        long wholePart = (long) d;
        return wholeToBinary(wholePart) + '.' + fractionalToBinary(d - wholePart);
    }

    public String wholeToBinary(long l)
    {
        return Long.toBinaryString(l);
    }

    public String fractionalToBinary(double num)
    {
        StringBuilder binary = new StringBuilder();
        while (num > 0)
        {
            double r = num * 2;
            if (r >= 1)
            {
                binary.append(1);
                num = r - 1;
            }
            else
            {
                binary.append(0);
                num = r;
            }
        }
        return binary.toString();
    }
    
    void calculateStat(TreeBS tree){
        
        int rCount = tree.countValue(tree.root, 1.0);
        int bCount = tree.countValue(tree.root, 0.0);
        
        reactant = rCount/ 2.0 ;
        buffer = bCount/ 2.0 ;
        operations = (rCount + bCount) - 1 ;
        waste = (operations - 1) / 2.0 ;
    }
    
    public void writeStat(String s_c_t, String fileName) throws IOException{
        File statFile = new File("./stat/BitScanning/" + fileName);
        FileWriter fw = null;
        //FileReader fr = null;
        BufferedReader br = null;
        int serialNo = 0;
        try {
                if(!statFile.exists()){
                    statFile.createNewFile();
                    serialNo = 1 ;
                }
                else{
                    br = new BufferedReader(new FileReader(statFile));

                    String lastLine = "";
                    String sCurrentLine;
                    while ((sCurrentLine = br.readLine()) != null) 
                    {
                        //System.out.println(sCurrentLine);
                        lastLine = sCurrentLine;
                    }
                    serialNo = Integer.parseInt(lastLine.split("\t")[0]) + 1;

                }
                fw = new FileWriter( statFile , true);
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        fw.append(serialNo + "\t" + s_c_t + "\t" + reactant + "\t" + buffer + "\t"  + waste + "\t" + operations + "\n");
        //System.out.println(reactant + " " + waste + " " + operation);
        fw.close();
    }

    void runBitScan(String ct , String d) throws IOException
    {
            TreeBS t = new TreeBS(0);
            double target = inputConversion(ct);
            double denominator = Math.pow(2, inputConversion(d));
            double ratio = target/denominator;
            System.out.println(target);
            String binary = fractionalToBinary(ratio);
            System.out.println(binary);
            char c ;
            double cInt;
            for(int i=binary.length()-1 ; i>= 0 ;i--)
            {
                    c = binary.charAt(i);
                    cInt = (double)(c -'0');
                    t.mix(cInt, 0.5);
            }



            TreeBSToDot ttd = new TreeBSToDot();
            ttd.createDotFile("BitScanningDot.dot");
            ttd.bsGraphStart();
            ttd.writeToDot(t, 1, denominator);
            ttd.bsGraphEnd();
            ttd.dotToPng("BitScanningDot.dot", "BitScanningDot.png");
            
            calculateStat(t);
            writeStat(ct, "BitScanning_Demo.txt");
    }
    
    void runBitScanForStat(String ct , String d,  FileWriter fw, DefaultTableModel tableModel) throws IOException
    {
            TreeBS t = new TreeBS(0);
            double target = inputConversion(ct);
            double denominator = Math.pow(2, inputConversion(d));
            double ratio = target/denominator;
            System.out.println(target);
            String binary = fractionalToBinary(ratio);
            System.out.println(binary);
            char c ;
            double cInt;
            for(int i=binary.length()-1 ; i>= 0 ;i--)
            {
                    c = binary.charAt(i);
                    cInt = (double)(c -'0');
                    t.mix(cInt, 0.5);
            }
            
            calculateStat(t);
            String lastLine  = ct + "\t" + ct + "\t" + reactant + "\t" + buffer + "\t" + waste + "\t" + operations ;
            try {
                fw.append(lastLine + "\n");
                
            } catch (IOException ex) {
                Logger.getLogger(Remia.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String[] sArray = lastLine.split("\t");
            Object[] ob = new Object[sArray.length];
            for(int i=0; i< sArray.length; i++){
                if( i == 0 || i == 1){
                    ob[i] = Integer.parseInt(sArray[i]) ;
                }
                else{
                    ob[i] = Double.parseDouble(sArray[i]) ;
                }
            }
            
            
            tableModel.addRow(ob);
    }
    
    void runBitScan(String d,  FileWriter fw, DefaultTableModel tableModel){
        double den = Math.pow(2, inputConversion(d));
        for(int i =1 ; i < den ;i ++){
            try {
                runBitScanForStat(((Integer)i).toString(), d, fw, tableModel);
            } catch (IOException ex) {
                Logger.getLogger(BitScanning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
	
	
}