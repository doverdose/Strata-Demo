package models.db.relation

import java.sql.Date
import models.db.BaseEntityWithStatus

class UserWorkflow(var UserID: Long,
                   var WorkflowDefinitionID: Long,
                   var StartTime: Option[Date],
                   var RunIntervalInMinutes: Option[Long]
                    ) extends BaseEntityWithStatus {
  def this() = this(0L,0L,Some(new Date(0,0,0)),Some(0L))
}