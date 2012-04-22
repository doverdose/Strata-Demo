package models.db.relation

import java.sql.Date


class UserRelationship(val UserID1: Long,
                       val UserID2: Long,
                       val RelationshipTypeID: Long,
                       val CreatedDate: Date
                        ) {
  def this() = this(0,0,0,new Date(0,0,0))

}