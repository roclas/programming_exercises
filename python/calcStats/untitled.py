class Untitled:

    def __init__(self,mylist):
	self.elems=mylist
	
    def minimum(self): return min(self.elems)
    def maximum(self): return max(self.elems)
    def length(self): return len(self.elems)
    def avg(self): return sum(self.elems)/float(self.length())


