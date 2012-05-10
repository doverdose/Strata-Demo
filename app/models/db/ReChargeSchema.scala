package models.db


import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import models.db.entity._
import models.db.relation._


object ReChargeSchema extends Schema {

  val applications = table[Application]
  val applicationPages = table[ApplicationPage]
  val applicationVersions = table[ApplicationVersion]
  val conditions = table[Condition]
  val notifications = table[Notification]
  val sessions = table[models.db.entity.Session]
  val sessionApplicationPages = table[SessionApplicationPage]("Session_ApplicationPage")
  val taskDefinitions = table[TaskDefinition]
  val taskExecutions = table[TaskExecution]
  val taskExecutionResults = table[TaskExecutionResult]
  val users = table[User]
  val userConditions = table[UserCondition]("User_Condition")
  val userRelationships = table[UserRelationship]
  val userWorkflowDefinitions = table[UserWorkflow]("User_Workflow")
  val workflowDefinitions = table[WorkflowDefinition]
  val workflowExecutions = table[WorkflowExecution]
  val workflowDefinitionTaskDefinitions = table[WorkflowDefinitionTaskDefinition]("WorkflowDefinition_TaskDefinition")

  on(applications) (application => declare(
     application.id is(primaryKey,autoIncremented)
  ))
  on(applicationPages) (applicationPage => declare(
    applicationPage.id is(primaryKey,autoIncremented)
  ))
  on(applicationVersions) (applicationVersion => declare(
    applicationVersion.id is(primaryKey,autoIncremented)
  ))
  on (conditions) (condition => declare(
    condition.id is(primaryKey)
  ))
  on (notifications) (notification => declare(
    notification.id is(primaryKey,autoIncremented)
  ))
  on (sessions) (session => declare(
    session.id is(primaryKey,autoIncremented)
  ))
  on (sessionApplicationPages) (sessionApplicationPage => declare(
  ))
  on (taskDefinitions) (taskDefinition => declare(
    taskDefinition.id is(primaryKey,autoIncremented)
  ))
  on (taskExecutions) (taskExecution => declare(
    taskExecution.id is(primaryKey,autoIncremented)
  ))
  on (taskExecutionResults) (taskExecutionResult => declare(
    taskExecutionResult.id is(primaryKey,autoIncremented)
  ))
  on (users) (user => declare(
    user.id is(primaryKey,autoIncremented)
  ))
  on (userConditions) (userCondition => declare(
    userCondition.id is(primaryKey,autoIncremented)
  ))
  on (userRelationships) (userRelationship => declare(
  ))
  on (userWorkflowDefinitions) (userWorkflow => declare(
    userWorkflow.id is(primaryKey,autoIncremented)
  ))
  on (workflowDefinitions) (workflowDefinition => declare(
    workflowDefinition.id is(primaryKey,autoIncremented)
  ))
  on (workflowExecutions) (workflowExecution => declare(
    workflowExecution.id is(primaryKey,autoIncremented)
  ))
  on (workflowDefinitionTaskDefinitions) (workflowDefinitionTaskDefinition => declare(
    workflowDefinitionTaskDefinition.id is(primaryKey,autoIncremented)
  ))


}