object HelloWorld
{
  def sayHello=(name:String) => "Hello, "+name

  def sayHello2=(opt:Int)=> opt match{
		case 1 => "hi WORLD"
		case 2 => "hi wORlD"
		case 3 => "hi wooooorrrlllddd"
		case _ => "hi world"
  }

  def main (args:Array[String])
  {
  	println ("Hello, world!")
	println(sayHello("Carlos"))
	println(sayHello2(1))
  }
}
