import math
import pdb

class node:
	mix = 0
	reactant = 0
	def __init__(self,cv):
		self.cv = cv
		self.left = None
		self.right = None
		
def rec(target,root):
	if(root==None):
		return
	if(root.left!=None):
		target.write("%s -> %s ;\n"%(root.cv,root.left.cv))
	if(root.right!=None):
		target.write("%s -> %s;\n"%(root.cv, root.right.cv))
	rec(target,root.left)
	rec(target,root.right)
	
def tree_to_dot(root):
	target = open("./image/Minmix/MinmixDot.dot",'w')
	target.write("digraph minmix{\nrankdir = TD;\n size=\"8,10.5\";\n graph [label= <<u>MinMix</u>> , labelloc=t, fontsize=30];\n")
	rec(target,root)
	target.write("}")
	target.close()
	
def minmix_helper(bins,depth):
	
	if(len(bins[depth])==0):
		child1 = minmix_helper(bins,depth-1)
		child2 = minmix_helper(bins,depth-1)
		temp = node("m%d"%node.mix)
		node.mix = node.mix + 1
		temp.left = child1
		temp.right = child2
		return temp 
	else:
		tm = bins[depth].pop()
		
		temp = node("x%s_%s"%(tm,node.reactant))
		node.reactant = node.reactant + 1
		return temp
		
	
def minmix(sample_array):
	n_reagents = len(sample_array)
	if(n_reagents==0):
		return
	n = sample_array[0][1]
	depth = int(math.log(n)/math.log(2))
	bins = [[] for i in range(depth+1)]
	
	#pdb.set_trace()
	for i in range(depth):
		mask = 2**i
		for j in range(n_reagents):
			if(sample_array[j][0] & mask != 0):
				bins[i].append(j)
	root = minmix_helper(bins,depth)
	return (root,bins)

def get_waste(root):
	if(root==None):
		return 0
	if(root.left==None and root.right==None):
		return 0
	return 1 + get_waste(root.left) + get_waste(root.right)
	
def generate_stat(sample_array):
	minmix_output = minmix(sample_array)
	root = minmix_output[0]
	bins = minmix_output[1]
	total_sample_array = [sum(i) for i in bins]
	waste = get_waste(root)
	return (root,total_sample_array,waste)
	
	
def main():
	filename = "./image/Minmix/minmix.input"
	target = open(filename,'r')
	stri = target.read()
	stri = stri.split(" ")
	d = int(stri.pop())
	total = int(stri[0])
	arr = [[d for j in range(2)] for i in range(total)]
	for i in range(total):
		arr[i][0] = int(stri.pop())
	generate_stat_output = generate_stat(sample_array)
	root = generate_stat_output[0]
	total_sample = generate_stat_output[1]
	waste = generate_stat_output[2]
	stat_filename = "./image/Minmix/minmix_stat.output"
	stat_filehandle = open(stat_filename,'w')
	for i in total_sample:
		stat_filehandle.write("%d ")
	stat_filehandle.write("\n%d\n"%waste)
	stat_filehandle.close()
	tree_to_dot(root)
	
if __name__=='__main__':
	main()

