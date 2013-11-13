class Untitled:

    def __init__(self):
	self.doors={}
	for i in range(100): self.doors[i]=False

    def visitEveryNthDoor(self,n):
	for i in range(n,100,n+1):
		self.doors[i]^=True

    def answer(self):
	for i in range(100):self.visitEveryNthDoor(i)
	return reduce(lambda t,x:(t+1 if(self.doors[x]==True) else t), self.doors, 0)


