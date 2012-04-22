package models.db.relation

import java.sql.Date
import models.db.BaseEntityWithStatus


class UserCondition(val AssignedToUserID: Long,
                    val AssignedByUserID: Long,
                    val ConditionID: Long
                     ) extends BaseEntityWithStatus {
  def this() = this(0,0,0)

}