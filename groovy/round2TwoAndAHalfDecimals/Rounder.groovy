class Rounder {
    def round(number,n) { 
	def sign=number>0?1:-1
	return ((double) (long) (number*(2*(10**n))+ sign*0.500000001))/(2*(10**n));
    }
}
       
