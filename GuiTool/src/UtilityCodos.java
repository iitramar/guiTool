import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

class node{
	String data;
	node left;
	node right;
	
	public node(String data){
		this.data = data;
		this.left = null;
		this.right = null;
	}
}

class Tree{
	node root;
	
	public Tree(){
		root = null;
	}
	
	public Tree(String data){
		root = new node(data);
	}
	
	public void printTree(node n){
		if(n==null){
			return;
		}
		System.out.print(n.data + "  ");
		printTree(n.left);
		printTree(n.right);
	}
}

class dotUtility{
	public Tree matrixToTree(int[][] recipeMatrix, int row, Vector<String> compound){
		Tree tree = new Tree();
		int col = recipeMatrix[0].length;
		Vector< Vector < node > > matrixOfNodes = new Vector < Vector < node > >(col);
		for(int i=0;i<col;i++){
			matrixOfNodes.add(i, new Vector<node>());
		}
		for(int j = col-1; j>=0; j--){
			for(int i=0; i<row; i++){
				if(recipeMatrix[i][j] == 1){
					node n = new node(compound.get(i));
					matrixOfNodes.get(j).add(n);
				}
			}
			int size = matrixOfNodes.get(j).size();
			if(size > 0){
				for(int k = 0;k<size;k=k+2){
					node l = matrixOfNodes.get(j).get(k);
					node r = matrixOfNodes.get(j).get(k+1);
					String s = l.data + r.data;
					node p = new node(s);
					p.left = l;
					p.right = r;
					if(j==0){
						tree.root = p;
					}
					else{
						matrixOfNodes.get(j-1).add(p);
					}
				}
			}
		}
		return tree;
	}
}

