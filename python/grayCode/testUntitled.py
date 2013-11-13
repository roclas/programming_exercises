import untitled
import unittest

class TestUntitled(unittest.TestCase):

    def test_str(self):
        '''simple example to start you off'''
        obj = untitled.Untitled()
	original=22
	encoded=obj.encode(original)
	print "original=%s (%s), encoded=%s" % (original,bin(original)[2:],encoded)
	decoded=obj.decode(encoded)
        self.assertEqual([int(x) for x in bin(original)[2:]],decoded)


    def test_complete(self):
        '''encode and decode all numbers from 0 to 32'''
        obj = untitled.Untitled()
	for i in range(32):
		encoded=obj.encode(i)
		enc="".join([str(x) for x in encoded])
		print "i=%s(%s)->%s(%s)" % (i,bin(i)[2:],int(enc,2),enc)
        	self.assertEqual([int(x) for x in bin(i)[2:]],obj.decode(encoded))



if __name__ == '__main__':
    unittest.main()

