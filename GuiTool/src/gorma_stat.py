from gorma import get_stat

def main():
	filename = "./image/Gorma/gorma.input"
	filehandle = open(filename)
	gorma_input = filehandle.read()
	filehandle.close()
	accuracy = int(gorma_input)
	stat_filename = "./stat/Gorma/Gorma_Stat.txt"
	output = open(stat_filename,'w')
	
	for i in range(1,2**accuracy):
		stat = get_stat(i,accuracy)
		output.write("%d\t%d\t%d\t%d\t%d\t%d\n"%(i,i,stat[3],stat[0],stat[1],stat[2]))
	output.close()
	
if __name__=="__main__":
	main()
