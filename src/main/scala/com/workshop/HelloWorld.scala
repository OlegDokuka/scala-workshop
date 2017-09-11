package com.workshop

object HelloWorld extends App {
  val reg = "/^([0-9\\.]{15})\\s\\-\\s\\-\\s\\[(.*)\\]\\s\\\"(POST|PUT|GET|DELETE)\\s(.*)\\s(HTTP\\/[0-2\\.]{3})\\\"\\s([0-9]{3})\\s([0-9]{0,20})\\s\\\"(https?\\:.*)\\\"\\s\\\"(.*)\"".r

  println(reg.pattern.matcher("54.231.186.143 - - [18/Aug/2017:18:37:29 +0300] \"POST /explore HTTP/1.0\" 200 4953 \"http://hicks.com/register.html\" \"Mozilla/5.0 (iPod; U; CPU iPhone OS 4_1 like Mac OS X; sl-SI) AppleWebKit/532.44.1 (KHTML, like Gecko) Version/4.0.5 Mobile/8B118 Safari/6532.44.1\""))
}
