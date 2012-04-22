package models.db

import java.sql.Date

class BaseEntityWithStatus(var StatusID: Long,
                           var StatusTime: Date
                            ) extends BaseEntity{
  def this() = this(0L,new Date(0,0,0))

}