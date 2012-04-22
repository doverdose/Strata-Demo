package models.db.entity

import java.sql.Date
import models.db.BaseEntityWithStatus


class Notification(val WorkflowDefinitionID: Long                  ,
                   val NotificationTypeID: Long
                    ) extends BaseEntityWithStatus {
  def this() = this(0,0)

}