import pdb
import os
class node:
	def __init__(self,cv,side):
		self.cv = cv
		self.side = side
		self.waste = 0
		self.left_p = None
		self.right_p = None
		self.left_val = 0
		self.right_val = 0
		self.visited = 0
		
def find_node(root, ct):
	if(root==None):
		return None
	if(root.cv==ct):
		return root
	l_ans = find_node(root.left_p,ct)
	r_ans = find_node(root.right_p,ct)
	if(l_ans!=None):
		return  l_ans
	if(r_ans!=None):
		return r_ans
	return None
	
def reset_visited(target_node):
	if(target_node==None):
		return 
	target_node.visited = 0
	reset_visited(target_node.left_p)
	reset_visited(target_node.right_p)
	
def postorder(root):
	if(root==None):
		return []
	if(root.visited==1):
		return []
	root.visited = 1
	arr = []
	arr.append(root)
	
	arr2 = postorder(root.left_p) + postorder(root.right_p)
	print root.cv
	return arr2 + arr
	

def compute_outdegree(nodes, i,total_nodes):
	tmp = nodes[i]
	ans = 0
	for j in range(total_nodes):
		if(nodes[j].left_p==tmp and tmp.side=="l"):
			ans = ans + 1
		if(nodes[j].right_p==tmp and tmp.side=="r"):
			ans = ans + 1
	return ans
		
	
def rec(target, root,yy):
	if(root==None):
		return
	if(root.visited==1):
		return
	root.visited = 1
	yy = yy - 2
	if(root.side=='l'):
		target.write("%d [pos=\"2,%d!\"];\n"%(root.cv,yy))
		
	
	if(root.side=="r"):
		target.write("%d [pos=\"8,%d!\"];\n"%(root.cv,yy))
		
	
	if(root.side=="m"):
		target.write("%d [pos=\"6,%d!\"];\n"%(root.cv,yy))
		
	if(root.left_p!=None):
		target.write("%d -> %d [label=\"%d\"];\n"%(root.cv,root.left_p.cv,root.left_val))
		rec(target,root.left_p,yy)
	if(root.right_p!=None):
		target.write("%d -> %d [label=\"%d\"];\n"%(root.cv,root.right_p.cv,root.right_val))
		rec(target,root.right_p,yy)
	
def graph_to_dot(root):
	print root
	target = open("./image/IDMA/IDMADot.dot","w")
	target.write("digraph DMRW{\nrankdir = LR;\n size=\"8,10.5\";\n ")
	rec(target,root,25)
	target.write("}")
	target.close()
	
def dmrw(cl,ch,ct,n):
	power_of_two = 2**n
	a = int(cl*power_of_two)
	b = int(ch*power_of_two)
	c = int(ct*power_of_two)
	l,r = a,b
	l_node = node(a,"l")
	r_node = node(b,"r")
	x = (l+r)/2
	if(x<c):
		mid_node = node(x,"l")
	else:
		mid_node = node(x,"r")
	
	mid_node.left_p = l_node
	mid_node.right_p = r_node
	
	err = abs(x-c)
	i,j,num = 0,0,0
	stack = []
	stack.append(mid_node)
	parent_left = {}
	parent_right = {}
	parent_left[mid_node] = l_node
	parent_right[mid_node] = r_node
	
	while(err>=1):
		if(x<c):
			l = x
			mid_node.side = "l"
			l_node = mid_node
			i = i+1
		else:
			r = x
			mid_node.side = "r"
			r_node = mid_node
			j = j+1
		
		num = num + 1
		x = (l+r)/2
		mid_node = node(x,"m")
		mid_node.left_p = l_node
		mid_node.right_p = r_node
		stack.append(mid_node)
		parent_left[mid_node] = l_node
		parent_right[mid_node] = r_node
		err = abs(x-c)
	
	###################################################################
	#BackTracking######################################################
	###################################################################
	#pdb.set_trace()
	mid_node.left_val = 1
	mid_node.right_val = 1
	total_waste = 0
	stack.pop()
	while(num>0):
		tmp = stack.pop()
		val = 0
		if(tmp.side == "l") :
			for key in parent_left:
				if(parent_left[key]==tmp):
					val = val + key.left_val
		elif(tmp.side == "r"):
			for key in parent_right:
				if(parent_right[key]==tmp):
					val = val + key.right_val
		if(val%2==1):
			tmp.waste = 1
			total_waste = total_waste + 1
			val = val + 1
		tmp.right_val = val/2
		tmp.left_val = val/2
		num = num - 1
	#pdb.set_trace()
	#preorder(mid_node)
	stat_filename = "./stat/IDMA/IDMA_Demo.txt"
	counter = 1
	if os.path.isfile(stat_filename):
		stat_file_handle = open(stat_filename)
		stat_file_data = stat_file_handle.read()
		stat_file_data_linewise = stat_file_data.split("\n")
		if stat_file_data_linewise !=['']:
			print stat_file_data_linewise
			stat_file_data_linewise.pop()
			last_value = (stat_file_data_linewise.pop()).split("\t")
			counter += int(last_value[0])
		stat_file_handle.close()
		
	stat_file_handle = open(stat_filename,'a')
	stat_file_handle.write("%d\t"%counter)
	#~ target = open("./stat/IDMA/IDMA_Demo.txt",'w')
	stat_file_handle.write("%d\t"%c)
	stat_file_handle.write("%d\t%d\t%d\t%d\n"%(tmp.right_val,tmp.right_val,tmp.left_val,total_waste))
	stat_file_handle.close()
	return mid_node


	
