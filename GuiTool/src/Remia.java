import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;
import java.io.FileWriter;

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

	public void writeStat() throws IOException{
		File f = new File("../../Stat.txt");
		FileWriter fw;
		try{
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			fw = new FileWriter(f);
		}
		
		fw.append(reactant + " " + buffer + " " + waste + " " + operations);
		fw.close();
	}
	
	public void runRemia( String s_n, String s_c_t, String s_del){
		
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
			ttd.createDotFile("DotFile.dot");
			ttd.remiaGraphStart();
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
			ttd.dotToPng("DotFile.dot" ,"Image.png");

			ttd.createDotFile("RemiaHeapDotFile.dot");
			ttd.remiaGraphStart();
			for(int i=0;i<forest.setOfTreeRemia.size();i++){
				System.out.println("I = " + i );
				ttd.writeToDot(forest.setOfTreeRemia.get(i),j , den);
				j = j+100;
			}

			ttd.remiaGraphEnd();
			ttd.dotToPng("RemiaHeapDotFile.dot" , "RemiaHeapImage.png");


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		calculateStat();
		System.out.println("reactant = " + reactant + " buffer = " + buffer + " waste = " + waste + " operations = " + operations);

		try{
			writeStat();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(den);

	}

	public static void main(String args[]){
		Remia obj = new Remia();
		obj.runRemia("2", "607 503", "11");
	}
}