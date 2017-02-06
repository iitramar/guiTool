import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

class nodeRemia
{
	double data ;
	nodeRemia left ;
	nodeRemia right ;
	
	public nodeRemia(double data)
	{
		this.data = data ;
		this.left =null ;
		this.right = null ;
	}
}

class TreeRemia
{
	nodeRemia root;
	public TreeRemia()
	{
		root = null ;
	}
	
	public TreeRemia(double data)
	{
		root = new nodeRemia(data);
	}
	
	public void printTreeRemia(nodeRemia root){
		if(root == null){
			return;
		}
		
		System.out.print(root.data + " ");
		printTreeRemia(root.left);
		printTreeRemia(root.right);
	}
	
}

class ForestRemia{
	
	Vector<TreeRemia> setOfTreeRemia = new Vector<TreeRemia>();
	
	public void makeTreeRemia(nodeRemia n, HashMap<Double, Integer> hm){
		
		if(n == null){
			return ;
		}
		
		double childData = n.data/2 ;
		
		if(hm.containsKey(childData)){
			int available = hm.get(childData);
			
			if(available > 0){
				n.left = new nodeRemia(childData);
				available-- ;
				hm.put(childData, hm.get(childData)-1);
				
				if(available > 0 ){
					n.right = new nodeRemia(childData);
					available-- ;
					hm.put(childData, hm.get(childData)-1);
				}
			}
			
			makeTreeRemia(n.left , hm);
			makeTreeRemia(n.right , hm);
		}
		
		
	}
	
	public void buildForest( HashMap<Double, Integer> hm ){
		
		TreeRemia tree;
		
		while(hm.get(1.0) > 0){
			tree = new TreeRemia(1.0);
			setOfTreeRemia.addElement(tree);
			hm.put(1.0, hm.get(1.0)-1);
		}
		
		for(int i=0 ; i < setOfTreeRemia.size() ; i++){
			System.out.println(setOfTreeRemia.get(i).root.data);
			makeTreeRemia(setOfTreeRemia.get(i).root , hm);
		}
		
	}
	
	public void printForest(){
		for(int i=0 ; i < setOfTreeRemia.size() ; i++){
			setOfTreeRemia.get(i).printTreeRemia(setOfTreeRemia.get(i).root);
			System.out.println("-----------------------");
			System.out.println();
		}
	}

	public int brachNodesIn(nodeRemia n){
		if(n.left == null && n.right == null){
			return 0 ;
		}

		int count = 0;
		if(n.left != null){
			count += brachNodesIn(n.left) ;
		}
		if(n.right != null){
			count += brachNodesIn(n.right) ;
		}

		return 1 + count ;
	} 

	public int brachNodesCount(){
		int count = 0 ;
		for(int i =0 ; i <setOfTreeRemia.size() ;i ++){
			count += brachNodesIn(setOfTreeRemia.get(i).root);
		}

		return count ;
	}

	public int singleChildIn(nodeRemia n){

		if(n.left == null && n.right == null){
			return 0 ;
		}

		int count = 0;
		if(n.left != null){
			count += singleChildIn(n.left) ;
		}
		if(n.right != null){
			count += singleChildIn(n.right) ;
		}

		if(n.left != null && n.right != null){
			return count ;
		}
		else{
			return 1 + count ;
		}

	}	

	public int singleChildCount(){
		int count = 0 ;
		for(int i =0 ; i <setOfTreeRemia.size() ;i ++){
			count += singleChildIn(setOfTreeRemia.get(i).root);
		}
		return count ;
	}

	
}

class MinHeap
{
    private double[] Heap;
    private int size;
    private int maxsize;
    
    private static final int FRONT = 1;
    
    public MinHeap(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = new double[this.maxsize + 1];
        Heap[0] = Double.MIN_VALUE;
    }
 
    private int parent(int pos)
    {
        return pos / 2;
    }
 
    private int leftChild(int pos)
    {
        return (2 * pos);
    }
 
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }
 
    private boolean isLeaf(int pos)
    {
        if (pos >  (size / 2)  &&  pos <= size)
        { 
            return true;
        }
        return false;
    }
 
    private void swap(int fpos, int spos)
    {
        double tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }
 
    private void minHeapify(int pos)
    {
        if (!isLeaf(pos))
        { 
            if ( Heap[pos] > Heap[leftChild(pos)]  || Heap[pos] > Heap[rightChild(pos)])
            {
                if (Heap[leftChild(pos)] < Heap[rightChild(pos)])
                {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                }else
                {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }
 
    public void insert(double element)
    {
        Heap[++size] = element;
        int current = size;
 
        while (Heap[current] < Heap[parent(current)])
        {
            swap(current,parent(current));
            current = parent(current);
        }	
    }
 
    public void print()
    {
	if(size == 0){
    		System.out.println("Empty");
    	}
    	if(size == 1){
    		System.out.println(Heap[FRONT]);
    	}
        for (int i = 1; i <= size / 2; i++ )
        {
            System.out.print(Heap[i] + " : " + Heap[2*i]);
            if(2*i + 1 <= size){
            	System.out.print(" : " + Heap[2*i + 1]);
            }
            System.out.println();
        } 
    }
 
    public void minHeap()
    {
        for (int pos = (size / 2); pos >= 1 ; pos--)
        {
            minHeapify(pos);
        }
    }
 
    public double remove()
    {
        double popped = Heap[FRONT];
        if(size == 1){
        	size = 0 ;
        }
        else{
        	Heap[FRONT] = Heap[size--];
            minHeapify(FRONT);
        }
        return popped;
    }

	public int getSize() {
		return size;
	}

	public int setSize(int size) {
		this.size = size;
		return size;
	}
 
}

class TreeRemiaToDot{
	FileWriter fw = null;
	void createDotFile(String fileName) throws IOException{
		File dotFile = new File("../../" + fileName);
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

	void fillTreeRemiaDetail(nodeRemia n, int i , double den) throws IOException{
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
		fillTreeRemiaDetail(n.left,2*i, den);
		fillTreeRemiaDetail(n.right,2*i+1, den);
	}
	
	void remiaGraphStart() throws IOException{
		fw.append("digraph G { \n");
		fw.append("rankdir = BT;" + '\n');
	}
	
	void remiaGraphEnd() throws IOException{
		fw.append("}");
		fw.close();
	}
	
	void writeToDot(TreeRemia t,int i , double den) throws IOException{
		double d = t.root.data;
		String s = fractionToRatio(d , den);
		fw.append(i + "[label=" +s + "];" + "\n");
		fillTreeRemiaDetail(t.root,i,den);
	}
	
	public void dotToPng(String fileName ,String imageName) throws IOException{
    	final String graphvizDotUtilityPath = "/usr/bin/dot";
    	final String imageType = "png";
    	final String sourceDotFilePath = "../../" + fileName;
    	final String outputImageFilePath = "../../" + imageName;

    	final List commandList = new ArrayList();
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

	
}