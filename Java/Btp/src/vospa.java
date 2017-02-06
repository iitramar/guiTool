import java.util.*;
import java.io.*;
class vospa 
{
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

    double inf = Double.POSITIVE_INFINITY;
    double reactant = 0, waste = 0, operations = 0, consumption = 0, production = 0, buffer=0;
    int segments ;
    File f;
    FileWriter writer = null;
    int sub = 0;
    int z=1;
    TreeMap<Double,Double> B = new TreeMap<Double,Double>();
    public double Vospa(double c_t, double del, int n) throws IOException
    {
        segments = n;
        
        B.put(1.0, inf);
        return masterProcess(c_t, del , B , n);
    }
     
    public double masterProcess(double EV, double del , TreeMap<Double,Double> B , int n) throws IOException
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
                    B = subsidiaryProcess(LBS ,UBS , B);
                }
                else
                {
                    x = masterProcess(segments*(EV-AV), segments*del ,B , n);
                    Double node1 = (Double)x;
                    String s1 = node1.toString();
                    writer.append(s1 + "[style=filled, fillcolor=\"#5F9EA0\"];" +  '\n');
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
        Double node1 = (Double)AV;
        String s1 = node1.toString();
        for(int i=0;i<M.size();i++){
        	Double node2 = (Double)M.get(i);
        	String s2 = node2.toString();
        	Double node3 = (Double)1.0/segments;
            String s3 = node3.toString();
            writer.append(s1 + " [style=filled, fillcolor=white];" + '\n');
            String s = s2 + "->" + s1 + "[label=" + s3 + "];";
            writer.append(s+'\n');
        }

        return AV;
    }
     
    public TreeMap<Double,Double> subsidiaryProcess(double LBS , double UBS ,  TreeMap<Double,Double> B) throws IOException
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
                production++;
                buffer += 1.0 - i/segments;
                if(c*(i/segments)*(1.0/segments) > UBS)
                {
                    B = subsidiaryProcess(LBS , UBS , B);
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
     
    public void dotToPng() throws IOException{
    	final String graphvizDotUtilityPath = "/usr/bin/dot";
    	final String imageType = "png"; // "png", "gif", or other supported type
    	final String sourceDotFilePath = "./Stats/graph/vospaDot.dot";
    	final String outputImageFilePath = "./Stats/graph/vospaGraph.png";

    	final List commandList = new ArrayList();
    	commandList.add(graphvizDotUtilityPath);
    	commandList.add("-T" + imageType);
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
    
    public void runVospa(String s_c_t, String s_del, String s_n) throws IOException
    {
        vospa obj = new vospa();
        double c_t, del;
        int n;
        String folder_name = "graph";
        String path = "./Stats/";
        File theDir = new File(path + folder_name);
        if(!theDir.exists()){
            theDir.mkdir();
        }
        String file_name = "vospaDot.dot";
        String stat_file = "vospastat.txt";
        File statF = new File(path + folder_name + "/" + stat_file);
        
        statF.createNewFile();
        obj.f = new File(path + folder_name + "/" + file_name);
        obj.f.createNewFile();
        obj.writer = new FileWriter(path + folder_name + "/" + file_name, false);
        obj.writer.append(obj.start_graph() + '\n');
        obj.writer.append("rankdir = BT;" + '\n');
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
        double AV = obj.Vospa(c_t, del, n);
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
        obj.writer = new FileWriter(path + folder_name + "/" + stat_file, false);
        
        //System.out.println("AV = " + AV);
        obj.writer.append("" + obj.getReactant() + '\n');
        obj.writer.append("" + obj.getBuffer() + '\n');
        obj.writer.append("" + obj.getWaste() + '\n');
        obj.writer.append("" + (int)obj.getOperations() + '\n');
        obj.writer.close();
        dotToPng();
        
    }
}
