/**
 * * 
 * Created by carlos on 4/03/15.
 */
package com.roclas.timeSeries

import scala.io.Source

import org.log4s.getLogger

import scala.collection.mutable.ListBuffer

class TimeSeriesProcessor extends Output{

  val logger = getLogger
  val period = 60 //we will use a period of 60s

  /*
   * The lists containing the
   * elements that need to be printed
   * The decision to make it mutable is just for pure efficiency
   */
  var list= ListBuffer[Option[(Int,Double)]]()

  /**
   * Evaluated lazily(will not crash no matter how big the file is)*
   * @param path
   */
  def processFile(path:String)={
    lazy val lines = Source.fromFile(path).getLines
    print("T          V        N   RS       MinV       MaxV\n__________________________________________________")
    lines.toStream.foreach{getElem(_)}
  }

  /**
   * Inserts element in the list and prints current windows*
   * @param s
   */
  def getElem(s: String) = {
    list+=parseLine(s)
    getWindow()
    val result = list.foldLeft((0,0.0,0, 99999.9, 0.0, 0.0)) {
      case ((timestamp, value, count, min, max, sum), e) => (
        e.get._1,//these two first values could be taken out
        e.get._2,//but for the sake of understandability they are in
        count + 1,
        math.min(min, e.get._2),
        math.max(max, e.get._2),
        sum + e.get._2
        )
    }
    print(f"${result._1}%10d ${result._2}%07f ${result._3}%03d ${result._6}%07f ${result._4}%07f ${result._5}%07f")
  }

  /**
   * removes elements as it prints the finished windows before new inserts*
   */
  //more beautiful but less efficient one-line alternative to getWindow
  //def getWindow()={list=list.dropWhile(e=>e.get._1+period<=list.last.get._1) }
  def getWindow()={
    val last=list.last.get._1
    var first=list(0).get._1
    while(list.nonEmpty && first+period<=last){
        list.remove(0)
        if(list.nonEmpty)first=list(0).get._1
    }
  }

  /**
   * parses a single line*
   * @param s
   * @return
   */
  def parseLine(s: String):Option[(Int,Double)] = {
   try {
    val e=s.split("\\s+")
    Some(e(0).toInt,e(1).toDouble)
   } catch {
    case e:Exception => {
      logger.error(s"probably the string '${s}' is not well formated")
      None
    }
   }
  }
}

object Main {
  /**
   * main method* 
   * @param args
   */
  def main(args: Array[String]): Unit = {
    val p=new TimeSeriesProcessor()
    if (args.length == 1){
      p.processFile(args(0))
      //p.processFile("src/main/resources/data.txt")
    }else println("wrong number of params")
  }
}

  
