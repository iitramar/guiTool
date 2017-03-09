import os
import pickle

	
def tree_to_dot(ans,graph,BS):
	n = len(BS)
	filehandle = open("./image/MTC/MTCDot.dot",'w')
	filehandle.write("digraph mtc{\n")
	
	for i in range(n):
		filehandle.write("node%d [label=\"%s\"];\n"%(i,BS[i]))
	#pdb.set_trace()
	for i in range(n):
		
		par = ans[i-1]
		child = ans[i]
		filehandle.write("node%d -> node%d [label=\"%s\"];\n"%(par,child,graph[par][child]))
	#for i in range(n):
		#for j in range(n):
			#filehandle.write("node%d -> node%d [label=\"%s\"];\n"%(i,j,graph[i][j]))
	filehandle.write("}")
	filehandle.close()

def bin_maker(sample_array,n,d):
	bins = ["" for i in range(n)]
	for i in range(n):
		mask = 2**(d-1)
		for j in range(d):
			if(sample_array[i] & mask !=0):
				bins[i] += '1'
			else:
				bins[i] += '0'
			mask /= 2
	return bins
	
def reverse(a):
	
	n = len(a)
	ans = ""
	for i in range(1,n+1):
		ans += a[(-1)*i]
	return ans
	
def edge_length(a,b):
	n = len(a)
	
	#print "%s %s"%(a,b)
	b = reverse(b)	
	i = 0
	while(i<n and a[i]==b[i]):
		i += 1
	return n - i
	
	
def mtc(target,d):
	print target
	n = len(target)
	BS = bin_maker(target,n,d)
	BS.insert(0,"")
	for i in range(d):
		BS[0] += '0'
	n += 1
	print BS
	graph = [[edge_length(BS[j],BS[i]) for i in range(n)] for j in range(n)]
	print graph
	#exit()
	matrix = []
	matrix.append(BS)
	matrix.append(graph)
	print "tsp starts here "
	pickle.dump(matrix,open("./src/tsp/citiesAndDistances.pickled","wb"))
	os.system("python ./tsp/anttsp.py %d"%n)
	ans = pickle.load(open("./src/tsp/mtc_tsp.output","r"))
	print ans
	print "tsp ends here"
	print graph
	tree_to_dot(ans,graph,BS)
	#os.system("dot -Tpng pp.dot -o p.png; display p.png")
	
filename = "./image/MTC/mtc.input"
filehandle = open(filename)
mtc_input = filehandle.read()
mtc_input = mtc_input.split(" ")
no_of_target = int(mtc_input[0])
accuracy = int(mtc_input[1])
target = []
for i in range(no_of_target):
	target.append(int(mtc_input[i+2]))
mtc(target,accuracy)

