class Untitled:

    def neighbours(self,m,i,j):
	result=[]
	try: result.append(m[i-1][j-1])
	except: pass
	try: result.append(m[i-1][j])
	except: pass
	try: result.append(m[i-1][j+1])
	except: pass
	try: result.append(m[i][j-1])
	except: pass
	try: result.append(m[i][j+1])
	except: pass
	try: result.append(m[i+1][j-1])
	except: pass
	try: result.append(m[i+1][j])
	except: pass
	try: result.append(m[i+1][j+1])
	except: pass
	return result

    def answer(self,m):
	result=[l[:] for l in m] #copy the input matrix
	for i in range(len(m)):
		for j in range(len(m[i])):
			neighbours=self.neighbours(m,i,j)
			actualState=m[i][j]
			result[i][j]={
				0:0,
				1:0,
				2:0 if actualState==0 else 1,
				3:1,
				4:0,
				5:0,
				6:0,
				7:0,
				8:0,
			}[sum(neighbours)]
	return result


