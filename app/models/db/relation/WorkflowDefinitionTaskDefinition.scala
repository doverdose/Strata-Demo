package models.db.relation

import models.db.BaseEntity


class WorkflowDefinitionTaskDefinition(var WorkflowDefinitionID: Long,
                                       var TaskDefinitionID: Long,
                                       var ParentTaskDefinitionID: Option[Long]
                                        ) extends BaseEntity {
  def this() = this(0L,0L,Some(0L))

}