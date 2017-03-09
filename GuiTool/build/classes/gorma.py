from collections import deque
import pdb
class node:
	
	def __init__(self,cv, d):
		self.node_id = ""
		self.cv = cv
		self.d = d
		self.left = None 
		self.right = None
		self.waste = 1
		self.sharable = 1
		
	def __repr__(self):
		return "%s/%s %s"%(self.cv,2**(self.d),self.node_id)


def rec(target,root):
	if(root.left!=None):
		target.write("%s -> %s;\n"%(root.node_id,root.left.node_id))
		#print "%d/%d -> %d/%d;\n"%(root.cv,root.d,root.left.cv,root.left.d)
		rec(target,root.left)
	if(root.right!=None):
		target.write("%s -> %s;\n"%(root.node_id,root.right.node_id))
		#print "%d/%d -> %d/%d;\n"%(root.cv,root.d,root.right.cv,root.right.d)
		rec(target,root.right)

countt = 0

def rec_label(targ,root):
	if(root==None):
		return
	kk = 2**(root.d)
	if(kk==1):
		targ.write("%s [label=\"%s\"];\n"%(root.node_id,root.cv))
		return 
	targ.write("%s [label=\"%s/%s\"];\n"%(root.node_id,root.cv,kk))
	rec_label(targ,root.left)
	rec_label(targ,root.right)

def modify_dot(root):
	global countt
	nm = "%d_%d"%(root.cv,root.d)
	targ = open("./image/Gorma/GormaDotOld.dot",'r')
	targ2 = open("./image/Gorma/GormaDot.dot",'w')
	init_dot = targ.read()
	h_map = {}
	init_dot = init_dot.split("\n")
	for s in init_dot:
		if s not in h_map:
			targ2.write(s)
			h_map[s] = 1
	targ.close()
	targ2.close()
	#os.system("dot -Tpng pp1.dot -o p.png; ")
	#os.system("dot -Tpng pp1.dot -o image/p%d.png"%countt) 
	countt += 1
	
	
def tree_to_dot(root):
	nm = "%d_%d"%(root.cv,root.d)
	targ = open("./image/Gorma/GormaDotOld.dot",'w')
	targ.write("digraph groma{\n")
	rec_label(targ,root)
	rec(targ,root)
	targ.write("}")
	targ.close()
	#os.system("dot -Tpng pp.dot -o p.png;")
	#os.system("dot -Tpng %s.dot -o %s.png; display %s.png"%(nm,nm,nm))
	modify_dot(root)

def init_id(level):
	n = len(level)
	for i in range(n):
		level[i].node_id = "node%d"%i
		
def bfs(root,parent):
	queue = deque()
	queue.append(root)
	level = []
	
	while queue:
		temp_node = queue.popleft()
		level.append(temp_node)
		if(temp_node.left!=None):
			parent[temp_node.left] = temp_node
			queue.append(temp_node.left)
		if(temp_node.right!=None):
			parent[temp_node.right] = temp_node
			queue.append(temp_node.right)
	return level
	
def maximal_droplet_sharing(root):
	parent = {}
	parent[root] = root
	level = bfs(root,parent)
	init_id(level)
	d_wise_nodes = [[] for i in range(root.d+1)]
	
	for i in level:
		d_wise_nodes[i.d].append(i)
	
	n = root.d - 1
	while n > 0:
		d_length = len(d_wise_nodes[n])
		for i in range(d_length):
			for j in range(i+1,d_length):
				if(d_wise_nodes[n][i].cv == d_wise_nodes[n][j].cv and d_wise_nodes[n][j].waste==1):
					if(parent[d_wise_nodes[n][i]].left == d_wise_nodes[n][i]):
						parent[d_wise_nodes[n][i]].left = d_wise_nodes[n][j]
						
					else:
						parent[d_wise_nodes[n][i]].right = d_wise_nodes[n][j]
					
					d_wise_nodes[n][j].waste = 0
					maximal_droplet_sharing(root)
					return
						
				
		n -= 1
				
				

def initialise_best_tree():
	v = node(100,100)
	root = v
	for i in range(100):
		v.left = node(1,0)
		v.right = node(100,100)
		v = v.right
	return root
	
def tree_evaluate(root):
	if(root==None):
		return 0
	if(root.cv == 1 and root.d == 0):
		return 1
	return tree_evaluate(root.left) + tree_evaluate(root.right)
	
##reduce fraction tested successfully
def reduce_fraction(x,d):
	if(x==0):
		return [0,0]
	tmp = d
	ww = x
	while(ww%2==0):
		tmp = tmp-1
		ww = ww/2
	arr = [0,0]
	arr[0] = ww
	arr[1] = tmp
	return arr

def tree_duplicate(root):
	if(root==None):
		return None
	n = node(root.cv, root.d)
	n.left = tree_duplicate(root.left)
	n.right = tree_duplicate(root.right)
	return n
	
def build_cmt(z,d):
	#print "heya!!"
	if(d<0):
		return None
	v = node(z,d)
	x = min(2**(d-1),z)
	y = z - x
	while(x>y):
		l_parameter = reduce_fraction(x,d-1)
		r_parameter = reduce_fraction(y,d-1)
		v.left = build_cmt(l_parameter[0], l_parameter[1])
		v.right = build_cmt(r_parameter[0], r_parameter[1])
		#tree_to_dot(v)
		x = x - 1
		y = y + 1
	return v
		
def cmt_enumeration(z,d):
	v = node(z,d)
	best_tree = initialise_best_tree()
	min_reactant = tree_evaluate(best_tree)
	if(d<0):
		return None
	x = min(2**(d-1),z)
	y = z - x
	
	while(x>y):
		l_parameter = reduce_fraction(x,d-1)
		r_parameter = reduce_fraction(y,d-1)
		v.left = build_cmt(l_parameter[0], l_parameter[1])
		v.right = build_cmt(r_parameter[0], r_parameter[1])
		temp = tree_evaluate(v)
		if(temp<min_reactant):
			min_reactant = temp
			best_tree = tree_duplicate(v)
		
		x = x-1
		y = y+1
	return best_tree

def gorma(ct,d):
	bt = cmt_enumeration(ct,d)
	
	maximal_droplet_sharing(bt)
	
	
	#for i in level:
		#print "%s/%s"%(i.cv,2**(i.d))
	tree_to_dot(bt)



filename = "./image/Gorma/gorma.input"
filehandle = open(filename)
gorma_input = filehandle.read()
gorma_input = gorma_input.split(" ")
gorma(int(gorma_input[0]),int(gorma_input[1]))
