package models.db

import entity._
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.Session
import org.squeryl.SessionFactory
import java.sql.Date
import relation.{UserWorkflow, WorkflowDefinitionTaskDefinition}
import java.io.{FileWriter, PrintWriter}
import java.util.Calendar


object DatabaseProxy {

  val h2DatabaseUsername = "recharge"
  val h2DatabasePassword = "recharge"
  val h2DatabaseConnection = "jdbc:h2:h2/ReChargeWeb"


  def startH2DatabaseSession():Unit = {
    Class.forName("org.h2.Driver")
      SessionFactory.concreteFactory = Some(() => Session.create(
          java.sql.DriverManager.getConnection(h2DatabaseConnection, h2DatabaseUsername, h2DatabasePassword),
          new H2Adapter)
        )
  }



  val mySqlDatabaseUsername = "ReCharge"
  val mySqlDatabasePassword = "ReChargeHealth"
  val mySqlDatabaseConnection = "jdbc:mysql://localhost:3306/ReCharge"

  def startMySqlDatabaseSession():Unit = {
    Class.forName("com.mysql.jdbc.Driver")
      SessionFactory.concreteFactory = Some(() => Session.create(
          java.sql.DriverManager.getConnection(mySqlDatabaseConnection, mySqlDatabaseUsername, mySqlDatabasePassword),
          new MySQLAdapter)
        )
  }



  def generateSchema():Unit = {
    transaction {
      ReChargeSchema.drop
      ReChargeSchema.create
      println("Created the schema")
    }
  }
  def writeDdlToDisk() = {
    transaction {
      ReChargeSchema.printDdl
      ReChargeSchema.printDdl(new PrintWriter(new FileWriter("/home/anna/ReChargeSchema.sql", true)))
      println("Wrote schema to disk")
    }
  }

  def createTestData():Unit = {
    transaction {
      val anna = User.createUser(firstName="Anna",
                              lastName="Ayuso",
                              email="annaayuso@gmail.com",
                              password="pwd",
                              roleTypeID=1L,
                              createdDate = new java.sql.Date(Calendar.getInstance.getTime.getTime)
                              )

      val royce = User.createUser(firstName="Royce",
                              lastName="Cheng",
                              email="royce.cheng@gmail.com",
                              password="pwd",
                              roleTypeID=1L,
                              createdDate = new java.sql.Date(Calendar.getInstance.getTime.getTime)
                              )

      val task1 = new TaskDefinition(Name="CheckBloodPressure",
                                     TaskTypeID=TaskType.NumberEntry,
                                     Question="Check and report your blood presure.",
                                     Choices=None
                                    )
      ReChargeSchema.taskDefinitions.insert(task1)

      val task2 = new TaskDefinition(Name="TakeAmlodipine",
                                     TaskTypeID=TaskType.Statement,
                                     Question="Take 20mg of Amlodipine.  It is a blue, round pill",
                                     Choices=None
                                    )
      ReChargeSchema.taskDefinitions.insert(task2)

      val task3 = new TaskDefinition(Name="CheckWeight",
                                     TaskTypeID=TaskType.NumberEntry,
                                     Question="Check and report your weight.",
                                     Choices=Some("Weight")
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
      ReChargeSchema.userWorkflowDefinitions.insert(userWorkflowAssoc)

      val userWorkflowAssoc1 = new UserWorkflow(UserID=royce.id,
                                               WorkflowDefinitionID=workflow.id,
                                               StartTime=None,
                                               RunIntervalInMinutes=None
                                              )
      ReChargeSchema.userWorkflowDefinitions.insert(userWorkflowAssoc1)
    }
  }

  def initializeDatabase = {
      startMySqlDatabaseSession()
      //generateSchema()
      //createTestData()
  }

}