def idma(cl,ch,ct,n):
	power_of_two = 2**n
	target_node = dmrw(cl,ch,ct,n)
	a = int(cl*power_of_two)
	b = int(ch*power_of_two)
	c = int(ct*power_of_two)
	nodes = postorder(target_node)
	#reset_visited(target_node)
	total_nodes = len(nodes)
	outdegree = [0 for i in range(total_nodes)]
	#print nodes
	#pdb.set_trace()
	for i in range(total_nodes):
		#print nodes[i].cv
		outdegree[i] = compute_outdegree(nodes,i,total_nodes)
	
	flag = 0
	print outdegree
	new_node = None
	for i in range(total_nodes)	:
		if(outdegree[i]>3 and (nodes[i].cv!=a and nodes[i].cv!=b)):
			flag = 1
			new_node = nodes[i]
			break
		
	#pdb.set_trace()
	if(flag==0):
		return target_node
	
	node_count = 0
	j = 0
	
	nl,nr = 0,0
	
	#print "%d %d new_node"%(new_node.left_p.cv,new_node.right_p.cv)
	#print "%d %d nodesj"%(nodes[j].left_p.cv,nodes[j].right_p.cv)
	#postorder(nodes[j])
	root_of_new_tree = None
	if(new_node.cv == 2**(n-1)):
		if(new_node.cv<c):
			nl = float(new_node.left_p.cv)/power_of_two
			nr = float(new_node.cv + new_node.right_p.cv)/(2*power_of_two)
			root_of_new_tree = dmrw(nl,nr,ct,n)
			arr = find_node(root_of_new_tree,(nl+nr)*power_of_two/2)
			arr.left_p = new_node.left_p
			arr.right_p.left_p =  new_node
			arr.right_p.right_p = new_node.right_p
			
			
		else:
			nr = float(new_node.right_p.cv)/power_of_two
			nl = float(new_node.cv + new_node.right_p.cv)/(2*power_of_two)
			root_of_new_tree = dmrw(nl,nr,ct,n)
			arr = find_node(root_of_new_tree,(nl+nr)*power_of_two/2)
			arr.left_p.left_p =  new_node
			arr.left_p.right_p = new_node.right_p
			arr.right_p = new_node.right_p
	
	else:
		
		if(new_node.cv<c):
			nr = float(new_node.right_p.cv)/power_of_two
			nl = float(new_node.cv + new_node.left_p.cv)/(2*power_of_two)
			root_of_new_tree = dmrw(nl,nr,ct,n)
			arr = find_node(root_of_new_tree,(nl+nr)*power_of_two/2)
			arr.right_p = new_node.right_p
			arr.left_p.left_p = new_node.left_p
			arr.left_p.right_p = new_node
			
		else:
			#pdb.set_trace()
			nr = float(new_node.cv + new_node.right_p.cv)/(2*power_of_two)
			nl = float(new_node.left_p.cv)/power_of_two
			
			root_of_new_tree = dmrw(nl,nr,ct,n)
			postorder(root_of_new_tree)
			arr = find_node(root_of_new_tree,(nl+nr)*power_of_two/2)
			arr.left_p = new_node.left_p
			arr.right_p.left_p = new_node
			arr.right_p.right_p = new_node.right_p
			
	print nl
	print nr
	reset_visited(root_of_new_tree)
	#~ graph_to_dot(root_of_new_tree)
	#~ reset_visited(root_of_new_tree)
	#~ postorder(root_of_new_tree)
	return root_of_new_tree 
	print "heya!!"
		
	
	
def main():
	filename = "./image/IDMA/idma.input"

	target = open(filename,'r')
	stri = target.read()
	stri = stri.split(" ")
	#~ root = idma(float(stri[0]),float(stri[1]),float((stri[2])/math.pow(2,float(stri[3]))),float(stri[3]))
	root = dmrw(float(stri[0]),float(stri[1]),float(stri[2])/2**(float(stri[3])),float(stri[3]))
	graph_to_dot(root)
	
	
if __name__=="__main__":
	main()

		
		
		
		
		
		
