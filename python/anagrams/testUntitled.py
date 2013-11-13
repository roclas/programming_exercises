import untitled
import unittest

class TestUntitled(unittest.TestCase):

    def test_basic(self):
        '''basic case'''
	word=""
        obj = untitled.Untitled(word)
        self.assertEqual(0, len(obj.answer()))
	print "basic ok"
	
    def test_general(self):
	word="carlos"
        obj = untitled.Untitled(word)
	result=reduce(lambda x,y:x*y,range(1,len(word)+1),1)
        self.assertEqual(result, len(obj.answer()))

if __name__ == '__main__':
    unittest.main()

