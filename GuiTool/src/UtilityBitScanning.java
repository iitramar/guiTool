
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author iitramar
 */
class nodeBS
{
	double data ;
	nodeBS left ;
	nodeBS right;
	double utilized ;
	
	public nodeBS(double data , nodeBS l , nodeBS r)
	{
		this.data = data ;
		this.left = l ;
		this.right = r ;
		this.utilized = 0 ;
	}
}

class TreeBS
{
	nodeBS root;
	public TreeBS()
	{
		root = null ;
	}
	
	public TreeBS(double data)
	{
		root = new nodeBS(data,null , null);
	}
	
	void mix(double data , double ut)
	{
		nodeBS reactant = new nodeBS(data , null , null);
		reactant.utilized = ut ;
		root.utilized = 1 - ut ;
		nodeBS n = new nodeBS((data*ut + root.data*root.utilized) , root ,reactant);
		root = n ;
		
	}
	
	void printTreeBS(nodeBS n)
	{
		if(n == null)
		{
			return ;
		}
		printTreeBS(n.left);
		printTreeBS(n.right);
		System.out.print(n.data + " ");
	}
        
        int countValue(nodeBS n, double val){
            
            if(n == null){
                return 0 ;
            }
            
            int haveSameVal = 0 ;
            
            if(n.data == val){
                haveSameVal = 1 ;
            }
            
            return haveSameVal + countValue(n.left, val) + countValue(n.right, val);
            
            
        }
}

class TreeBSToDot{
	FileWriter fw = null;
	void createDotFile(String fileName) throws IOException{
		File dotFile = new File("./image/BitScanning/" + fileName);
		try {
			dotFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			fw = new FileWriter(dotFile);
		}
	}
	
	String fractionToRatio(double frac , double den){
		double num = frac * den ;
		
		Double n1 = (Double)num ;
		Double d1 = (Double)den ;

		Integer n = n1.intValue();
		Integer d = d1.intValue();

		return (" \" " + Integer.toString(n) + "/" + Integer.toString(d) + " \" ") ;
	}

	void fillTreeBSDetail(nodeBS n, int i , double den) throws IOException{
		if(n == null){
			return;
		}
		if(n.left!=null){
                    double d = n.left.data;
                    String s = fractionToRatio(d , den);
                    int l = 2*i;
                    fw.append(l + "[label=" + s + "];" + "\n");
                    fw.append(l + "->" + i + "\n");
		}
		if(n.right!=null){
                    double d = n.right.data;
                    String s = fractionToRatio(d , den);
                    int r = 2*i+1;
                    fw.append(r + "[label=" + s + "];" + "\n");
                    fw.append(r + "->" + i + "\n");
		}
		if(n.left == null && n.right == null){
                    fw.append(i + "[style=filled, fillcolor=yellow];" + "\n");
		}
		fillTreeBSDetail(n.left,2*i, den);
		fillTreeBSDetail(n.right,2*i+1, den);
	}
	
	void bsGraphStart() throws IOException{
		fw.append("digraph G { \n");
		fw.append("rankdir = BT;" + '\n');
                fw.append("graph [label= <<u>Bit-Scanning</u>> , labelloc=t, fontsize=30];" + '\n');
	}
	
	void bsGraphEnd() throws IOException{
		fw.append("}");
		fw.close();
	}
	
	void writeToDot(TreeBS t,int i , double den) throws IOException{
		double d = t.root.data;
		String s = fractionToRatio(d , den);
		fw.append(i + "[label=" +s + "];" + "\n");
		fillTreeBSDetail(t.root,i,den);
	}
	
	public void dotToPng(String fileName ,String imageName) throws IOException{
            
            final String imageType = "png";
            final String sourceDotFilePath = "./image/BitScanning/" + fileName;
            final String outputImageFilePath = "./image/BitScanning/" + imageName;
            
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

	
}
