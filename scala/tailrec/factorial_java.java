public class factorial_java{
        final public int calculate(int x){
		int result=1;
		for(int i=1;i<=x;i++){
			result*=i;
		}
		return result;
        }
}
