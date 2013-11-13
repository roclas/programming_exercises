medios=[]
for i in [0,1,2,3,4,5,6,7,8,9]:
	for j in [0,1,2,3,4,5,6,7,8,9]:
		for k in [0,5]:
			medios.append("22.%d%d%d" % (i,j,k))
medios.append("23.000")

enteros=[]
for i in xrange(1000000):
	enteros.append( "22.%06d" % i )


i=0
j=0
while(i<len(medios) and j<len(enteros)):
	print "%s %s" %(enteros[j],medios[i])
	j+=1
	try:
		if(float(enteros[j])+0.002500001>=float(medios[i+1])): i+=1
	except: pass

