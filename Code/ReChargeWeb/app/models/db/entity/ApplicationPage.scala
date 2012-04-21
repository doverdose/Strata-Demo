package models.db.entity

import models.db.BaseEntity

class ApplicationPage(val ApplicationID: Long,
                      val Name: String,
                      val URL: String
                       ) extends BaseEntity {
  def this() = this(0,"","")
}