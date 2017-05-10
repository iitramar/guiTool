from minmix import generate_stat
from fractions import gcd
from itertools import product
def get_tuple(length, total):
	return filter(lambda x:sum(x)==total,product(range(total+1),repeat=length))

def check_not_zero(a):
	temp = list(filter(lambda x: x!=0,a))
	return len(temp) == len(a)
	
def main():
	filename = "./image/Minmix/minmix.input"
	filehandle = open(filename)
	rma_input = filehandle.read()
	filehandle.close()
	rma_input = rma_input.split(" ")
	stat_filename = "./stat/Minmix/Minmix_Stat.txt"
	output = open(stat_filename,'w')
	number_of_reactants = int(rma_input[0])
	accuracy = int(rma_input[1])
	total = 2**accuracy
	arr = get_tuple(number_of_reactants,total)
	arr = list(filter(lambda x: check_not_zero(x), arr))
	stat_file_handle = open(stat_filename,'w')
	for i, A in enumerate(arr):
		print A
		gg = reduce(gcd,A)
		A = [j/gg for j in A]
		s_array = [[accuracy for k in range(2)] for j in range(number_of_reactants)]
		for j in range(number_of_reactants):
			s_array[j][0] = A[j]
		stat = generate_stat(s_array)
		output.write("%d\t"%(i+1))
		element = A[0]
		output.write("%d"%element)
		for j in range(1,len(A)):
			output.write(":%d"%A[j])
		output.write("\t%d\tNA\t%d\t%d\n"%( stat[1],stat[2]-1,stat[2]))
		
	output.close()
	
		
		
	
if __name__=="__main__":
	main()
