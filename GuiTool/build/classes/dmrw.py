import pdb
import math
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
		
def preorder(root):
	if(root==None):
		return 
	if(root.visited==1):
		return
	root.visited = 1
	print root.cv
	preorder(root.left_p)
	preorder(root.right_p)
	

	
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
		target.write("%d -> %d "%(root.left_p.cv,root.cv))
		rec(target,root.left_p,yy)
	if(root.right_p!=None):
		target.write("%d -> %d ;\n"%(root.right_p.cv,root.cv))
		rec(target,root.right_p,yy)
	
def graph_to_dot(root):
	#print root
	target = open("./image/DMRW/DMRWDot.dot","w")
	target.write("digraph DMRW{\nrankdir = BT;\n")
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
	#print tmp.left_val
	graph_to_dot(mid_node)
	stat_filename = "./stat/DMRW/DMRW_Demo.txt"
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
	#~ target = open("./stat/DMRW/DMRW_Demo.txt",'w')
	stat_file_handle.write("%d\t"%c)
	stat_file_handle.write("%d\t%d\t%d\t%d\n"%(tmp.right_val,tmp.right_val,tmp.left_val,total_waste))
	stat_file_handle.close()

filename = "./image/DMRW/dmrw.input"
target = open(filename,'r')
stri = target.read()
stri = stri.split(" ")
#~ pdb.set_trace()
dmrw(float(stri[0]),float(stri[1]),float(stri[2])/2**(float(stri[3])),float(stri[3]))
#~ math.pow(2,float(stri[3])
