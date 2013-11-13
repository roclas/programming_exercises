import sys
class Untitled:

    def _score_x_case(self,frames,i):
		total=0
		if ((i+1)<len(frames)):
			if('X' in frames[i+1]): total+=10
			else: total+=self.score(frames[i+1])
			if ((i+2)<len(frames)):total+=self.score(frames[i+2][0])
		return total
				
    def score(self,s):
	extra=0
	parts=s.split("||")
	frames=[x for x in parts[0].split("|")]
	try: extra=self.score(parts[1])
	except:pass
	try: 
	   if(len(parts[1])>1):
		if("X" in frames[-2] or "/" in frames[-2] ):
			extra+=self.score(parts[1][0])
	except:pass
	total=0
	for i in range(len(frames)):
		sf=frames[i]
		#sys.stdout.write("\n%s ->" % ("|".join(frames[:i+1])))
		if('/' in sf):
		  total+=10 
		  if((i+1)<len(frames)):total+=self.score(frames[i+1][0])
		  #sys.stdout.write("%d\n" % total)
		elif('XX' in sf):total+=20
		elif('X' in sf):total+=10+self._score_x_case(frames,i)
		else:
			try: total+=int(sf[0])
			except: pass
			try: total+=int(sf[1])
			except: pass
	return total+extra
