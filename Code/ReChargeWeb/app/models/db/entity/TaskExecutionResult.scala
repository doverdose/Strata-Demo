package models.db.entity

import models.db.BaseEntityWithStatus

class TaskExecutionResult(val TaskExecutionID: Long,
                          val Results: String
                           ) extends BaseEntityWithStatus {
  def this() = this(0,"")

}