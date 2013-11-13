import untitled
import unittest

class TestUntitled(unittest.TestCase):

    def test_str(self):
        '''scores 0'''
        obj = untitled.Untitled()
        self.assertEqual(9, obj.score("9"))
        self.assertEqual(62, obj.score("12|13|23|34|22|37|31|81|18|25||"))
        self.assertEqual(90, obj.score("9-|9-|81|9-|27|9-|9-|9-|9-|9-||"))
        self.assertEqual(10, obj.score("9/"))
        self.assertEqual(10, obj.score("5/"))
        self.assertEqual(25, obj.score("5/|5/"))
        self.assertEqual(15, obj.score("5/||5"))
        self.assertEqual(30, obj.score("5/|5/||5"))
        self.assertEqual(150, obj.score("5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5"))
        self.assertEqual(30, obj.score("X|X"))
        self.assertEqual(270, obj.score("X|X|X|X|X|X|X|X|X|X||"))
        self.assertEqual(300, obj.score("X|X|X|X|X|X|X|X|X|X||XX"))

if __name__ == '__main__':
    unittest.main()

