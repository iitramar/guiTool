import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
class vospa 
{
    double inf ;
    double reactant, waste , operations , consumption , production, buffer;
    int segments ;
    File f;
    FileWriter writer;
    int sub ;
    int z;
    
    public vospa(){
        inf = Double.POSITIVE_INFINITY;
        reactant = 0;
        waste = 0;
        operations = 0;
        consumption = 0;
        production = 0;
        buffer=0;
        writer = null;
        sub = 0;
        z=1;
    }
    public double inputConversion(String s){
        double num = 0,den;
        int i=0;
        while(i<s.length() && s.charAt(i)!='.' && s.charAt(i)!='/'){
            num = 10*num + s.charAt(i)-48;
            i++;
        }
        if(i<s.length() && s.charAt(i)=='/'){
            den = 0;
            i++;
            while(i<s.length()){
                den = 10*den + s.charAt(i)-48;
                i++;
            }
            return num/den;
        }
        else if(i<s.length() &&  s.charAt(i)=='.'){
            i++;
            int j=1;
            while(i<s.length()){
                num = num + (s.charAt(i)-48)/Math.pow(10,j);
                i++;
                j++;
            }
            return num;
        }
        else{
        	return num;
        }
    }

    
    TreeMap<Double,Double> B = new TreeMap<Double,Double>();
    public double Vospa(double c_t, double del, int n, boolean b) throws IOException
    {
        segments = n;
        
        B.put(1.0, inf);
        return masterProcess(c_t, del , B , n, b);
    }
     
    public double masterProcess(double EV, double del , TreeMap<Double,Double> B , int n, boolean b) throws IOException
    {
        ArrayList<Double> M = new ArrayList<Double>();
        int emp_seg = n;
        double AV = 0, LBS, UBS, x, vol_X;
        while(Math.abs(EV-AV)>del)
        {
            LBS = (EV-AV-del)/emp_seg;
            UBS = EV-AV+del;
            Set S = B.keySet();
            Object[] array = S.toArray();
            int index = array.length-1 ;
            x = 1.0;
            vol_X = x/segments;
 
            while(index >= 0)
            {
                vol_X = (double)array[index]/segments;
                if(vol_X>=LBS && vol_X<=UBS)
                {
                    x = (double)array[index];
                    double supp_X = B.get(x);
                    double p = 1.0/segments;
                    if(supp_X>=p){
                        break;
                    }
                }
                index -- ;
            }           
            if(vol_X>=LBS && vol_X<=UBS)
            {
                M.add(x);
                AV = AV + vol_X;
                double supply_X = B.get(x) - (double)1/segments;
                if(x == 1.0)
                {
                    reactant += vol_X;
                }
                else
                {
                    consumption += (double)1/segments;
                }
                B.put(x, supply_X);
                emp_seg--;
            }
            else
            {
                if(emp_seg>1)
                {
                    B = subsidiaryProcess(LBS ,UBS , B, b);
                }
                else
                {
                    x = masterProcess(segments*(EV-AV), segments*del ,B , n, b);
                    if(b == false){
                        Double node1 = (Double)x;
                        String s1 = node1.toString();
                        writer.append(s1 + "[style=filled, fillcolor=\"#5F9EA0\"];" +  '\n');
                    }
                    vol_X = x/segments;
                    M.add(x);
                    AV = AV + vol_X;
                    production += 1.0;
                    consumption += 0.25;
                    emp_seg--;
                }
            }
        }
        while(emp_seg>0)
        {
            M.add(0.0);
            emp_seg--;
            buffer+=1.0/segments;
        }

        for(int i=0;i<M.size();i++){

             
            System.out.print(M.get(i) + " ");
        }

        for(Map.Entry<Double, Double> entry : B.entrySet())
        {
            double c = entry.getKey();
            System.out.println("Key = " + c + "--------- Value = " + entry.getValue());
        }
        System.out.println();
        operations++;
        String s1 = null;
        if(b == false){
            Double node1 = (Double)AV;
            s1 = node1.toString();
        }
        
        for(int i=0;i<M.size();i++){
            if(b==false){
                Double node2 = (Double)M.get(i);
        	String s2 = node2.toString();
        	Double node3 = (Double)1.0/segments;
                String s3 = node3.toString();
                writer.append(s1 + " [style=filled, fillcolor=white];" + '\n');
                String s = s2 + "->" + s1 + "[label=" + s3 + "];";
                writer.append(s+'\n');
            }
        }

        return AV;
    }
     