class TreeToDot{
	FileWriter fw = null;
	void createDotFile() throws IOException{
		File dotFile = new File("./image/Codos/CodosDot.dot");
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
	
	void fillTreeDetail(node n, int i) throws IOException{
		if(n == null){
			return;
		}
		if(n.left!=null){
            String s = n.left.data;
            int l = 2*i;
			fw.append(l + "[label=" + s + "];" + "\n");
			fw.append(l + "->" + i + "\n");
		}
		if(n.right!=null){
            String s = n.right.data;
            int r = 2*i+1;
			fw.append(r + "[label=" + s + "];" + "\n");
			fw.append(r + "->" + i + "\n");
		}
		if(n.left == null && n.right == null){
			fw.append(i + "[style=filled, fillcolor=yellow];" + "\n");
		}
		fillTreeDetail(n.left,2*i);
		fillTreeDetail(n.right,2*i+1);
	}
	
	void remiaGraphStart() throws IOException{
		fw.append("digraph G {\n");
		fw.append("rankdir = BT;" + '\n');
	}
	
	void remiaGraphEnd() throws IOException{
		fw.append("}");
		fw.close();
	}
	
	void writeToDot(Tree t,int i) throws IOException{
		String s = t.root.data;
		fw.append(i + "[style=filled, fillcolor= \"#FBCEB1\", label=" +s + "];" + "\n");
		fillTreeDetail(t.root,i);
	}
	
	public void dotToPng() throws IOException{
    	final String graphvizDotUtilityPath = "/usr/bin/dot";
    	final String imageType = "png";
    	final String sourceDotFilePath = "./image/Codos/CodosDot.dot";
    	final String outputImageFilePath = "./image/Codos/CodosDot.png";

    	final List commandList = new ArrayList();
    	commandList.add(graphvizDotUtilityPath);
    	commandList.add("-T" + imageType);
    	commandList.add(sourceDotFilePath);
    	commandList.add("-o" + outputImageFilePath);
    	
    	try {
			Process process = new ProcessBuilder(commandList).start();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	
}

class Forest{
	
	Vector<Tree> setOfTree = new Vector<Tree>();
	
	public void makeTree(node n, int i, int N, Vector<String> compound, Vector<String> compound1){
		if(i<N){
			return;
		}
		String s = compound.get(i);
		int first = (int)s.charAt(0)-48;
		int second = (int)s.charAt(1)-48;
		node l = new node(compound1.get(first));
		node r = new node(compound1.get(second));
		n.left = l;
		n.right = r;
		makeTree(n.left,first,N, compound, compound1);
		makeTree(n.right,second,N, compound, compound1);
	}
	
	public void buildForest(Vector<String> compound, Vector<String> compound1, int N){
		Tree tree;
		for(int i=N;i<compound1.size();i++){
			tree = new Tree(compound1.get(i));
			setOfTree.add(tree);
			makeTree(tree.root,i,N, compound, compound1);
		}
	}
	
	public void printForest(){
		for(int i=0 ; i < setOfTree.size() ; i++){
			setOfTree.get(i).printTree(setOfTree.get(i).root);
			System.out.println("-----------------------");
			System.out.println();
		}
	}
	
	public boolean isLeaf(node n){
		return (n.left==null && n.right==null );
	}
	
	public int search(String s, Vector<String> compound1, int N){
		int j=0 ;
		for(int i =N ; i<compound1.size();i++){
			if(compound1.get(i) == s){
				j = i ;
			}
		}
		return j;
	}
	
	public void expandTreeFrom(node n , Vector<String> compound1 , int N ){
		
		if(isLeaf(n)){
			return ;
		}
		
		if(n.left != null){
			if(isLeaf(n.left)){
				String leftChildData = n.left.data ;
				int li = search(leftChildData, compound1, N);
				
				if(li >= N){
					for(int i=0 ; i < setOfTree.size() ; i++){
						if(setOfTree.get(i).root.data == leftChildData){
							Tree temp = setOfTree.get(i);
							n.left = temp.root ;
						}
					}
				}
			}
		}
		
		if(n.right != null){
			if(isLeaf(n.right)){
				String rightChildData = n.right.data ;
				int ri = search(rightChildData, compound1, N);
				
				if(ri >= N){
					for(int i=0 ; i < setOfTree.size() ; i++){
						if(setOfTree.get(i).root.data == rightChildData){
							Tree temp = setOfTree.get(i);
							n.right = temp.root ;
						}
					}
				}
			}
		}
		
		expandTreeFrom(n.left, compound1, N);
		expandTreeFrom(n.right, compound1, N);
		
	}
	
	public void expandTree(Tree tree , Vector<String> compound1 , int N){
		
		for(int i=0 ; i < setOfTree.size() ; i++){
			expandTreeFrom(setOfTree.get(i).root, compound1, N);
		}
		
		expandTreeFrom(tree.root, compound1, N);
	}
	
}

class Stats{
	int waste;
	int operation;
	int reactant;
	
	TreeMap<String,Integer> count;
	
	
	public Stats(){
		this.waste = 0;
		this.reactant = 0;
		this.operation = 0;
		count = new TreeMap<String,Integer>();
	}
	
	public String search(String s, Vector<String> compound1, int N){
		for(int i=0;i<N;i++){
			if(compound1.get(i) == s){
				return "Reactant";
			}
		}
		for(int i =N ; i<compound1.size();i++){
			if(compound1.get(i) == s){
				return "Compound";
			}
		}
		return "Intermediate";
	}
	
	public void fillCount(node n, Vector<String> compound1, int N){
		if(n==null){
			return;
		}
		String data = n.data;
		if(search(data, compound1, N) != "Reactant"){
			if(count.containsKey(data)){
				if(count.get(data)>0){
					count.put(data, count.get(data)-1);
					return;
				}
				else{
					count.put(data, 1);
					operation++;
					if(search(n.left.data, compound1, N) == "Reactant"){
						reactant++;
					}
					if(search(n.right.data, compound1, N) == "Reactant"){
						reactant++;
					}
				}
			}
			else{
				count.put(data, 1);
				operation++;
				if(search(n.left.data, compound1, N) == "Reactant"){
					reactant++;
				}
				if(search(n.right.data, compound1, N) == "Reactant"){
					reactant++;
				}
			}
		}
		fillCount(n.left, compound1, N);
		fillCount(n.right, compound1, N);
	}
	
	public void getWaste(){
		for(Map.Entry<String, Integer> entry : count.entrySet())
        {
            waste += entry.getValue();
            System.out.println("Key = " + entry.getKey() + "--------- Value = " + entry.getValue());
        }
	}
	
	public void generateStats(Tree t, Vector<String> compound, Vector<String> compound1, int N, String s_n, String fileName) throws IOException{
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
		fillCount(t.root, compound1, N);
		getWaste();
		fw.append(serialNo + "\t" + s_n + "\t" + reactant + "\t" + "NA" + "\t"  + waste + "\t" + operation + "\n");
		System.out.println(reactant + " " + waste + " " + operation);
		fw.close();
	}
}


