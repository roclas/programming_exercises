class Untitled:

    def __init__(self):
	self.coins=[25,10,5,1]
	self.objective=100

    def answer(self):
	result=[]
	objective=self.objective
	for c in self.coins:
		if not(result): result+=self.combine(c,objective)
		else:
			result2=[]
			for r in result:
				try:objective2=objective-sum(r)
				except Exception,e: print e
				for m in self.combine(c,objective2):
					result2+=[r+m]
			if(result2):result=result2
	return [x for x in result if sum(x)==self.objective]


    def combine(self,coin,objective):
	combinations=[]
	partial=[]
	while(sum(partial)<=objective):
		combinations.append(list(partial))
		partial.append(coin)
	return combinations
