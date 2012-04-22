package models.db

import entity._
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter
import org.squeryl.Session
import org.squeryl.SessionFactory
import java.sql.Date
import relation.{UserWorkflow, WorkflowDefinitionTaskDefinition}

object DatabaseProxy {

  val databaseUsername = "recharge"
  val databasePassword = "recharge"
  val databaseConnection = "jdbc:h2:h2/ReChargeWeb"


  def startDatabaseSession():Unit = {
    Class.forName("org.h2.Driver")
      SessionFactory.concreteFactory = Some(() => Session.create(
          java.sql.DriverManager.getConnection(databaseConnection, databaseUsername, databasePassword),
          new H2Adapter)
        )
  }

  def generateSchema():Unit = {
    transaction {
    ReChargeSchema.drop
    ReChargeSchema.create
    println("Created the schema")
    }
  }

  def createTestData():Unit = {
    transaction {
      val today: java.util.Date  = new java.util.Date();
      val anna = new User(FirstName="Anna",
                          LastName="Ayuso",
                          Email="annaayuso@gmail.com",
                          Password="pwd",
                          RoleTypeID=1L,
                          CreatedDate = new Date(today.getTime)
                          )
      ReChargeSchema.users.insert(anna)

      val task1 = new TaskDefinition(Name="CheckBloodPressure",
                                     TaskTypeID=1,
                                     Question="Check and report your blood presure.",
                                     Choices=None
                                    )
      ReChargeSchema.taskDefinitions.insert(task1)

      val task2 = new TaskDefinition(Name="TakeAmlodipine",
                                     TaskTypeID=1,
                                     Question="Take 20mg of Amlodipine.  It is a blue, round pill",
                                     Choices=None
                                    )
      ReChargeSchema.taskDefinitions.insert(task2)

      val task3 = new TaskDefinition(Name="CheckWeight",
                                     TaskTypeID=1,
                                     Question="Check and report your weight.",
                                     Choices=None
                                    )
      ReChargeSchema.taskDefinitions.insert(task3)

      val workflow = new WorkflowDefinition(Name="HighBloodPressure",
                                            RootWorkflowDefinitionID=None,
                                            ParentWorkflowDefinitionID=None
                                            )
      ReChargeSchema.workflowDefinitions.insert(workflow)

      val assoc1 = new WorkflowDefinitionTaskDefinition(WorkflowDefinitionID=workflow.id,
                                                        TaskDefinitionID=task1.id,
                                                        ParentTaskDefinitionID=None
                                                        )
      ReChargeSchema.workflowDefinitionTaskDefinitions.insert(assoc1)

      val assoc2 = new WorkflowDefinitionTaskDefinition(WorkflowDefinitionID=workflow.id,
                                                        TaskDefinitionID=task2.id,
                                                        ParentTaskDefinitionID=Some(task1.id)
                                                        )
      ReChargeSchema.workflowDefinitionTaskDefinitions.insert(assoc2)

      val assoc3 = new WorkflowDefinitionTaskDefinition(WorkflowDefinitionID=workflow.id,
                                                        TaskDefinitionID=task3.id,
                                                        ParentTaskDefinitionID=Some(task2.id)
                                                        )
      ReChargeSchema.workflowDefinitionTaskDefinitions.insert(assoc3)



      val userWorkflowAssoc = new UserWorkflow(UserID=anna.id,
                                               WorkflowDefinitionID=workflow.id,
                                               StartTime=None,
                                               RunIntervalInMinutes=None
                                              )
      ReChargeSchema.userWorkflows.insert(userWorkflowAssoc)
    }
  }

  def initializeDatabase = {
      startDatabaseSession()
      generateSchema()
      createTestData()
  }



  def main(args: Array[String]) {
      startDatabaseSession()
      generateSchema()
      createTestData()
    }
}