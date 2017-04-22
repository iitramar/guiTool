from rma import get_stat
from itertools import product
def get_tuple(length, total):
	return filter(lambda x:sum(x)==total,product(range(total+1),repeat=length))

def check_not_zero(a):
	temp = list(filter(lambda x: x!=0,a))
	return len(temp) == len(a)
	
def main():
	filename = "./image/RMA/rma.input"
	filehandle = open(filename)
	rma_input = filehandle.read()
	filehandle.close()
	rma_input = rma_input.split(" ")
	stat_filename = "./stat/RMA/RMA_Stat.txt"
	output = open(stat_filename,'w')
	number_of_reactants = int(rma_input[0])
	accuracy = int(rma_input[1])
	total = 2**accuracy
	arr = get_tuple(number_of_reactants,total)
	arr = list(filter(lambda x: check_not_zero(x), arr))
	stat_file_handle = open(stat_filename,'w')
	for i, A in enumerate(arr):
		stat = get_stat(A,accuracy)
		output.write("%d\t"%(i+1))
		element = A[0]
		output.write("%d"%element)
		for j in range(1,len(A)):
			output.write(":%d"%A[j])
		output.write("\t%d\tNA\t%d\t%d\n"%( stat[0],stat[1]-1,stat[1]))
		
	output.close()
	
		
		
	
if __name__=="__main__":
	main()
	
