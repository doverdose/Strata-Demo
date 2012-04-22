package models.db.entity

import models.db.BaseEntity

class WorkflowDefinition(val Name: String,
                         var RootWorkflowDefinitionID: Option[Long],
                         val ParentWorkflowDefinitionID: Option[Long]
                          ) extends BaseEntity {
  def this() = this("",Some(0L),Some(0L))

}