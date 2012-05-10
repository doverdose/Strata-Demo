package models.db

import entity._
import scala.collection.mutable.Map

object Cache {

  var Users : Map[Long, User] = Map.empty[Long, User]

  def getUserByID(id : Long) : Option[User] = {
    Users.get(id)
  }

  def addUser(user : User) = {
    Users(user.id) = user
  }

  def removeUser(user : User) : Option[User] = {
    Users.get(user.id)
  }

  def invalidate = {
    Users = Map.empty[Long, User]
  }

}