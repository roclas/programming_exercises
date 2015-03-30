package com.roclas.timeSeries

/**
 * Created by carlos on 6/03/15.
 */
trait MockOutput extends Output {
    var messages: Seq[String] = Seq()

    override def print(s: String) = {
      println(s)
      messages = messages :+ s
    }
  }
