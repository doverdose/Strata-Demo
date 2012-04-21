package models.db

import org.squeryl.KeyedEntity
import org.squeryl.annotations._

class BaseEntity(@Column(name="ID") var id: Long
                 ) extends KeyedEntity[Long]{
  def ID = id
  def this() = this(0)

}