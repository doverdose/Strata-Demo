package models.db.entity

import java.sql.Date
import models.db.BaseEntityWithStatus


class WorkflowExecution(val UserID: Long,
                        val StartTime: Date,
                        var EndTime: Option[Date]
                         ) extends BaseEntityWithStatus {
  def this() = this(0L,new Date(0,0,0),Some(new Date(0,0,0)))

}