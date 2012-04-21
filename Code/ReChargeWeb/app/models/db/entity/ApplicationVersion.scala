package models.db.entity

import java.sql.Date
import models.db.BaseEntity

class ApplicationVersion(val ApplicationID: Long,
                         val Version: String,
                         val CreatedDate: Date,
                         val AndroidSDKVersion: Option[String],
                         val JavaVersion: Option[String]
                         ) extends BaseEntity {
  def this() = this(0L,"",new Date(0,0,0), Some(""),Some(""))
}