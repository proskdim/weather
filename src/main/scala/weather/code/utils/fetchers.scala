package weather.code.utils

import ujson.*
import scala.collection.mutable

def fetchObj(
    value: Value,
    key: String
): mutable.Map[String, Value] = value(key).obj

def fetchNum(value: Value, key: String): Double = value(key).num

def fetchStr(value: Value, key: String): String = value(key).str
