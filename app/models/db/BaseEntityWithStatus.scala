package models.db

import java.sql.Date
import models.db.Status.Status
import org.squeryl.Table
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.boilerplate
import java.util.Calendar


class BaseEntityWithStatus(var StatusID: Status,
                           var StatusTime: Date
                            ) extends BaseEntity{
  def this() = this(Status.Undefined,new Date(0,0,0))



}