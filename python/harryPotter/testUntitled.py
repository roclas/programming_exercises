import untitled
import unittest

class TestUntitled(unittest.TestCase):

    def test_str(self):
        '''simple example to start you off'''
        obj = untitled.Untitled()
	basket=[1,1,2,2,3,3,4,5]
	print "original list=%s" % basket
	obj.printCombinations(basket)

	basket=[1,1,1,1,2,3,3,4,4,4,4,4,4,5,5,5,5]
	print "original list=%s" % basket
	obj.printCombinations(basket)
        #self.assertEqual(10, obj.answer())



if __name__ == '__main__':
    unittest.main()

