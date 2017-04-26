import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class Remia {

	int reactant , buffer, waste, operations ;
	ForestRemia forest , forestOfTarget ;
	
	public double lcv(double conc){
		double x = 0.5 ;
		while(conc < x){
			x = x/2 ;
		}
		
		return 2*x ;
	}
	
	public TreeRemia buildMixTreeRemia(double target,double den ){
		
		TreeRemia tree = new TreeRemia(target);
		nodeRemia temp = tree.root ;
		
		int n = (int) (temp.data * den) ;
		while((n & (n-1)) != 0){
			temp.left = new nodeRemia(lcv(temp.data));
			temp.right = new nodeRemia(2*temp.data - temp.left.data);
			temp = temp.right ;
			n = (int) (temp.data * den) ;
			
		}
		
		return tree;
	}
	
	public void addLeafToHeap(MinHeap mh, nodeRemia n){
		if(n == null){
			return ;
		}
		if(n.left == null && n.right == null){
			mh.insert(n.data);
		}
		addLeafToHeap(mh, n.left);
		addLeafToHeap(mh, n.right);
		
	}
	
	public void buildEDTForest(TreeRemia tree, HashMap<Double, Integer> hm){
		MinHeap mh = new MinHeap(100);
		addLeafToHeap(mh, tree.root);
		System.out.println("before---------");
		mh.print();
		
		while(mh.getSize() > 0){
			
			double x = mh.remove();
			System.out.println("remove---------" + x);
			mh.print();
			
			if(hm.containsKey(x)){
				hm.put(x, hm.get(x)+1);
			}
			else{
				hm.put(x, 1);
			}
			
			
			if(x != 1.0){
				
				if(mh.getSize() > 0){
					double y = mh.remove();
					System.out.println("remove---------" + y);
					mh.print();
					
					if(hm.containsKey(y)){
						hm.put(y, hm.get(y)+1);
					}
					else{
						hm.put(y, 1);
					}
					if(x != y){
						mh.insert(y);
						System.out.println("insert---------" + y);
						mh.print();
						hm.put(y, hm.get(y)-1);
					}
				}
				
				double z = 2*x ;
				mh.insert(z);
				System.out.println("insert---------" + z);
				mh.print();
				
			}
		
			for(Entry<Double, Integer> m:hm.entrySet()){  
			  System.out.println(m.getKey()+" "+m.getValue());  
			}
			System.out.println();
			
			
		}
		 
		
	}
	
	public double inputConversion(String s_c_t){
		double res = 0;
		for(int i=0;i<s_c_t.length();i++){
			res = res*10 + (int)s_c_t.charAt(i)-48;
		}
		return res;
	}
	
	public void convertArray(String s_n, double T[]){
		String s = "";
		int j = 0;
		for(int i=0;i<s_n.length();i++){
			if(s_n.charAt(i)!=' '){
				s = s+s_n.charAt(i);
			}
			else{
				double res = inputConversion(s);
				T[j] = res;
				j++;
				s = "";
			}
		}
		double res = inputConversion(s);
		T[j] = res;
		j++;
		
	}

	public void calculateStat(){

		reactant = forest.setOfTreeRemia.size() ;
		buffer = forest.brachNodesCount() ;
		operations = buffer + forestOfTarget.brachNodesCount() ;
		waste = (forestOfTarget.brachNodesCount() - forestOfTarget.setOfTreeRemia.size()) + forest.singleChildCount() ;

	}

	public void writeStat(String s_c_t, String fileName) throws IOException{
		File statFile = new File(fileName);
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
        
        public BufferedImage joinBufferedImage(String imgOne, String imgTwo) {
            BufferedImage img1 = null,img2 = null;
            
            try {
                img1 = ImageIO.read(new File("./image/Remia/" + imgOne));
                img2 = ImageIO.read(new File("./image/Remia/" + imgTwo));
            } catch (IOException ex) {
                Logger.getLogger(Remia.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                //do some calculate first
                int offset  = 5;
                int wid = img1.getWidth()+img2.getWidth()+offset;
                int height = Math.max(img1.getHeight(),img2.getHeight())+offset;
                //create a new buffer and draw two image into the new image
                BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = newImage.createGraphics();
                Color oldColor = g2.getColor();
                //fill background
                g2.setPaint(Color.WHITE);
                g2.fillRect(0, 0, wid, height);
                //draw image
                g2.setColor(oldColor);
                g2.drawImage(img1, null, 0, 0);
                g2.drawImage(img2, null, img1.getWidth()+offset, 0);
                g2.dispose();
                System.out.println("Done combining!!");
                return newImage;
        }
	
	public void runRemia( String s_n, String s_c_t, String s_del , String fileName){
		
		double d , N;
		N = inputConversion(s_n);
		double[] num = new double[(int)N];
		//num = inputConversion(s_c_t);
		convertArray(s_c_t, num);
		d = inputConversion(s_del);
		
		double den = Math.pow(2, d);
		
		double target[] = new double[(int)N];
		
		for(int i=0 ; i <N ; i ++){
			target[i] = num[i] / den ;
		}
		
		Remia rmobj = new Remia();
		TreeRemia tobj = new TreeRemia();
		HashMap<Double, Integer> hm = new HashMap<Double, Integer>();
		
		//TreeRemia tree = rmobj.buildMixTreeRemia(target, den);
		
		
//		tobj.printTreeRemia(tree.root);
//		System.out.println();
		TreeRemia tree;
		forestOfTarget = new ForestRemia();
		for(int i=0 ; i <N ; i ++){
			tree = rmobj.buildMixTreeRemia(target[i], den);
			rmobj.buildEDTForest(tree, hm);
			forestOfTarget.setOfTreeRemia.addElement(tree);
		}
		
		
		
		
		
		forest = new ForestRemia();
		forest.buildForest(hm);
		
		forest.printForest();
		
		TreeRemiaToDot ttd = new TreeRemiaToDot();
		try {
			ttd.createDotFile("RemiaDot.dot");
			ttd.remiaGraphStart("Interpolation");
			int j=1;
			// for(int i=0;i<forest.setOfTreeRemia.size();i++){
			// 	System.out.println("I = " + i );
			// 	ttd.writeToDot(forest.setOfTreeRemia.get(i),j);
			// 	j = j+100;
			// }
			
			for(int i =0 ; i < N ; i ++){
				ttd.writeToDot(forestOfTarget.setOfTreeRemia.elementAt(i), j , den);
				j = j+100;
			}
			ttd.remiaGraphEnd();
			ttd.dotToPng("RemiaDot.dot" ,"RemiaDotOld.png");

			ttd.createDotFile("RemiaHeapDot.dot");
			ttd.remiaGraphStart("Exponentiation");
			for(int i=0;i<forest.setOfTreeRemia.size();i++){
				System.out.println("I = " + i );
				ttd.writeToDot(forest.setOfTreeRemia.get(i),j , den);
				j = j+100;
			}

			ttd.remiaGraphEnd();
			ttd.dotToPng("RemiaHeapDot.dot" , "RemiaHeapDot.png");
                        
                        TimeUnit.MILLISECONDS.sleep(1000);
                        ImageIO.write(joinBufferedImage("RemiaDotOld.png", "RemiaHeapDot.png"), "PNG", new File("./image/Remia/RemiaDot.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException ex) {
                Logger.getLogger(Remia.class.getName()).log(Level.SEVERE, null, ex);
            }

		calculateStat();
		System.out.println("reactant = " + reactant + " buffer = " + buffer + " waste = " + waste + " operations = " + operations);

		try{
                        if(N==1){
                            writeStat(s_c_t, "./stat/Remia/" + fileName);
                        }
                        else{
                            writeStat(s_c_t, "./stat/ExRemia/" + fileName);
                        }
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(den);

	}
        
        public void runRemiaForStat( String s_n, String s_c_t, String s_del , FileWriter fw, DefaultTableModel tableModel){
		
		double d , N;
		N = inputConversion(s_n);
		double[] num = new double[(int)N];
		//num = inputConversion(s_c_t);
		convertArray(s_c_t, num);
		d = inputConversion(s_del);
		
		double den = Math.pow(2, d);
		
		double target[] = new double[(int)N];
		
		for(int i=0 ; i <N ; i ++){
			target[i] = num[i] / den ;
		}
		
		Remia rmobj = new Remia();
		TreeRemia tobj = new TreeRemia();
		HashMap<Double, Integer> hm = new HashMap<Double, Integer>();
		
		TreeRemia tree;
		forestOfTarget = new ForestRemia();
		for(int i=0 ; i <N ; i ++){
			tree = rmobj.buildMixTreeRemia(target[i], den);
			rmobj.buildEDTForest(tree, hm);
			forestOfTarget.setOfTreeRemia.addElement(tree);
		}
		
		
		
		
		
		forest = new ForestRemia();
		forest.buildForest(hm);
		
		TreeRemiaToDot ttd = new TreeRemiaToDot();

		calculateStat();
                
                String lastLine  = s_c_t + "\t" + s_c_t + "\t" + reactant + "\t" + buffer + "\t" + waste + "\t" + operations ;
		
            try {
                fw.append(lastLine + "\n");
                
            } catch (IOException ex) {
                Logger.getLogger(Remia.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String[] sArray = lastLine.split("\t");
            Object[] ob = new Object[sArray.length];
            for(int i=0; i< sArray.length; i++){
                ob[i] = Integer.parseInt(sArray[i]) ;
            }
            
            tableModel.addRow(ob);
	}
       
        public void runRemia(String s_del, FileWriter fw, DefaultTableModel tableModel){
            double d = inputConversion(s_del);
            double den = Math.pow(2, d);
		
            for(int i =1 ; i < den ;i ++){
                runRemiaForStat("1", ((Integer)i).toString(), s_del, fw, tableModel);
            }
            
        }

}