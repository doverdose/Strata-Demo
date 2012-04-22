package models.db.entity

import models.db.BaseEntity

class TaskDefinition(val Name: String,
                     val TaskTypeID: Long,
                     val Question: String,
                     val Choices: Option[String]
                      ) extends BaseEntity {
  def this() = this("",0,"",Some(""))

}