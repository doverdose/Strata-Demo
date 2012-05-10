package models.db.entity

import models.db.{TaskType, BaseEntity}
import models.db.TaskType.TaskType


class TaskDefinition(val Name: String,
                     val TaskTypeID: TaskType,
                     val Question: String,
                     val Choices: Option[String]
                      ) extends BaseEntity {
  def this() = this("",TaskType.TrueFalse,"",Some(""))

}