class Untitled:

    def answer(self,n):
	return [(x,str(bin(x))) for x in range(2**n) if not "11" in str(bin(x))]


