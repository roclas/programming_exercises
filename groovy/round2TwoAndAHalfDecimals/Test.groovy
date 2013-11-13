import groovy.util.GroovyTestCase

class Test extends GroovyTestCase {

    void testSomething() {
	def rounder= new Rounder()
	def cases=[[22.244,22.245],[22.2024,22.2],[22.2025,22.205]]
	cases.each(){
		def rounded=rounder.round(it[0],2)
		println " testing if ${rounded} == ${it[1]} [ from ${it[0]}]..." 
        	assert rounded == it[1] : "<-- last case didn't work"
	}
    }

    void testFile0() {
	def file = new File("data0.txt")
	def rounder= new Rounder()
	file.eachLine(){
		def i=it.split(" ")
		def rounded=rounder.round(   Double.parseDouble(i[0]) ,2)
		println " testing if ${rounded} == ${i[1]} [ from ${i[0]}]..." 
        	assert rounded == Double.parseDouble(i[1]) : "<-- last case didn't work"
	}

    }

    void testFile1() {
	def file = new File("data1.txt")
	def rounder= new Rounder()
	file.eachLine(){
		def i=it.split(" ")
		def rounded=rounder.round(   Double.parseDouble(i[0]) ,2)
		println " testing if ${rounded} == ${i[1]} [ from ${i[0]}]..." 
        	assert rounded == Double.parseDouble(i[1]) : "<-- last case didn't work"
	}

    }
   
    void main(){
	testSomething()
    }
}
