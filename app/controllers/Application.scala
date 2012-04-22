package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import models.db.entity.User
import models.db.DatabaseProxy

object Application extends Controller {

  def initialize = {
    DatabaseProxy.initializeDatabase
  }

  initialize

  def index = Action {
    Ok(views.html.index(signInForm))
  }

val signInForm = Form(
    tuple(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )
  )


  def signIn = Action { implicit request =>
    signInForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.index(formWithErrors)),
      {case (email,password) => Ok(views.html.dashboard())}
    )
  }


}