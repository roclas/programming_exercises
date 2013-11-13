import untitled
import unittest

class TestUntitled(unittest.TestCase):

    def test_str(self):
        '''simple example to start you off'''
        obj = untitled.Untitled()
        result=obj.answer()
        self.assertTrue(len(result)>0)
	print "\n".join(result)

if __name__ == '__main__':
    unittest.main()

