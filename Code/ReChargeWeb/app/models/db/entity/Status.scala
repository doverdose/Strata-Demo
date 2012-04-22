package models.db.entity

import models.db.BaseEnumEntity

class Status(val IsFinal: Boolean) extends BaseEnumEntity {
  def this() = this(false)

}