package models.db.entity

import java.sql.Date
import models.db.Status._
import java.util.Calendar
import models.db.{ReChargeSchema, BaseEntityWithStatus}
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._


class TaskExecution(val WorkflowExecutionID: Long,
                    val TaskDefinitionID: Long,
                    val StartTime: Date,
                    val StartSessionID: Long,
                    var EndTime: Option[Date],
                    var EndSessionID: Option[Long],
                    val Priority: Long
                    ) extends BaseEntityWithStatus {
  def this() = this(0L,0L,new Date(0,0,0),0L,Some(new Date(0,0,0)), Some(0L),0L)

  protected def updateStatus(status:Status) = {
  inTransaction {
    this.StatusID = status
    this.StatusTime = new java.sql.Date(Calendar.getInstance.getTime.getTime)
    ReChargeSchema.taskExecutions.update(this)
  }
  }
}