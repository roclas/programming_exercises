class Untitled:

    def createDigit(self,d):
	return{
		"0":"._.\n|.|\n|_|\n",
		"1":"...\n..|\n..|\n",
		"2":"._.\n._|\n|_.\n",
		"3":"._.\n._|\n._|\n",
		"4":"...\n|_|\n..|\n",
		"5":"._.\n|_.\n._|\n",
		"6":"._.\n|_.\n|_|\n",
		"7":"._.\n..|\n..|\n",
		"8":"._.\n|_|\n|_|\n",
		"9":"._.\n|_|\n..|\n",
	}[str(d)]
    
    def answer(self,number):
	result=[]
	for i in range(3):
		result.append("")
		for n in str(number):
			result[i]+=" %s" % self.createDigit(n).split('\n')[i]
	return "\n".join([s.strip() for s in result])

