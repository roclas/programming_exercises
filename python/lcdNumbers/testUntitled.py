import untitled
import unittest

class TestUntitled(unittest.TestCase):
	
    @classmethod
    def setUpClass(self):
        self.obj = untitled.Untitled()

    def test_0(self):
        print '''test number 0'''
	representation="._.\n|.|\n|_|"
	ans=self.obj.answer(0)
        self.assertEqual(representation,ans )

    def test_3(self):
        print '''test number 3'''
	representation="._.\n._|\n._|"
	ans=self.obj.answer(3)
        self.assertEqual(representation, ans)

    def test_all(self):
        print '''test all numbers '''
	print "\n%s\n" % self.obj.answer(9876543210)


if __name__ == '__main__':
    unittest.main()

