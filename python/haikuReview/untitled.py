import re
class Untitled:

    def __init__(self):
	self.re=re.compile("^[a-z]*$")
	self.v_re=re.compile("[aeiouy]*")

    def checkFile(self,filename):
	file=open(filename)
	result=[]
	for l in file:result.append(self.checkHaiku(l))
	return result

    def checkHaiku(self,haiku):
	lines=haiku.split("/")
	result=True
	if(len(lines)!=3):result&=False
	len0=self.countSyllabes(lines[0])
	len1=self.countSyllabes(lines[1])
	len2=self.countSyllabes(lines[2])
	if self.re.match("".join(lines)):result&=False
	if(len0!=5 or len1!=7 or len2!=5):result&=False
	print "%s,%s,%s,%s" %(len0,len1,len2,"Yes" if result==True else "No")
	return result

    def countSyllabes(self,word):
	return len(filter(lambda x:x,self.v_re.findall(word)))


