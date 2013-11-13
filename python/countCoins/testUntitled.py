import untitled
import unittest

class TestUntitled(unittest.TestCase):

    def test_str(self):
        '''simple example to start you off'''
        obj = untitled.Untitled()
	combinations0=obj.combine(25,100)
        self.assertEqual(5, len(combinations0))
	answer=obj.answer()
	print "existen %d posibles cambios" % len(answer)
	for a in answer: 
        	self.assertEqual(sum(a), 100)
		print a
        self.assertGreater(len(answer), len(combinations0))



if __name__ == '__main__':
    unittest.main()

