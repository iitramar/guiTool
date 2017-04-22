
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author linux
 */
public class Wara {
    
    int reactant , buffer, waste, operations ;
    ForestWara forest , forestOfTarget ;
    
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
    
    public double lcv(double conc){
        double x = 0.5 ;
        while(conc < x){
                x = x/2 ;
        }

        return 2*x ;
    }
    
    public TreeWara buildMixTreeWara(double target,double den ){
		
		TreeWara tree = new TreeWara(target);
		nodeWara temp = tree.root ;
		
		int n = (int) (temp.data * den) ;
		while((n & (n-1)) != 0){
			temp.left = new nodeWara(lcv(temp.data));
			temp.right = new nodeWara(2*temp.data - temp.left.data);
			temp = temp.right ;
			n = (int) (temp.data * den) ;
			
		}
		
		return tree;
    }
    
    public void dropletSharing(ForestWara f){
        
    }
    
    public void runWara(String s_n, String s_c_t, String s_del , String fileName){
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
        
        Wara waraObj = new Wara();
        TreeWara tobj = new TreeWara();
        TreeWara tree;
        forestOfTarget = new ForestWara();
        for(int i=0 ; i <N ; i ++){
                tree = waraObj.buildMixTreeWara(target[i], den);
                forestOfTarget.setOfTreeWara.addElement(tree);
        }
        
        dropletSharing(forestOfTarget);
        
    }
    
}
