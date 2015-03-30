import scala.annotation.tailrec
class factorial {
        @tailrec
        final def calculate(x: Int, y: Int=1): Int = {
                if(x!=1) calculate(x-1,y*x)
		else 1
        }
}
