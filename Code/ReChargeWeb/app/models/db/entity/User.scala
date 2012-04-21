package models.db.entity

import java.sql.Date
import models.db.BaseEntity


class User(val FirstName: String,
           val LastName: String,
           val Email: String,
           val Password: String,
           val RoleTypeID: Long,
           val CreatedDate: Date) extends BaseEntity {
  def this() = this("","","","",0L,new Date(0,0,0))

}