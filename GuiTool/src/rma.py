import pdb
import math
import os
class node:
	mix = 1
	reagent = 1
	def __init__(self,cv):
		self.cv = cv
		self.left = None
		self.right = None
	
	def __repr__(self):
		return repr(self.cv)


class pair:
	
	def __init__(self,a,x):
		self.a = a
		self.x = x
	
	def __repr__(self):
		return "%d * x%d"%(self.a,self.x)
		

#ratio approximiation takes an array as input and the accuracy level d 
#returns the array containing the new ratios

def debug_statement(p,l):
	print "l is : %d"%l
	print "p starts here"
	for i in p:
		print "%s : %s"%(i.a,i.x+1)
	print "ends here"
	
def preorder(root):
	if(root==None):
		return
	print root.cv
	preorder(root.left)
	preorder(root.right)
	
def expression_to_ratio(p):
	total = 100
	arr = [0 for i in range(total)]
	stri = ""
	for i in p:
		arr[i.x] = i.a
	for i in range(total):
		if(arr[i]!=0):
			stri +="%dX%d +"%(arr[i],i)
	return stri
			
	
def ratio_approx(A):
	l = sum(A)
	d = int(math.ceil(math.log(l,2)))
	ld = 2**d
	if(ld==l):
		return A
	n = len(A)
	arr = [1 for i in range(n)]
	tot = 0
	for i in range(n-1):
		arr[i] = int(ld*A[i]/l)
		if(arr[i]==0):
			arr[i] = 1
		tot += arr[i]
	arr[n-1] = ld - tot
	return arr
	
	
	
	

def rec(target,root):
	if(root==None):
		return
	if(root.left!=None):
		target.write("%s -> %s ;\n"%(root.cv, root.left.cv))
	if(root.right!=None):
		target.write("%s -> %s;\n"%(root.cv, root.right.cv))
	rec(target,root.left)
	rec(target,root.right)
	
def tree_to_dot(root):
	target = open("./image/RMA/RMADot.dot",'w')
	target.write("digraph minmix{\n")
	rec(target,root)
	target.write("}")
	target.close()
	#os.system("dot -Tpngpp.dot -o p.png; display p.png")
	
def find_z(p,e):
	n = len(p)
	
	for i in range(n):
		if(p[i].a==e):
			return i
	return -1
	
def add_terms(subset,p2,p1):
	ans = p1
	for i in subset:
		ans.append(pair(p2[i].a,p2[i].x))
	return ans
	
	
def remove_terms(subset,p):
	n = len(p)
	arr = [1 for i in range(n)]
	for i in subset:
		arr[i]=0
	ans = []
	for i in range(n):
		if(arr[i] and 1):
			ans.append(pair(p[i].a,p[i].x))
	
	return ans
	
	
	
def subset_sum(p,e):
	#pdb.set_trace()
	n = len(p)
	ans = [[0 for j in range(e+1)] for i in range(n+1)]
	for i in range(n+1):
		ans[i][0] = 1
	for i in range(1,n+1):
		for j in range(1,e+1):
			ans[i][j] = ans[i-1][j]
			if(ans[i][j]==0 and j>=p[i-1].a):
				ans[i][j] = ans[i][j] or ans[i-1][j-p[i-1].a]
	
	solution = []
	j = e
	i = n
	while(i>0 and j>0 and ans[i][j]==1):
		solution.append(i-1)
		j = j - p[i-1].a
		flag = 0
		while(i>=0 and j>=0 and ans[i][j]==1):
			flag = 1
			i-=1
		if(flag):
			i+=1
	return solution
		
	
	
def find_max(p):
	n = len(p)
	ans = -1
	mx = -1
	for i in range(n):
		if(p[i].a>mx):
			ans = i
			mx = p[i].a
	return ans
	
def remove_zero(p):
	p_new = []
	for i in p:
		if(i.a!=0):
			p_new.append(i)
	return p_new
	
def remove_p(p,u):
	p_new = []
	n = len(p)
	
	for i in range(n):
		if(i!=u):
			p_new.append(p[i])
	return p_new
	
