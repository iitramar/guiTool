import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dmrw {
    public void dotToPng(String s, String algo) throws IOException{
        final String imageType = "png";
        final String sourceDotFilePath = "./image/" + algo + "/" + algo + "Dot.dot";
        final String outputImageFilePath = s;
        //dot -Kfdp -n -Tpng pp.dot -o p.png
        //commandList.add("-Gsize=9,12! -Gdpi=100");
        char os = System.getProperty("os.name").charAt(0);

        if(os == 'L'){
            String graphvizDotUtilityPath = "/usr/bin/dot";
            List commandList = new ArrayList();
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
        else if(os == 'W'){
            try { 
                Process p=Runtime.getRuntime().exec("cmd /c dot -Tpng " + sourceDotFilePath + " -o " +  outputImageFilePath); 
                p.waitFor(); 
                BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
                String line=reader.readLine(); 
                while(line!=null) 
                { 
                System.out.println(line); 
                line=reader.readLine(); 
                } 

            } catch(IOException e1) {} 
            catch(InterruptedException e2) {}
        }

    }
	
    public void runDmrw(String s){
        final List commandList = new ArrayList();
        final String pythonPath = "/usr/bin/python";
        final String sourceFile = "./src/" + s.toLowerCase() + ".py";
        commandList.add(pythonPath);
        commandList.add(sourceFile);
        System.out.println("commandlist = " + commandList);
        try {
            Process process = new ProcessBuilder(commandList).start();
            System.out.println("Compiling");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
