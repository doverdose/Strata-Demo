package models.db.relation

import models.db.BaseEntity


class WorkflowDefinitionTaskDefinition(val WorkflowDefinitionID: Long,
                                       val TaskDefinitionID: Long,
                                       val ParentTaskDefinitionID: Option[Long]
                                        ) extends BaseEntity {
  def this() = this(0L,0L,Some(0L))

}