def express_partioning(p,l):
	#pdb.set_trace()
	u = find_max(p)
	l1 = l/2
	l2 = l1
	p1 = None
	
	p2 = None
	if(p[u].a>=l1):
		p1 = []
		p1.append(pair(1,p[u].x))
		l1 = 1
		p2 = remove_p(p,-1)
		p2[u].a -= l2
	else:
		p1 = []
		p1.append(pair(p[u].a,p[u].x))
		p2 = remove_p(p,u)
		e = l/2 - p[u].a
		z = find_z(p2,e)
		subset = subset_sum(p2,e)
		subset_length = len(subset)
		
			
		if(z!=-1):
			p1.append(pair(p[z].a,p[z].x))
			p2 = remove_p(p2,z)
		
		elif(2*subset_length<len(p2) and subset_length!=0):
			p1 = add_terms(subset,p2,p1)
			p2 = remove_terms(subset,p2)
		
		else:
			v = find_max(p2)
			if(p2[v].a>e):
				p1.append(pair(e,p[v].x))
				p2[v].a -=  e
				#p2 = remove_p(p2,v)
		
	
	ans = [[0,0],[0,0]]
	#print "l1 is %d\nl2 is %d"	%(l1,l2)
	p1 = remove_zero(p1)
	p2 = remove_zero(p2)
	ans[0][0] = p1
	ans[0][1] = l1
	ans[1][0] = p2
	ans[1][1] = l2
	return ans

def partitioning(p,l,root):
	#debug_statement(p,l)
	#pdb.set_trace()
	if(l==1):
		print "null"
		#print "termination zone"
		#for i in p:
			#print i.a
		return 
	ans = express_partioning(p,l)
	print root.cv
	print p
	#ln = node(expression_to_ratio(ans[0][0]))
	#print ln.cv
	#rn = node(expression_to_ratio(ans[1][0]))
	#print rn.cv
	if(ans[0][1]==1):
		ln = node("x%d_%d"%(ans[0][0][0].x,node.reagent))
		node.reagent += 1
	else:
		ln = node("m%d"%node.mix)
		node.mix +=1
		
	partitioning(ans[0][0],ans[0][1],ln)
	root.left = ln
	
	if(ans[1][1]==1):
		rn= node("x%d_%d"%(ans[1][0][0].x,node.reagent))
		node.reagent += 1
	else:
		rn = node("m%d"%node.mix)
		node.mix +=1
		
	partitioning(ans[1][0],ans[1][1],rn)
	root.right = rn
	
	


#rma takes an array A and the accuracy level d as  input 
#returns the root node of the mixing tree 	
def rma(A,d):
	l = 2**d
	n = len(A)
	#pdb.set_trace()
	p = [pair(A[i],i) for i in range(n)]
	print p
	root = node("m0")
	print root.cv
	partitioning(p,l,root)
	print "preorder starts here!!"
	#preorder(root)
	#tree_to_dot(root)
	return root

def get_stat_recursive(root, total_reactants):
	
	waste, reactant = 0,0
	
	if root==None:
		return (waste, reactant)
	root_concentration = root.cv
	if root_concentration[0]=='m':
		waste += 1
		
	if root_concentration[0]=='x':
		reactant += 1
		
	left_ans = get_stat_recursive(root.left,total_reactants)
	right_ans = get_stat_recursive(root.right,total_reactants)
	waste += left_ans[1] + right_ans[1]
	reactant += left_ans[0] + right_ans[0]
	return (reactant,waste)
	
def get_stat(A, accuracy):
	A = ratio_approx(A)
	print sum(A)
	root = rma(A,accuracy)
	return get_stat_recursive(root, len(A))
	
def main():
	filename = "./image/RMA/rma.input"
	filehandle = open(filename)
	rma_input = filehandle.read()
	rma_input = rma_input.split(" ")
	no_of_reactant = int(rma_input[0])
	accuracy = int(rma_input[1])
	A = [ int(rma_input[i+2]) for i in range(no_of_reactant)]
			
	print "ratio approx starts here"
	A = ratio_approx(A)
	print sum(A)
	print A
	root = rma(A,accuracy)
	stat = get_stat_recursive(root, no_of_reactant)
	stat_filename = "./stat/RMA/RMA_Demo.txt"
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
	for i in range(len(A)-1):
		stat_file_handle.write("%d "%A[i])
	stat_file_handle.write("%d\t"%A[-1])
	stat_file_handle.write("%d\tNA\t%d\t%d\n"%(stat[0],stat[1]-1,stat[1]))
	stat_file_handle.close()
	tree_to_dot(root)
	
if __name__=="__main__":
	main()


	





	

