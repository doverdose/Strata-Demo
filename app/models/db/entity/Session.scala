package models.db.entity

import java.sql.Date
import models.db.{ReChargeSchema, BaseEntity}
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._


object Session {
  val Today: java.util.Date  = new java.util.Date();

  def createSession(userID : Option[Long]) : Session = {
    inTransaction {
      val session = new Session(StartDate = new Date(Today.getTime),
                                EndDate = None,
                                UserID = userID,
                                ApplicationVersionID = 1L)
      ReChargeSchema.sessions.insert(session)
      session
    }
  }

}

class Session(val StartDate: Date,
              var EndDate: Option[Date],
              val UserID: Option[Long],
              val ApplicationVersionID: Long
              ) extends BaseEntity {

  def this() = this(new Date(0,0,0), Some(new Date(0,0,0)),Some(0L),0L)

  def end = {
    inTransaction {
      this.EndDate = Some(new Date(Session.Today.getTime))
      ReChargeSchema.sessions.update(this)
    }
  }
}