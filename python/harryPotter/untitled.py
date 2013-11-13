# -*- coding: latin-1 -*-
import math
class Untitled:

    def printCombinations(self,l):
     best=9999999999999999999
     for i in self.combinations(l): 
      p=self.price(i)
      if(p<best):best=p
      print i,p,"€","<-best so far" if p==best else ""

    def price(self,l):
	total=0
	for li in l:
		total+=8*len(li)*{
			"1":1,
			"2":0.95,
			"3":0.9,
			"4":0.8,
			"5":0.75
		}[str(len(li))]
	return total
		
	
    def combinations(self,l):
	result=[]
	new=self.initialSolution(l)
	while(new):
	 result.append(list(new))
	 new=self.nivelate(result[len(result)-1])
	return result

    def nivelate(self,l):
	lr=[list(x) for x in list(sorted(l))]
	le=[len(x) for x in lr]
	im=le.index(max(le))
	for l0 in lr:
		dif=list(set(lr[im])-set(l0))
		if(len(dif)>1):
			#print "moving %s from %s to %s" % (dif[0],lr[im],l0)
			lr[im].remove(dif[0])
			l0.append(dif[0])
			return lr
	return []
	

    def initialSolution(self,l):
	result=[]
	for e in l:
		appended=False
		for g in result:
			try: g.index(e)
			except:
				g.append(e)
				appended=True
				break
		if not (appended): result.append([e])
	return result


