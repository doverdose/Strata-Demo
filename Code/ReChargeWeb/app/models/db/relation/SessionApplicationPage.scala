package models.db.relation

import java.sql.Date


class SessionApplicationPage(val SessionID: Long,
                             val ApplicationPageID: Long,
                             val StartTime: Date,
                             var EndTime: Option[Date]
                              ) {
  def this() = this(0,0,new Date(0,0,0),Some(new Date(0,0,0)))

}