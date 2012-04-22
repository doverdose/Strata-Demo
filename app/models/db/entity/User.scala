package models.db.entity

import java.sql.Date
import models.db._
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

object User {

  def getUserByEmail(email:String) : Option[User] = {
    val results = from(ReChargeSchema.users) (user => where (user.Email === email) select(user))
    if (results.size == 0) None else Some(results.head)
  }

  def validateUserPassword(user:User,password:String) : Boolean = {
    user.Password == password
  }

}



class User(val FirstName: String,
           val LastName: String,
           val Email: String,
           val Password: String,
           val RoleTypeID: Long,
           val CreatedDate: Date) extends BaseEntity {
  def this() = this("","","","",0L,new Date(0,0,0))

}