import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class codos {
	
	static int ptr;
	
	public void updateRecipeMatrix(int[][] recipeMatrix, int[] rect, Vector<String> compound){
		int x = rect[1];
		int y = rect[2];
		int p = rect[3];
		int q = rect[4];
		if(p>=1){
			recipeMatrix[ptr][p-1] = 1;
		}
		if(q>=1){
			recipeMatrix[ptr][q-1] = 1;
		}
		recipeMatrix[x][p] = 0;
		recipeMatrix[y][p] = 0;
		recipeMatrix[x][q] = 0;
		recipeMatrix[y][q] = 0;
		ptr = ptr+1;
		compound.add(""+x+""+y);
	}
	
	public int binary(double num, double den){
		return (den>num?0:1);
	}
	
	public void constructRecipeMatrix(int T[], int[][] recipeMatrix){
		int N = T.length;
		int d = recipeMatrix[0].length;
		double k;
		for(int i=0;i<N;i++){
			k = T[i];
			for(int j=0;j<d;j++){
				recipeMatrix[i][j] = binary(k,Math.pow(2, d-j-1));
				if(recipeMatrix[i][j] == 1){
					k = k - Math.pow(2, d-j-1);
				}
			}
		}
	}
	
	public void printRecipeMatrix(int[][] recipeMatrix){
		int d = recipeMatrix[0].length;
		for(int i=0;i<ptr;i++){
			for(int j=0;j<d;j++){
				System.out.print(recipeMatrix[i][j]);
			}
			System.out.println();
		}
	}
	
	public boolean getRecatngle(int[][] recipeMatrix, int[] rect, int N){
		int d = recipeMatrix[0].length;
		int prev = -1;
		boolean b = false;
		for(int i=0;i<N;i++){
			for(int j=i+1;j<ptr;j++){
				for(int k=d-1;k>=0;k--){
					if(recipeMatrix[i][k]==1 && recipeMatrix[j][k]==1){
						if(prev == -1){
							prev = k;
						}
						else{
							if(rect[1] != -1 && rect[1]<N && rect[2] != -1 && rect[2]<N && j>=N){
								continue;
							}
							else if(rect[1] != -1 && rect[1]<N && rect[2] != -1 && rect[2]<N && j<N){
								if(rect[3]+rect[4]<prev+k){
									rect[1] = i;
									rect[2] = j;
									rect[3] = prev;
									rect[4] = k;
									b = true;
								}
							}
							else if(rect[1] == -1 && rect[2] == -1){
								rect[1] = i;
								rect[2] = j;
								rect[3] = prev;
								rect[4] = k;
								b = true;
							}
						}
					}
				}
				prev = -1;
			}
		}
		return b;
	}
	
	public void setRectangle(int[] rect){
		rect[1] = -1;
		rect[2] = -1;
		rect[3] = -1;
		rect[4] = -1;
	}
	
	public void codos(int[] T, int[][] recipeMatrix, Vector<String> compound){
		int N = T.length;
		ptr = N;
		constructRecipeMatrix(T,recipeMatrix);
		int rect[] = new int[5];
		setRectangle(rect);
		while(getRecatngle(recipeMatrix, rect, N)){
			updateRecipeMatrix(recipeMatrix, rect, compound);
			setRectangle(rect);
		}
		System.out.println("Final Recipe Matrix:");
		printRecipeMatrix(recipeMatrix);
	}
	
	public int inputConversion(String s_c_t){
		int res = 0;
		for(int i=0;i<s_c_t.length();i++){
			res = res*10 + (int)s_c_t.charAt(i)-48;
		}
		return res;
	}
	
	public void convertArray(String s_n, int T[]){
		String s = "";
		int j = 0;
		for(int i=0;i<s_n.length();i++){
			if(s_n.charAt(i)!=':' && s_n.charAt(i)!=' '){
				s = s+s_n.charAt(i);
			}
			else{
				int res = inputConversion(s);
				T[j] = res;
				j++;
				s = "";
			}
		}
		int res = inputConversion(s);
		T[j] = res;
		j++;
		
	}
	
	public void runCodos(String s_c_t, String s_del, String s_n, String fileName) throws IOException{
		int N,d;
		Vector<String> compound = new Vector<String>();
		Vector<String> compound1 = new Vector<String>();
		Scanner sc = new Scanner(System.in);
		//System.out.println("Enter number of Reactant types");
		N = inputConversion(s_c_t);
		//System.out.println("Enter Precision level");
		d = inputConversion(s_del);
		int T[] = new int[N];
		//System.out.println("Enter Target Concentration(space separated vector)");
		convertArray(s_n, T);
		for(int i=0 ;i<N ; i++){
			compound.add(""+(i+1));
		}
		int[][] recipeMatrix = new int[100][d];
		codos cod = new codos();
		cod.codos(T,recipeMatrix, compound);
		
		for(int i=0;i<N;i++){
			compound1.add("R" + (i+1));
		}
		
		for(int i=N; i<compound.size();i++){
			System.out.print(compound.elementAt(i)+ " ");
			String s = compound.get(i);
			int first = (int)s.charAt(0)-48;
			int second = (int)s.charAt(1)-48;
			String s1 = compound1.get(first) + compound1.get(second);
			compound1.add(s1);
			System.out.println(compound1.elementAt(i)+ " ");
		}
		System.out.println();
		dotUtility du = new dotUtility();
		Tree tree = du.matrixToTree(recipeMatrix, ptr, compound1);
		tree.printTree(tree.root);
		Forest forest = new Forest();
		forest.buildForest(compound,compound1,N);
		TreeToDot ttd = new TreeToDot();
		try {
			ttd.createDotFile();
			ttd.remiaGraphStart();
			int j=1;
//			for(int i=0;i<forest.setOfTree.size();i++){
//				ttd.writeToDot(forest.setOfTree.get(i),j);
//				j = j+100;
//			}
			forest.expandTree(tree, compound1, N);
			ttd.writeToDot(tree, j);
			ttd.remiaGraphEnd();
			ttd.dotToPng();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stats stats = new Stats();
                
		stats.generateStats(tree, compound, compound1, N, s_n, fileName);
	}
        
        int serialNo = 1 ;
        public void runCodosForStat(String s_c_t, String s_del, String s_n, FileWriter fw, DefaultTableModel tableModel) throws IOException{
		int N,d;
		Vector<String> compound = new Vector<String>();
		Vector<String> compound1 = new Vector<String>();
		Scanner sc = new Scanner(System.in);
		N = inputConversion(s_c_t);
		d = inputConversion(s_del);
		int T[] = new int[N];
		convertArray(s_n, T);
		for(int i=0 ;i<N ; i++){
			compound.add(""+(i+1));
		}
		int[][] recipeMatrix = new int[100][d];
		codos cod = new codos();
		cod.codos(T,recipeMatrix, compound);
		
		for(int i=0;i<N;i++){
			compound1.add("R" + (i+1));
		}
		
		for(int i=N; i<compound.size();i++){
			String s = compound.get(i);
			int first = (int)s.charAt(0)-48;
			int second = (int)s.charAt(1)-48;
			String s1 = compound1.get(first) + compound1.get(second);
			compound1.add(s1);
		}
		dotUtility du = new dotUtility();
		Tree tree = du.matrixToTree(recipeMatrix, ptr, compound1);
		Forest forest = new Forest();
		forest.buildForest(compound,compound1,N);
                forest.expandTree(tree, compound1, N);
		Stats stats = new Stats();
                
                stats.fillCount(tree.root, compound1, N);
		stats.getWaste();
                String lastLine  = serialNo + "\t" + s_n + "\t" + stats.reactant + "\t" + "NA" + "\t" + stats.waste + "\t" + stats.operation ;
		serialNo ++ ;
                
                fw.append(lastLine + "\n");
                
                String[] sArray = lastLine.split("\t");
                Object[] ob = new Object[sArray.length];
                for(int i=0; i< sArray.length; i++){
                    if(i == 1 || i == 3){
                        ob[i] = sArray[i] ;
                    }
                    else{
                        ob[i] = Integer.parseInt(sArray[i]) ;
                    }
                }
            
                tableModel.addRow(ob);
                
		
	}

        public void findComb(int arr[] , int current , int end  ,int sum, String s_c_t, String s_del, FileWriter fw, DefaultTableModel tableModel){
		if(end - current + 1 > sum){
			return ;
		}

		if(current == end){
                        String s = "";
			//count ++ ;
			arr[current] = sum;
                        int i;
			for(i =0 ; i < arr.length-1;i ++){
				System.out.print(arr[i] + " ");
                                s = s+arr[i]+":";
			}
                        s = s+arr[i];
                    try {
                        runCodosForStat(s_c_t,s_del, s, fw, tableModel);
                    } catch (IOException ex) {
                        Logger.getLogger(codos.class.getName()).log(Level.SEVERE, null, ex);
                    }
			System.out.println();
			return ;
		}

		for(int i=1 ; i <= (sum - end + current) ;i ++){
			arr[current] = i ;
			findComb(arr , current + 1 , end , sum - i, s_c_t, s_del, fw, tableModel);
		}
	}
        
	public void runCodos(String s_c_t, String s_del, FileWriter fw, DefaultTableModel tableModel){
            int N,d,serialNo = 1;
            N = inputConversion(s_c_t);
            d = inputConversion(s_del);
            int s = (int)Math.pow(2, d);
            int[] arr = new int[N];
            findComb(arr , 0 , N-1, s, s_c_t, s_del, fw, tableModel);
	}
}
