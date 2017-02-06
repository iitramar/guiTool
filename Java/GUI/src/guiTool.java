import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class guiTool{
	JTextField c_t, del, segments;
	JButton submit;
	static JPanel bottom, right,left, value,top, gui, bottom_right, statsLabel;
	double zoom = 1.0;
	JFrame main;
	JLabel label;
	BufferedImage image = null;
	JScrollPane scrollPane = new JScrollPane();
	public guiTool() {
		main = new JFrame("GUI Tool");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[] algorithms = {"---", "Vospa", "Codos", "Remia", "DMRW", "IDMA", "MinMix" };
		JComboBox algolist = new JComboBox(algorithms);
		
		gui = new JPanel(new BorderLayout());
        main.add(gui);        
        left = new JPanel(new BorderLayout());
        right = new JPanel(new BorderLayout());
        gui.add(left, BorderLayout.WEST);
        gui.add(right, BorderLayout.CENTER);
        
        c_t = new JTextField();
        del = new JTextField();
        segments = new JTextField();
        top = new JPanel(new BorderLayout());
        bottom = new JPanel(new BorderLayout());
        bottom_right = new JPanel(new BorderLayout());
        ///bottom_right_center = new JPanel(new BorderLayout());
        left.add(top, BorderLayout.NORTH);
        left.add(bottom, BorderLayout.CENTER);
        right.add(bottom_right, BorderLayout.SOUTH);
        //bottom_right.add(bottom_right_center, BorderLayout.CENTER);
        JPanel labels = new JPanel(new GridLayout(0,1));
        JPanel controls = new JPanel(new GridLayout(0,1));
        top.add(labels, BorderLayout.WEST);
        top.add(controls, BorderLayout.CENTER);
        submit = new JButton("Submit");
        top.add(submit, BorderLayout.SOUTH);
        labels.add(new JLabel("Algorithm"));
        controls.add(algolist);
        
        statsLabel = new JPanel(new GridLayout(0,1));
        value = new JPanel(new GridLayout(0,1));
        bottom_right.add(statsLabel, BorderLayout.WEST);
        bottom_right.add(value, BorderLayout.CENTER);
        algolist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String s = (String) algolist.getSelectedItem();
				if(s=="Vospa"){
		        	labels.add(new JLabel("Target Concentration: "));
		            controls.add(c_t);
		            labels.add(new JLabel("Tolerance: "));
		            controls.add(del);
		            labels.add(new JLabel("Segments: "));
		            controls.add(segments);
		        }
				if(s=="Codos"){
					labels.add(new JLabel("Number of Reactant: "));
		            controls.add(c_t);
		            labels.add(new JLabel("Precision Level: "));
		            controls.add(del);
		            labels.add(new JLabel("Target Concentration: "));
		            controls.add(segments);
				}
				if(s=="Remia"){
					labels.add(new JLabel("Number of Targets: "));
		            controls.add(segments);
					labels.add(new JLabel("Target Concentration: "));
		            controls.add(c_t);
		            labels.add(new JLabel("Accuracy Level: "));
		            controls.add(del);
				}
				if(s=="DMRW"){
					labels.add(new JLabel("Target Concentration: "));
		            controls.add(segments);
					labels.add(new JLabel("Buffer and Sample: "));
		            controls.add(c_t);
		            labels.add(new JLabel("Accuracy Level: "));
		            controls.add(del);
				}
				if(s=="IDMA"){
					labels.add(new JLabel("Target Concentration: "));
		            controls.add(segments);
					labels.add(new JLabel("Sample and Buffer: "));
		            controls.add(c_t);
		            labels.add(new JLabel("Accuracy Level: "));
		            controls.add(del);
				}
				if(s=="MinMix"){
					labels.add(new JLabel("Number of Reactant: "));
		            controls.add(c_t);
		            labels.add(new JLabel("Target Concentration: "));
		            controls.add(del);
		            labels.add(new JLabel("Accuracy: "));
		            controls.add(segments);
				}
				
				labels.revalidate();
				controls.revalidate();
			}
          });
        
        submit.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
        		String s = (String) algolist.getSelectedItem();
        		if(s=="Vospa"){
    				vospa vospaObj = new vospa();
    				try {
    					
    					vospaObj.runVospa(c_t.getText(), del.getText(), segments.getText());
    					TimeUnit.MILLISECONDS.sleep(1000);
    					//readStat();
    					loadImage();
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (InterruptedException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
        		}
        		else if(s=="Remia"){
        			Remia remiaObj = new Remia();
    				try {
    					
    					remiaObj.runRemia(segments.getText(), c_t.getText(), del.getText());
    					TimeUnit.MILLISECONDS.sleep(1000);
    					//readStat();
    					loadImageRemia();
    				} catch (InterruptedException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
        		}
        		else if(s=="Codos"){
        			codos codosObj = new codos();
    				try {
    					
    					codosObj.runCodos(c_t.getText(), del.getText(), segments.getText());
    					TimeUnit.MILLISECONDS.sleep(1000);
    					//readStat();
    					loadImageCodos();
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (InterruptedException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
        		}
        		else if(s=="MinMix"){
        			Dmrw dmrwObj = new Dmrw();
        			FileWriter fw ;
        			try {
        				fw = new FileWriter("../g2/minmix.input");
						fw.write(c_t.getText() + " " + del.getText() + " " + segments.getText());
						fw.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
        			
        			try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
    				try {
    					
    					dmrwObj.runMinmix();
    					TimeUnit.MILLISECONDS.sleep(1000);
    					dmrwObj.dotToPng();
    					TimeUnit.MILLISECONDS.sleep(1000);
    					//readStat();
    					
    					loadImageDmrw();
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (InterruptedException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
        		}
        		else if(s=="DMRW") {
        			Dmrw dmrwObj = new Dmrw();
        			FileWriter fw ;
        			try {
        				fw = new FileWriter("../g2/dmrw.input");
						fw.write(c_t.getText() + " " + segments.getText() + " " + del.getText());
						fw.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
        			
        			try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
    				try {
    					
    					dmrwObj.runDmrw();
    					TimeUnit.MILLISECONDS.sleep(1000);
    					dmrwObj.dotToPng();
    					TimeUnit.MILLISECONDS.sleep(1000);
    					//readStat();
    					
    					loadImageDmrw();
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (InterruptedException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
        		}
        		else if(s=="IDMA"){
        			Dmrw dmrwObj = new Dmrw();
        			FileWriter fw ;
        			try {
        				fw = new FileWriter("../g2/idma.input");
						fw.write(c_t.getText() + " " + segments.getText() + " " + del.getText());
						fw.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
        			
        			try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
    				try {
    					
    					dmrwObj.runIdma();
    					TimeUnit.MILLISECONDS.sleep(1000);
    					dmrwObj.dotToPng();
    					TimeUnit.MILLISECONDS.sleep(1000);
    					//readStat();
    					
    					loadImageDmrw();
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (InterruptedException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
        		}

			}
        	
        });
        
        main.pack();
        main.setSize(1000, 600);
        main.setVisible(true);
	}
	
	public void readStatRemia() throws IOException{
		String folder_name = "graph/flospa";
        String path = "./Stats/";
        String stat_file = c_t.getText() + "_" + del.getText() + "_" + segments.getText() + ".txt";
        FileReader f = new FileReader(path + folder_name + "/" + stat_file);
        char [] a = new char[50];
        f.read(a);
    	String temp = "";
    	JLabel lab1 = new JLabel();
        JLabel lab2 = new JLabel();
        JLabel lab3 = new JLabel();
        JLabel lab4 = new JLabel();
        lab1.setText("Reactant: ");
        lab1.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 14));
        statsLabel.add(lab1);
        lab2.setText("Buffer: ");
        statsLabel.add(lab2);
        lab3.setText("Waste: ");
        statsLabel.add(lab3);
        lab4.setText("Operations: ");
        statsLabel.add(lab4);
        lab2.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 14));
        lab3.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 14));
        lab4.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 14));
        for(char c : a){
        	if(c=='\n'){
            	JTextField val = new JTextField();
            	val.setEditable(false);
            	val.setHorizontalAlignment(JTextField.CENTER);
            	//val.setBorder(null);
            	val.setText(temp);
            	val.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 14));
            	
            	value.add(val);
            	temp = "";
        	}
        	temp = temp + c;
        }
        value.revalidate();
        f.close();
	}
	
	public void readStat() throws IOException{
		String folder_name = "graph";
        String path = "./Stats/";
        String stat_file = "vospastat.txt";
        FileReader f = new FileReader(path + folder_name + "/" + stat_file);
        char [] a = new char[50];
        f.read(a);
    	String temp = "";
    	JLabel lab1 = new JLabel();
        JLabel lab2 = new JLabel();
        JLabel lab3 = new JLabel();
        JLabel lab4 = new JLabel();
        lab1.setText("Reactant: ");
        lab1.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 14));
        statsLabel.add(lab1);
        lab2.setText("Buffer: ");
        statsLabel.add(lab2);
        lab3.setText("Waste: ");
        statsLabel.add(lab3);
        lab4.setText("Operations: ");
        statsLabel.add(lab4);
        lab2.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 14));
        lab3.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 14));
        lab4.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 14));
        for(char c : a){
        	if(c=='\n'){
            	JTextField val = new JTextField();
            	val.setEditable(false);
            	val.setHorizontalAlignment(JTextField.CENTER);
            	//val.setBorder(null);
            	val.setText(temp);
            	val.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 14));
            	
            	value.add(val);
            	temp = "";
        	}
        	temp = temp + c;
        }
        value.revalidate();
        f.close();
	}
	
	public void loadImageRemia(){
		File f = null;
        try {
        	int width = 30, height = 20 ;
        	String graph_file = c_t.getText() + "_" + del.getText() + "_" + segments.getText() + ".png";
        	f = new File("../Remia/remiaDot.png");
        	image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(f);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        label = new JLabel(new ImageIcon(image));
        right.add(label, BorderLayout.CENTER);
        right.revalidate();
	}
	
	public void loadImageDmrw(){
		File f = null;
        try {
        	int width = 30, height = 20 ;
        	f = new File("../g2/p.png");
        	image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(f);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        label = new JLabel(new ImageIcon(image));
        right.add(label, BorderLayout.CENTER);
        right.revalidate();
	}
	
	public void loadImage(){
		
    	File f = null;
        try {
        	int width = 300, height = 200 ;
        	f = new File("../BTP/src/Stats/graph/vospaGraph.png");
        	image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(f);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        label = new JLabel(new ImageIcon(image));
        right.add(label, BorderLayout.CENTER);
        right.revalidate();
	}
	
	public void loadImageCodos(){
		
    	File f = null;
        try {
        	int width = 300, height = 200 ;
        	System.out.println("Reading Image");
        	f = new File("../Codos/codosDot.png");
        	image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(f);
            System.out.println("Done!");
        } catch (IOException e) {
        	e.printStackTrace();
        }
        label = new JLabel(new ImageIcon(image));
        right.add(label, BorderLayout.CENTER);
        right.revalidate();
	}
	
	public static void main(String [] args) throws IOException{
		guiTool tool = new guiTool();
	}
} 