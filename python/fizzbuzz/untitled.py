class Untitled:


    def answer(self):
	ans=[]
	for i in range(1,101):
		result=""
		if not (i % 3): result+="Fizz"
		if not (i % 5): result+="Buzz"
		if not (result): result= "%s" % i
		ans.append(result)
	return ans
		


