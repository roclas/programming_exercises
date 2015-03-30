public class factorial_java2{
        final public int calculate(int x){
		int result=1;
		for(int i=x;i-->1;){
			result*=i;
		}
		return result;
        }
}
