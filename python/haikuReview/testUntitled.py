import untitled
import unittest

class TestUntitled(unittest.TestCase):

    def test_sample_haiku(self):
        '''simple example to start you off'''
        obj = untitled.Untitled()
        self.assertEquals([True,False],obj.checkFile("sample_haiku.txt"))

if __name__ == '__main__':
    unittest.main()

