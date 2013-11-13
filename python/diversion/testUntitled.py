import untitled
import unittest

class TestUntitled(unittest.TestCase):

    def test_str(self):
        '''simple example to start you off'''
        obj = untitled.Untitled()
	result=obj.answer(3)
	print result
        self.assertEqual(5, len(result))

if __name__ == '__main__':
    unittest.main()

