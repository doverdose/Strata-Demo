package models.db.entity

import java.sql.Date
import models.db.BaseEntity


class Application(val Name: String,
                  val CreatedDate:  Date,
                  val ApplicationRoot: Option[String],
                  val URL: Option[String]
                  ) extends BaseEntity {
  def this() = this("",new Date(0,0,0),Some(""),Some(""))
}