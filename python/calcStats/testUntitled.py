import untitled
import unittest

class TestUntitled(unittest.TestCase):

    def test_min_max_len(self):
        '''test the min, max and len functions'''
        obj = untitled.Untitled([6,9,15,-2,92,11])
        self.assertEqual(-2, obj.minimum())
        self.assertEqual(92, obj.maximum())
        self.assertEqual(6, obj.length())

    def test_avg(self):
        '''test the avg function'''
        obj = untitled.Untitled([6,9,15,-2,92,11])
        self.assertEqual(int(21.833333*1000), int(obj.avg()*1000))




if __name__ == '__main__':
    unittest.main()

