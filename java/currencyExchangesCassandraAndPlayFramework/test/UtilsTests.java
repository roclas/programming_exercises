import org.junit.Test;

import play.test.UnitTest;
import utils.DateCalculator;

/**
* Testing that calls to the API can retrieve Currency EntityEntity Objects
*/
public class UtilsTests extends UnitTest{

    /**
     * Testing that we can get the date 90 days ago*
     */
    @Test
    public void getTheDay90DaysAgoFromToday(){
        String result = DateCalculator.getDate90DaysAgo();
        System.out.println(String.format("the date 90 days ago is: %s",result));
        assert(result.length()>4);
    }
}
   

