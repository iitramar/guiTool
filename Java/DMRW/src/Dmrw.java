import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dmrw {
	public void dotToPng() throws IOException{
    	final String graphvizDotUtilityPath = "/usr/bin/dot";
    	final String imageType = "png";
    	final String sourceDotFilePath = "../g2/pp.dot";
    	final String outputImageFilePath = "../g2/p.png";

    	final List commandList = new ArrayList();
    	//dot -Kfdp -n -Tpng pp.dot -o p.png
    	commandList.add(graphvizDotUtilityPath);
    	//commandList.add("-Kfdp");
    	//commandList.add("-n");
    	commandList.add("-T" + imageType);
    	//commandList.add("-Gsize=9,12! -Gdpi=100");
    	commandList.add(sourceDotFilePath);
    	commandList.add("-o" + outputImageFilePath);
    	
    	try {
			Process process = new ProcessBuilder(commandList).start();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void runDmrw(){
		final List commandList = new ArrayList();
    	//dot -Kfdp -n -Tpng pp.dot -o p.png
		final String pythonPath = "/usr/bin/python";
		final String sourceFile = "../g2/dmrw_v2.py";
		commandList.add(pythonPath);
    	commandList.add(sourceFile);
    	try {
			Process process = new ProcessBuilder(commandList).start();
			System.out.println("Compiling");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runIdma(){
		final List commandList = new ArrayList();
    	//dot -Kfdp -n -Tpng pp.dot -o p.png
		final String pythonPath = "/usr/bin/python";
		final String sourceFile = "../g2/idma.py";
		commandList.add(pythonPath);
    	commandList.add(sourceFile);
    	try {
			Process process = new ProcessBuilder(commandList).start();
			System.out.println("Compiling");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void runMinmix(){
		final List commandList = new ArrayList();
    	//dot -Kfdp -n -Tpng pp.dot -o p.png
		final String pythonPath = "/usr/bin/python";
		final String sourceFile = "../g2/minmix.py";
		commandList.add(pythonPath);
    	commandList.add(sourceFile);
    	try {
			Process process = new ProcessBuilder(commandList).start();
			System.out.println("Compiling");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
