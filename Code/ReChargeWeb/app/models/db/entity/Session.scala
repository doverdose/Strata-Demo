package models.db.entity

import java.sql.Date
import models.db.BaseEntity


class Session(val StartDate: Date,
              var EndDate: Option[Date],
              val UserID: Option[Long],
              val ApplicationVersionID: Long
              ) extends BaseEntity {

  def this() = this(new Date(0,0,0), Some(new Date(0,0,0)),Some(0L),0L)

}