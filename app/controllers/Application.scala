package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import models.db.DatabaseProxy
import models.db.entity._
import models._
import models.db.Status.Status

object Application extends Controller {

  def initialize = {
    DatabaseProxy.initializeDatabase
  }
  initialize

  ///PAGES
  def index = Action {
    Ok(views.html.index(signInForm))
  }


  val signInForm = Form(
    tuple(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )
  )

  def Authenticated(f: User => Request[AnyContent] => Result) = {
    Action { request =>
      val user = request.session.get("userID").flatMap(u => User.getByID(u.toLong))
      if (user.isDefined) {
        val sessionID= request.session.get("sessionID").flatMap(s => Some(s.toLong)).get
        if (!user.get.getCurrentSession.isDefined || user.get.getCurrentSession.get.id != sessionID) {
          models.db.Logger.logWarning("Unexpected SessionID found for User " + user, None)
          user.get.endCurrentSession
          user.get.startNewSession
        }
        f(user.get)(request)
      }
      else Unauthorized
    }
  }



  def signIn = Action { implicit request =>
    signInForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.index(formWithErrors)),
      {case (email,password) =>
        val user = User.getByEmail(email)
        if (user.isDefined && user.get.validatePassword(password)) {
          user.get.startNewSession
          val workflowDefinition = user.get.getWorkflowDefinition(List.empty[models.db.Status.Status]).get
          val tasks = workflowDefinition.getTaskDefinitions
          Ok(views.html.dashboard(user.get,tasks.map{taskDefinition : TaskDefinition => (taskDefinition,taskDefinitionToForm(taskDefinition))}.toMap)).withSession(
                                                                      "userID" -> user.get.id.toString,
                                                                      "sessionID" -> user.get.getCurrentSession.get.id.toString
                                                                    )
        }
        else Ok(views.html.index(signInForm))
      }
    )
  }

  val completeTaskForm = Form(
    tuple(
      "response" -> nonEmptyText,
      "altResonse" -> nonEmptyText
    )
  )

  def taskDefinitionToForm(taskDefinition:TaskDefinition) : Form[(String,String)] = {
    Form(
      tuple(
        "response" -> nonEmptyText,
        "altResonse" -> nonEmptyText
      )
    )
  }

  def completeTask(taskDefinitionId : Long, userId : Long) = Action { implicit request =>
    val user = User.getByID(userId)
    val tasks = user.get.getWorkflowDefinition(List.empty[models.db.Status.Status]).get.getTaskDefinitions
    completeTaskForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.dashboard(user.get,tasks.map{taskDefinition : TaskDefinition => (taskDefinition,taskDefinitionToForm(taskDefinition))}.toMap)),
      {case (response) =>
        if (user.isDefined) Ok(views.html.dashboard(user.get,tasks.map{taskDefinition : TaskDefinition => (taskDefinition,taskDefinitionToForm(taskDefinition))}.toMap))
        else Ok(views.html.index(signInForm))
      }
    )
  }




}