package com.workshop

import java.util.Locale

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.io.Source
import scala.util.Try

// define a case class that represents a log line

object LogAnalyzer extends App {
  val regex = "^([0-9\\.]{15})\\s\\-\\s\\-\\s\\[(.*)\\]\\s\\\"(POST|PUT|GET|DELETE)\\s(.*)\\s(HTTP\\/[0-2\\.]{3})\\\"\\s([0-9]{3})\\s([0-9]{0,20})\\s\\\"(https?\\:.*)\\\"\\s\\\"(.*)\\\"$".r
  //Read from the log file
  val log = Source.fromInputStream(getClass.getResourceAsStream("/access_log")).getLines()

  // map each line to a case class that represents it
  // Split? Regex?
  // How to parse the datetime? DateTime.parse(dateTimeString, DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ssZ"))
  val logLines = log
    .map(v => Try {
      val data = regex.findAllIn(v).matchData.toSeq.head
      val parts = (1 to 9).map(i => data.group(i))

      LogLine(Request(parts), Response(parts))
    })
    .filter(_.isSuccess)
    .map(_.get)
    .toSeq


  // how many web requests?
  // how many of each status code?
  // how many distinct IPs? Wait, did you use String for IP? Maybe use a case class that will validate?
  //   Maybe some lines are bad? We want to throw them away. (When parsing return Try/Option, use flatMap instead of map on the log lines)
  // Largest response size? What was the request for it? Average response size? Sum of all responses?
  // which url have most hits? How many hits?

  println("Use println for output")
  println(s"Request count : ${logLines.length}")
  println(s"Status count : ${logLines.groupBy(ll => ll.response.code).size}")
  println(s"IPs count : ${logLines.groupBy(ll => ll.request.ip).size}")
  println(s"Largest response size : ${logLines.maxBy(ll => ll.response.size)}")
  println(s"Most popular URL : ${logLines.groupBy(ll => ll.response.url).maxBy(ll => ll._2.size)._1}")
  println(s"Most popular URL hits count : ${logLines.groupBy(ll => ll.response.url).maxBy(ll => ll._2.size)._2.size}")
}

case class LogLine(request: Request, response: Response)

case class Request(ip: String, date: DateTime, request: Method, path: String, connectionType: String)

case class Response(code: Int, size: Long, url: String, headers: String)

object Request {
  def apply(parts: Seq[String]): Request = new Request(
    parts(0),
    DateTime.parse(parts(1), DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss Z").withLocale(Locale.US)),
    Method(parts(2)).get,
    parts(3),
    parts(4)
  )
}

object Response {
  def apply(parts: Seq[String]): Response = new Response(
    parts(5).toInt,
    parts(6).toLong,
    parts(7),
    parts(8)
  )
}

sealed trait Method

object Method {
  def apply(value: String): Option[Method] = {
    value match {
      case "GET" => Some(GET)
      case "POST" => Some(POST)
      case "PUT" => Some(PUT)
      case "DELETE" => Some(DELETE)
      case _ => None
    }
  }
}

case object GET extends Method

case object POST extends Method

case object PUT extends Method

case object DELETE extends Method