import untitled
import unittest

class TestUntitled(unittest.TestCase):

    def test_length(self):
        '''simple example to start you off'''
        obj = untitled.Untitled()
	m0=[
		[0,0,0,0,0,0,0,0],
		[0,0,0,0,1,0,0,0],
		[0,0,0,1,1,0,0,0],
		[0,0,0,0,0,0,0,0]
	]
        self.assertEqual(3, sum(obj.neighbours(m0,1,3)))

	
    def test_answer(self):
        '''simple example to start you off'''
        obj = untitled.Untitled()
	m0=[
		[0,0,0,0,0,0,0,0],
		[0,0,0,0,1,0,0,0],
		[0,0,0,1,1,0,0,0],
		[0,0,0,0,0,0,0,0]
	]
	r0=[
		[0,0,0,0,0,0,0,0],
		[0,0,0,1,1,0,0,0],
		[0,0,0,1,1,0,0,0],
		[0,0,0,0,0,0,0,0]
	]
	for l in m0: print ",".join([str(e) for e in l])
	print "\n\n\n"
	for l in obj.answer(m0): print ",".join([str(e) for e in l])
	print "\n\n\n"
        self.assertEqual(r0, obj.answer(m0))

    def test_execution(self):
	import time
        obj = untitled.Untitled()
	m0=[
		[1,0,1,0,0,0,0,0],
		[0,1,1,0,0,0,0,0],
		[0,1,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,0],
	]
	current=m0
	for i in range(100):
		future=obj.answer(current)
		for l in current: print ",".join([str(e) for e in l])
		print "\n\n"
		time.sleep(1)
		current=future
		
		

if __name__ == '__main__':
    unittest.main()