    public TreeMap<Double,Double> subsidiaryProcess(double LBS , double UBS ,  TreeMap<Double,Double> B, boolean b) throws IOException
    {
        for(Map.Entry<Double, Double> entry : B.entrySet())
        {
            double c = entry.getKey();
            double i=1;
            while((c*i/segments)*(1.0/segments)<LBS)
            {
                i=i+1;
            }
            double supply_C = entry.getValue();
             
            if(i/segments<=supply_C)
            {
                operations++;
                entry.setValue(supply_C - i/segments);
                if(c == 1)
                {
                    reactant += i/segments;
                }
                else
                {  	
                    consumption += i/segments;
                }
                B.put(c*i/segments, 1.0);
                if(b == false){
                    Double node1 = (Double)c*i/segments;
                    Double node2 = (Double)c;
                    Double node3 = (Double)i/segments;
                    String s1 = node1.toString();
                    String s2 = node2.toString();
                    String s3 = node3.toString();
                    if(c == 1){
                        writer.append(s2 + "[style=filled, fillcolor=yellow];" +  '\n');
                    }
                    String s = s2 + "->" + s1 + "[label=" + s3 + "];";
                    writer.append(s+'\n');
                    node2 = (Double)0.0;
                    s2 = node2.toString();
                    writer.append(s2 + "[style=filled, fillcolor=green];" +  '\n');
                    node3 = (Double)(1-i/segments);
                    s3 = node3.toString();
                    s = s2 + "->" + s1 + "[label=" + s3 + "];";
                    writer.append(s+'\n');
                }
                
                production++;
                buffer += 1.0 - i/segments;
                if(c*(i/segments)*(1.0/segments) > UBS)
                {
                    B = subsidiaryProcess(LBS , UBS , B, b);
                }
                break;
            }
        }
        return B;
    }
 
    public double getReactant()
    {
        return reactant;
    }
 
    public double getWaste()
    {
        waste = production-consumption;
        return waste;
    }
 
    public double getOperations()
    {
        return operations;
    }
     
    public double getBuffer()
    {
        return buffer;
    }
     
    public String start_graph() {
          return "digraph G {";
    }
     
    public String end_graph() {
          return "}";
    }
     
    public void dotToPng(String fileName ,String imageName) throws IOException{
            
            final String imageType = "png";
            final String sourceDotFilePath = "./image/Vospa/" + fileName;
            final String outputImageFilePath = "./image/Vospa/" + imageName;
            
            char os = System.getProperty("os.name").charAt(0);
            
            if(os == 'L'){
                String graphvizDotUtilityPath = "/usr/bin/dot";
                List commandList = new ArrayList();
                commandList.add(graphvizDotUtilityPath);
                commandList.add("-T" + imageType);
                commandList.add("-Gsize=9,15! -Gdpi=100");
                commandList.add(sourceDotFilePath);
                commandList.add("-o" + outputImageFilePath);

                // java.lang.Process
                // java.lang.ProcessBuilder
                try {
                                Process process = new ProcessBuilder(commandList).start();


                        } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
            }
            
            else if(os == 'W'){
                try { 
                    Process p=Runtime.getRuntime().exec("cmd /c dot -Tpng " + sourceDotFilePath + " -o " +  outputImageFilePath); 
                    p.waitFor(); 
                    BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
                    String line=reader.readLine(); 
                    while(line!=null) 
                    { 
                    System.out.println(line); 
                    line=reader.readLine(); 
                    } 

                } catch(IOException e1) {} 
                catch(InterruptedException e2) {}
            }


    }
    
