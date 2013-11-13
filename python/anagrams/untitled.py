class Untitled:

    def __init__(self,word):
	self.word=word

    def anagram(self,word):
	result=[]
	if not len(word): return result
	l=word[0]
	if(len(word)==1):return [l]
	for e in self.anagram(word[1:]):
		for i in range(len(e)+1):
			result.append(e[:i]+l+e[i:])
	return result

    def answer(self):
        result=self.anagram(self.word)
	print result
	return result
