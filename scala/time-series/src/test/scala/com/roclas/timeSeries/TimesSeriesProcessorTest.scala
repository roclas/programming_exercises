/**
 * Created by carlos on 6/03/15.
 */

package com.roclas.timeSeries
import org.scalatest._


class TimeSeriesProcessorTest extends FlatSpec with Matchers {


  "The TimeSeriesProcessor" should "be able to print a line" in {
    val p= new TimeSeriesProcessor with MockOutput
    //the logger will show an errror because lines are not well formated
    p.getElem("123 2.3")
    p.getElem("124 2.2")
    p.getElem("125 2.1")
    p.messages should not be empty
  }

  "It" should "be able to print something after the first window has been completed" in {
    val p= new TimeSeriesProcessor with MockOutput
    p.processFile("src/main/resources/data.txt")
    p.messages should not be empty
  }
  
}