    public void runVospa(String s_c_t, String s_del, String s_n, String stat_file) throws IOException
    {
        vospa obj = new vospa();
        double c_t, del;
        int n;
        String folder_name = "./image/Vospa";
        //String path = "../BTP/src/Stats/";
       
        String file_name = "VospaDot.dot";
        obj.f = new File(folder_name + "/" + file_name);
        obj.f.createNewFile();
        obj.writer = new FileWriter(folder_name + "/" + file_name, false);
        obj.writer.append(obj.start_graph() + '\n');
        obj.writer.append("rankdir = BT;" + '\n');
        obj.writer.append("graph [label= <<u>VOSPA</u>> , labelloc=t, fontsize=30];" + '\n');
        obj.writer.append("compound=true;" + '\n');
        //obj.writer.append("splines=line;" + '\n');
//        obj.writer.append("ratio=\"fill\";" + '\n');
//        obj.writer.append("size=\"11.7,11.7!\"" + '\n');
        //System.out.print("Enter Target Concentration = ");
        c_t = obj.inputConversion(s_c_t);
        //System.out.print("Enter Tolerance = ");
        del = obj.inputConversion(s_del);
        //System.out.print("Enter Number of segments = ");
        n = (int)obj.inputConversion(s_n);
        double AV = obj.Vospa(c_t, del, n, false);
        Double node1 = (Double)AV;
        String s1 = node1.toString();
        obj.writer.append(s1 + " [style=filled, fillcolor=\"#FBCEB1\"];" + '\n');
        for(Map.Entry<Double, Double> entry : obj.B.entrySet())
        {	
        	double x = entry.getKey();
        	double supply_C = entry.getValue();
        	if(supply_C > 0 && supply_C != obj.inf){
        		node1 = (Double)x;
                s1 = node1.toString();
                obj.writer.append(s1 + "[style=filled, fillcolor=\"#5F9EA0\"];" +  '\n');
        	}
        }
        obj.writer.append(obj.end_graph());
        obj.writer.close();
//        obj.writer = new FileWriter("./stat/Vospa/" + stat_file, true);
//        
//        //System.out.println("AV = " + AV);
//        obj.writer.append("" + obj.getReactant() + '\n');
//        obj.writer.append("" + obj.getBuffer() + '\n');
//        obj.writer.append("" + obj.getWaste() + '\n');
//        obj.writer.append("" + (int)obj.getOperations() + '\n');
//        obj.writer.close();

        File statFile = new File(stat_file);
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
        fw.append(serialNo + "\t" + s_c_t + "\t" + obj.getReactant() + "\t" + obj.getBuffer() + "\t"  + obj.getWaste() + "\t" + (int)obj.getOperations() + "\n");
        //System.out.println(reactant + " " + waste + " " + operation);
        fw.close();
        dotToPng("VospaDot.dot", "VospaDot.png");
        
    }
    
    public void runVospaForStat(String s_c_t, Double s_del, String s_n, FileWriter fw, DefaultTableModel tableModel, int serialNo) throws IOException
    {
        vospa obj = new vospa();
        double c_t, del;
        int n;
        boolean b = true;
        String stat_file = "Vospa_Stat.txt";
        File statF = new File("./stat/Vospa/" + stat_file);
        

        c_t = obj.inputConversion(s_c_t);
        //System.out.print("Enter Tolerance = ");
        del = s_del.floatValue();
        //del = obj.inputConversion(s_del);
        //System.out.print("Enter Number of segments = ");
        n = (int)obj.inputConversion(s_n);
        double AV = obj.Vospa(c_t, del, n, b);
        Double node1 = (Double)AV;

        for(Map.Entry<Double, Double> entry : obj.B.entrySet())
        {	
        	double x = entry.getKey();
        	double supply_C = entry.getValue();
        	if(supply_C > 0 && supply_C != obj.inf){
        		node1 = (Double)x;
                }
        }
        String lastLine  = serialNo + "\t" + serialNo + "\t" + ((Double)obj.getReactant()).floatValue() + "\t" + ((Double)obj.getBuffer()).floatValue() + "\t"  + ((Double)obj.getWaste()).floatValue() + "\t" + (int)obj.getOperations() ;
        fw.append(lastLine + "\n");
        String[] sArray = lastLine.split("\t");
            Object[] ob = new Object[sArray.length];
            for(int i=0; i< sArray.length; i++){
                if( i == 0 || i == 1 || i == 5){
                    ob[i] = Integer.parseInt(sArray[i]) ;
                }
                else{
                    ob[i] = Double.parseDouble(sArray[i]) ;
                }
            }
            
            
            tableModel.addRow(ob);
        
    }
    
    public void runVospa(String s_del, String s_n, FileWriter fw, DefaultTableModel tableModel){
        double den = Math.pow(2, inputConversion(s_del));
        for(int i =1 ; i < den ;i ++){
            try {
                runVospaForStat(((Double)(i/den)).toString(), ((Double)(1/(2*den))), s_n, fw, tableModel, i);
            } catch (IOException ex) {
                Logger.getLogger(BitScanning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
