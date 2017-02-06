import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		File dotFile = new File("../Codos/codosDot.dot");
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
			fw.append(i + "->" + l + "\n");
		}
		if(n.right!=null){
            String s = n.right.data;
            int r = 2*i+1;
			fw.append(r + "[label=" + s + "];" + "\n");
			fw.append(i + "->" + r + "\n");
		}
		if(n.left == null && n.right == null){
			fw.append(i + "[style=filled, fillcolor=yellow];" + "\n");
		}
		fillTreeDetail(n.left,2*i);
		fillTreeDetail(n.right,2*i+1);
	}
	
	void remiaGraphStart() throws IOException{
		fw.append("digraph G {\n");
	}
	
	void remiaGraphEnd() throws IOException{
		fw.append("}");
		fw.close();
	}
	
	void writeToDot(Tree t,int i) throws IOException{
		String s = t.root.data;
		fw.append(i + "[label=" +s + "];" + "\n");
		fillTreeDetail(t.root,i);
	}
	
	public void dotToPng() throws IOException{
    	final String graphvizDotUtilityPath = "/usr/bin/dot";
    	final String imageType = "png";
    	final String sourceDotFilePath = "../Codos/codosDot.dot";
    	final String outputImageFilePath = "../Codos/codosDot.png";

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
	
}
