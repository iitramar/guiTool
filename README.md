## Installation Manual
* Download and Install latest version of NetBeans ("http://www.oracle.com/technetwork/java/javase/downloads/jdk-netbeans-jsp-142931.html").
* Download and Install Graphviz - Graph Visualization Software ("http://www.graphviz.org/Download.php"). For windows user, add bin directory to your PATH variable. We add "C:\Program Files (x86)\Graphviz2.38\bin" to our PATH variable.
* Download and install python 2.x (for windows only) from "https://www.python.org/downloads/". For windows user, add python root directory to your PATH variable. We add "C:\Python27" to our PATH variable.
* Download or Clone repository from "https://github.com/iitramar/guiTool".
* Open the project in NetBeans.
* Go to "http://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.0/". In order to import Gson using netbeans you will need to download 3 files from here:

	1- gson-2.8.0.jar (classpath)
	2- gson-2.8.0-sources.jar (sources)
	3- gson-2.8.0-javadoc.jar (javadoc)

* Then, you have to create a library on netbeans:
	
	1- Open the project properties.
	2- Select Libraries.
	3- Click on "Add Library". Click on "Create" and provide "Library_Name" as "Gson".
	4- You have to import the right file in each of the tabs: classpath, sources, javadoc.



