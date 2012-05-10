package models.db.entity

import models.db.{ReChargeSchema, BaseEntity, Status}
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import java.sql.Date
import java.util.Calendar

class WorkflowDefinition(val Name: String,
                         var RootWorkflowDefinitionID: Option[Long],
                         val ParentWorkflowDefinitionID: Option[Long]
                          ) extends BaseEntity {
  def this() = this("",Some(0L),Some(0L))

  private var _taskDefinitions : Option[List[TaskDefinition]] = None

  def getTaskDefinitions : List[TaskDefinition] = {
    if (!_taskDefinitions.isDefined) {
       inTransaction {
        val results = from(ReChargeSchema.taskDefinitions,ReChargeSchema.workflowDefinitionTaskDefinitions)((td,wdtd) => where (wdtd.WorkflowDefinitionID === id and wdtd.TaskDefinitionID === td.id) select(td))
        _taskDefinitions =  Some(results.toList)
      }
    }
    _taskDefinitions.get
  }

  def execute(user:User) : WorkflowExecution = {
    inTransaction {
      val workflowExecution = new WorkflowExecution(UserID = user.id,
                                                    WorkflowDefinitionID = this.id,
                                                    StartTime = (new java.sql.Date(Calendar.getInstance.getTime.getTime)),
                                                    EndTime = None
                                                    )
      workflowExecution.StatusID = Status.InProgress
      ReChargeSchema.workflowExecutions.insert(workflowExecution)
      workflowExecution
    }
  }

}