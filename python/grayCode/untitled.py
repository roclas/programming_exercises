class Untitled:

    def encode(self,number):
	g=[]
	b=bin(number)[2:]
	for i in range(len(b)):
		if (b[i-1] != "1" or i==0): g.append(int(b[i]))
  		else: g.append(int(not int(b[i])))
	return g


    def decode(self,bnumber):
	g=[]
	for i in range(len(bnumber)):
		if (i==0): g.append(bnumber[i])
  		else: g.append(bnumber[i]^g[i-1])
	return g
	


