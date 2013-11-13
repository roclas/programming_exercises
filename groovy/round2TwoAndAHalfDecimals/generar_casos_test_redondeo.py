medios=[]
for i in [0,1,2,3,4,5,6,7,8,9]:
	for j in [0,1,2,3,4,5,6,7,8,9]:
		for k in [0,5]:
			medios.append("38765.%d%d%d" % (i,j,k))
medios.append("38766.000")

enteros=[]
for i in xrange(10000):
	enteros.append( "38765.%04d" % i )


i=0
j=0
while(i<len(medios) and j<len(enteros)):
	print "%s %s" %(enteros[j],medios[i])
	j+=1
	try:
		if(float(enteros[j])+0.00251>=float(medios[i+1])): i+=1
	except: pass

