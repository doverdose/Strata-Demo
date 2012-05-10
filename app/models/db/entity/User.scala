package models.db.entity

import java.sql.Date
import models.db._
import models.db.entity._
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.boilerplate
import org.squeryl.annotations._
import models.db.Status.Status

object User {

  def getByEmail(email:String) : Option[User] = {
    inTransaction {
      val results = from(ReChargeSchema.users) (user => where (user.Email === email) select(user))
      val user = if (results.size == 0) None else Some(results.head)
      if (user.isDefined) Cache.addUser(user.get)
      user
    }
  }

  def getByID(id : Long) : Option[User] = {
    val cachedUser = Cache.getUserByID(id)
    if (cachedUser.isDefined) cachedUser
    else {
      inTransaction {
        val results = from(ReChargeSchema.users) (user => where (user.id === id) select(user))
        val user = if (results.size == 0) None else Some(results.head)
        if (user.isDefined) Cache.addUser(user.get)
        user
      }
    }
  }

  def createUser(firstName : String,
              lastName : String,
              email : String,
              password : String,
              roleTypeID : Long,
              createdDate: Date) : User = {
    val user = new User(FirstName = firstName,
                    LastName=lastName,
                    Email=email,
                    Password=password,
                    RoleTypeID=roleTypeID,
                    CreatedDate=createdDate)
    inTransaction{
      ReChargeSchema.users.insert(user)
      Cache.addUser(user)
    }
    user
  }

}


class User(val FirstName: String,
           val LastName: String,
           val Email: String,
           val Password: String,
           val RoleTypeID: Long,
           val CreatedDate: Date) extends BaseEntity {
  def this() = this("","","","",0L,new Date(0,0,0))

  @Transient
  private var _activeWorkflowExecutions : Option[List[WorkflowExecution]] = None
  @Transient
  private var _availableWorkflowDefinitions : Option[List[WorkflowDefinition]] = None
  @Transient
  private var _currentSession : Option[models.db.entity.Session] = None

  def validatePassword(password:String) : Boolean = {
    Password == password
  }

  def getWorkflowDefinition(limitStatuses : List[Status] = List.empty[Status]) : Option[WorkflowDefinition] = {
    transaction {
      val results = from(ReChargeSchema.workflowDefinitions, ReChargeSchema.userWorkflowDefinitions)((w, uwd) => where (uwd.WorkflowDefinitionID === w.id and uwd.UserID === id) select(w))
      if (results.size == 0) None else Some(results.head)
    }
  }

  def getAvailableWorkflowDefinitions(limitStatuses : List[Status] = List.empty[Status]) : List[WorkflowDefinition] = {
    if (!_availableWorkflowDefinitions.isDefined) {
      transaction {
        val results = from(ReChargeSchema.workflowDefinitions, ReChargeSchema.userWorkflowDefinitions)((w, uwd) =>
                      where (uwd.WorkflowDefinitionID === w.id and uwd.UserID === this.id ) select(w))
        _availableWorkflowDefinitions = Some(results.toList)
      }
    }
    _availableWorkflowDefinitions.get
  }

  def getActiveWorkflowExecutions() {
    if (!_activeWorkflowExecutions.isDefined) {
      transaction {
        val results = from(ReChargeSchema.workflowExecutions, ReChargeSchema.userWorkflowDefinitions)((we, uwd) =>
                      where (we.WorkflowDefinitionID === uwd.WorkflowDefinitionID and
                              uwd.UserID === this.id) select(we))
        _activeWorkflowExecutions = Some(results.toList)
      }
    }
    _activeWorkflowExecutions.get
  }

  def getCurrentSession : Option[models.db.entity.Session] = {
    _currentSession
  }

  def startNewSession : models.db.entity.Session = {
    if (_currentSession.isDefined) {
      Logger.logWarning("Attempting to start a new Session for User " + this + " when Session " + _currentSession.get + " is still active.",None)
      this.endCurrentSession
    }
    _currentSession = Some(Session.createSession(Some(this.id)))
    _currentSession.get
  }

  def endCurrentSession = {
    if (_currentSession.isDefined) {
      _currentSession.get.end
      _currentSession = None
    }
  }

}