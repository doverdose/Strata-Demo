package models.db.entity

import java.sql.Date
import models.db.{ReChargeSchema, BaseEntityWithStatus}
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.boilerplate
import models.db.Status._
import java.util.Calendar


class WorkflowExecution(val UserID: Long,
                        val WorkflowDefinitionID: Long,
                        val StartTime: Date,
                        var EndTime: Option[Date] = None
                         ) extends BaseEntityWithStatus {
  def this() = this(0L,0L,new Date(0,0,0),Some(new Date(0,0,0)))

  private var _taskExecutions : Option[List[TaskExecution]] = None

  def getTaskExecutions : List[TaskExecution] = {
    if (!_taskExecutions.isDefined) {
       transaction {
        val results = from(ReChargeSchema.taskExecutions)((te) =>
                      where (te.id === id) select(te))
        _taskExecutions =  Some(results.toList)
      }
    }
    _taskExecutions.get
  }

    protected def updateStatus(status:Status) = {
    inTransaction {
      this.StatusID = status
      this.StatusTime = new java.sql.Date(Calendar.getInstance.getTime.getTime)
      ReChargeSchema.workflowExecutions.update(this)
    }
  }

}