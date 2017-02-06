import java.io.*;
import java.util.*;

class Graph{
	HashMap<Double,LinkedList<Double> > graph;
	
	public Graph(){
		graph = new HashMap<Double,LinkedList<Double> >();;
	}
	
	void addVertex(double u){
		LinkedList<Double> L = new LinkedList<Double>();
		graph.put(u, L);
	}
	
	void addEdge(double u, double v){
		LinkedList<Double> temp = graph.get(u);
		temp.add(v);
	}
	
	boolean checkKey(double key){
		return graph.containsKey(key);
	}
}

class node{
	double data;
	double ultilized;
	node parent;
	node child;
	node secondaryParent;
	
	public node(double data){
		this.data = data;
		this.parent=null;
		this.child=null;
		this.secondaryParent = null;
		this.ultilized = 0;
	}
	
}

class Tree{
	node head,last;
	
	public Tree(){
		head = last = null;
	}
	
	public Tree(double data){
		head = last = new node(data);
	}
	
	void mixing(node n, double data){
		node temp = new node(data);
		n.child = temp;
		last.child = temp;
		temp.parent = last;
		temp.secondaryParent = n;
		last = temp;
	}
	
	void pruning(node n){
		node p = n.parent;
		node q = n.secondaryParent;
		p.child = null;
		q.child = null;
		n.parent = null;
	}
}

public class tpg {
	double d[];
	
	int accuracyLevel(double acc){
		Double k  = acc;
		String[] splitter = k.toString().split("\\.");
		//splitter[0].length();   // Before Decimal Count
		return splitter[1].length();   // After  Decimal Count
	}
	
	void fillD(int p){
		int q = (int)Math.pow(2,p-2);
		int l = 2*q-1;
		d = new double[l];
		for(int i=0;i<l;i++){
			d[i] = (double)(i+1)/(2*q);
		}
	}
	
	Graph DFS(Graph g, double cin, int p , int n){
		double cout;
		for(int i=0;i<d.length;i++){
			cout = d[i]*cin;
			if(accuracyLevel(cout)<=n){
				if(!g.checkKey(cout)){
					g.addVertex(cout);
					g = DFS(g,cout,p,n);
				}
				g.addEdge(cin, cout);
			}
		}
		return g;
	}
	
	Graph gcv(int p,int n){
		Graph graph = new Graph();
		fillD(p);
		graph.addVertex(1.0);
		graph = DFS(graph,1,p,n);
		return graph;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		tpg tp = new tpg();
		Graph g = tp.gcv(3, 4);
		HashMap<Double,LinkedList<Double> > check = g.graph;
		for (Map.Entry m : check.entrySet()) {
		    System.out.println("Key = " + m.getKey() + ", Value = " + m.getValue());
		}
	}
}
