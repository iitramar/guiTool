
import java.util.HashMap;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author linux
 */
class nodeWara
{
	double data ;
	nodeWara left ;
	nodeWara right ;
	
	public nodeWara(double data)
	{
		this.data = data ;
		this.left =null ;
		this.right = null ;
	}
}

class TreeWara
{
	nodeWara root;
	public TreeWara()
	{
		root = null ;
	}
	
	public TreeWara(double data)
	{
		root = new nodeWara(data);
	}
	
	public void printTreeWara(nodeWara root){
		if(root == null){
			return;
		}
		
		System.out.print(root.data + " ");
		printTreeWara(root.left);
		printTreeWara(root.right);
	}
	
}

class ForestWara{
	
	Vector<TreeWara> setOfTreeWara = new Vector<TreeWara>();
	
	public void makeTreeWara(nodeWara n, HashMap<Double, Integer> hm){
		
		if(n == null){
			return ;
		}
		
		double childData = n.data/2 ;
		
		if(hm.containsKey(childData)){
			int available = hm.get(childData);
			
			if(available > 0){
				n.left = new nodeWara(childData);
				available-- ;
				hm.put(childData, hm.get(childData)-1);
				
				if(available > 0 ){
					n.right = new nodeWara(childData);
					available-- ;
					hm.put(childData, hm.get(childData)-1);
				}
			}
			
			makeTreeWara(n.left , hm);
			makeTreeWara(n.right , hm);
		}
		
		
	}
	
	public void buildForest( HashMap<Double, Integer> hm ){
		
		TreeWara tree;
		
		while(hm.get(1.0) > 0){
			tree = new TreeWara(1.0);
			setOfTreeWara.addElement(tree);
			hm.put(1.0, hm.get(1.0)-1);
		}
		
		for(int i=0 ; i < setOfTreeWara.size() ; i++){
			System.out.println(setOfTreeWara.get(i).root.data);
			makeTreeWara(setOfTreeWara.get(i).root , hm);
		}
		
	}
	
	public void printForest(){
		for(int i=0 ; i < setOfTreeWara.size() ; i++){
			setOfTreeWara.get(i).printTreeWara(setOfTreeWara.get(i).root);
			System.out.println("-----------------------");
			System.out.println();
		}
	}

	public int brachNodesIn(nodeWara n){
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
		for(int i =0 ; i <setOfTreeWara.size() ;i ++){
			count += brachNodesIn(setOfTreeWara.get(i).root);
		}

		return count ;
	}

	public int singleChildIn(nodeWara n){

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
		for(int i =0 ; i <setOfTreeWara.size() ;i ++){
			count += singleChildIn(setOfTreeWara.get(i).root);
		}
		return count ;
	}

	
}


public class UtilityWara {
    
}
