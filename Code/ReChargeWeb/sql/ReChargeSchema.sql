SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`RoleType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`RoleType` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`User` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `FirstName` VARCHAR(200) NOT NULL ,
  `LastName` VARCHAR(200) NOT NULL ,
  `Email` VARCHAR(200) NOT NULL ,
  `Password` VARCHAR(200) NOT NULL ,
  `RoleTypeID` INT NOT NULL ,
  `CreatedTime` DATETIME NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_User_RoleType1` (`RoleTypeID` ASC) ,
  CONSTRAINT `fk_User_RoleType1`
    FOREIGN KEY (`RoleTypeID` )
    REFERENCES `mydb`.`RoleType` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`RelationshipType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`RelationshipType` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`UserRelationship`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`UserRelationship` (
  `UserID1` INT NOT NULL ,
  `UserID2` INT NOT NULL ,
  `RelationshipTypeID` INT NOT NULL ,
  `CreatedTime` DATETIME NOT NULL ,
  INDEX `fk_UserRelationship_User1` (`UserID1` ASC) ,
  INDEX `fk_UserRelationship_User2` (`UserID2` ASC) ,
  INDEX `fk_UserRelationship_RelationshipType1` (`RelationshipTypeID` ASC) ,
  PRIMARY KEY (`UserID1`, `UserID2`) ,
  CONSTRAINT `fk_UserRelationship_User1`
    FOREIGN KEY (`UserID1` )
    REFERENCES `mydb`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserRelationship_User2`
    FOREIGN KEY (`UserID2` )
    REFERENCES `mydb`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserRelationship_RelationshipType1`
    FOREIGN KEY (`RelationshipTypeID` )
    REFERENCES `mydb`.`RelationshipType` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Condition`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Condition` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Status`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Status` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(200) NOT NULL ,
  `IsFinal` BIT NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User_Condition`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`User_Condition` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `AssignedToUserID` INT NOT NULL ,
  `AssignedByUserID` INT NOT NULL ,
  `StatusID` INT NOT NULL ,
  `StatusTime` DATETIME NOT NULL ,
  `ConditionID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_User_Condition_User1` (`AssignedToUserID` ASC) ,
  INDEX `fk_User_Condition_User2` (`AssignedByUserID` ASC) ,
  INDEX `fk_User_Condition_Condition1` (`ConditionID` ASC) ,
  INDEX `fk_User_Condition_Status1` (`StatusID` ASC) ,
  CONSTRAINT `fk_User_Condition_User1`
    FOREIGN KEY (`AssignedToUserID` )
    REFERENCES `mydb`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Condition_User2`
    FOREIGN KEY (`AssignedByUserID` )
    REFERENCES `mydb`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Condition_Condition1`
    FOREIGN KEY (`ConditionID` )
    REFERENCES `mydb`.`Condition` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Condition_Status1`
    FOREIGN KEY (`StatusID` )
    REFERENCES `mydb`.`Status` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`WorkflowDefinition`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`WorkflowDefinition` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(200) NOT NULL ,
  `RootWorkflowID` INT NOT NULL ,
  `ParentWorkflowID` INT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_WorkflowDefinition_WorkflowDefinition1` (`RootWorkflowID` ASC) ,
  INDEX `fk_WorkflowDefinition_WorkflowDefinition2` (`ParentWorkflowID` ASC) ,
  CONSTRAINT `fk_WorkflowDefinition_WorkflowDefinition1`
    FOREIGN KEY (`RootWorkflowID` )
    REFERENCES `mydb`.`WorkflowDefinition` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WorkflowDefinition_WorkflowDefinition2`
    FOREIGN KEY (`ParentWorkflowID` )
    REFERENCES `mydb`.`WorkflowDefinition` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User_Workflow`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`User_Workflow` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `UserID` INT NOT NULL ,
  `WorkflowDefinitionID` INT NOT NULL ,
  `StatusID` INT NOT NULL ,
  `StatusTime` DATETIME NOT NULL ,
  `StartTime` TIME NULL ,
  `RunIntervalInMinutes` INT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_User_Workflow_User1` (`UserID` ASC) ,
  INDEX `fk_User_Workflow_Status1` (`StatusID` ASC) ,
  INDEX `fk_User_Workflow_WorkflowDefinition1` (`WorkflowDefinitionID` ASC) ,
  CONSTRAINT `fk_User_Workflow_User1`
    FOREIGN KEY (`UserID` )
    REFERENCES `mydb`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Workflow_Status1`
    FOREIGN KEY (`StatusID` )
    REFERENCES `mydb`.`Status` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Workflow_WorkflowDefinition1`
    FOREIGN KEY (`WorkflowDefinitionID` )
    REFERENCES `mydb`.`WorkflowDefinition` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`WorkflowExecution`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`WorkflowExecution` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `UserID` INT NOT NULL ,
  `StartDate` DATETIME NOT NULL ,
  `EndDate` DATETIME NULL ,
  `StatusID` INT NOT NULL ,
  `StatusTime` DATETIME NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_WorkflowExecution_Status1` (`StatusID` ASC) ,
  INDEX `fk_WorkflowExecution_User1` (`UserID` ASC) ,
  CONSTRAINT `fk_WorkflowExecution_Status1`
    FOREIGN KEY (`StatusID` )
    REFERENCES `mydb`.`Status` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WorkflowExecution_User1`
    FOREIGN KEY (`UserID` )
    REFERENCES `mydb`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TaskType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`TaskType` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TaskDefinition`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`TaskDefinition` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(200) NOT NULL ,
  `TaskTypeID` INT NOT NULL ,
  `Question` VARCHAR(1000) NOT NULL ,
  `Choices` VARCHAR(1000) NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_TaskDefinition_TaskType1` (`TaskTypeID` ASC) ,
  CONSTRAINT `fk_TaskDefinition_TaskType1`
    FOREIGN KEY (`TaskTypeID` )
    REFERENCES `mydb`.`TaskType` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Application`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Application` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(200) NOT NULL ,
  `CreatedDate` DATETIME NOT NULL ,
  `ApplicationRoot` VARCHAR(200) NULL ,
  `URL` VARCHAR(200) NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ApplicationVersion`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`ApplicationVersion` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `ApplicationID` INT NOT NULL ,
  `Version` VARCHAR(200) NOT NULL ,
  `CreatedDate` DATETIME NOT NULL ,
  `AndroidSDKVersion` VARCHAR(200) NULL ,
  `JavaVersion` VARCHAR(200) NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_ApplicationVersion_Application1` (`ApplicationID` ASC) ,
  CONSTRAINT `fk_ApplicationVersion_Application1`
    FOREIGN KEY (`ApplicationID` )
    REFERENCES `mydb`.`Application` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Session`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Session` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `StartDate` DATETIME NOT NULL ,
  `EndDate` DATETIME NULL ,
  `UserID` INT NULL ,
  `ApplicationVersionID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_Session_User1` (`UserID` ASC) ,
  INDEX `fk_Session_ApplicationVersion1` (`ApplicationVersionID` ASC) ,
  CONSTRAINT `fk_Session_User1`
    FOREIGN KEY (`UserID` )
    REFERENCES `mydb`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Session_ApplicationVersion1`
    FOREIGN KEY (`ApplicationVersionID` )
    REFERENCES `mydb`.`ApplicationVersion` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TaskExecution`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`TaskExecution` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `WorkflowExecutionID` INT NOT NULL ,
  `TaskDefinitionID` INT NOT NULL ,
  `StartTime` DATETIME NOT NULL ,
  `StartSessionID` INT NOT NULL ,
  `EndTime` DATETIME NULL ,
  `EndSessionID` INT NULL ,
  `Priority` INT NOT NULL ,
  `StatusID` INT NOT NULL ,
  `StatusTime` DATETIME NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_TaskExecution_WorkflowExecution1` (`WorkflowExecutionID` ASC) ,
  INDEX `fk_TaskExecution_TaskDefinition1` (`TaskDefinitionID` ASC) ,
  INDEX `fk_TaskExecution_Session1` (`StartSessionID` ASC) ,
  INDEX `fk_TaskExecution_Session2` (`EndSessionID` ASC) ,
  INDEX `fk_TaskExecution_Status1` (`StatusID` ASC) ,
  CONSTRAINT `fk_TaskExecution_WorkflowExecution1`
    FOREIGN KEY (`WorkflowExecutionID` )
    REFERENCES `mydb`.`WorkflowExecution` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TaskExecution_TaskDefinition1`
    FOREIGN KEY (`TaskDefinitionID` )
    REFERENCES `mydb`.`TaskDefinition` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TaskExecution_Session1`
    FOREIGN KEY (`StartSessionID` )
    REFERENCES `mydb`.`Session` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TaskExecution_Session2`
    FOREIGN KEY (`EndSessionID` )
    REFERENCES `mydb`.`Session` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TaskExecution_Status1`
    FOREIGN KEY (`StatusID` )
    REFERENCES `mydb`.`Status` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ApplicationPage`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`ApplicationPage` (
  `ID` INT NOT NULL ,
  `ApplicationID` INT NOT NULL ,
  `Name` VARCHAR(200) NOT NULL ,
  `URL` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_ApplicationPage_Application1` (`ApplicationID` ASC) ,
  CONSTRAINT `fk_ApplicationPage_Application1`
    FOREIGN KEY (`ApplicationID` )
    REFERENCES `mydb`.`Application` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Session_ApplicationPage`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Session_ApplicationPage` (
  `SessionID` INT NOT NULL ,
  `ApplicationPageID` INT NOT NULL ,
  `StartTime` DATETIME NOT NULL ,
  `EndTime` DATETIME NULL ,
  PRIMARY KEY (`SessionID`, `ApplicationPageID`, `StartTime`) ,
  INDEX `fk_Session_ApplicationPage_Session1` (`SessionID` ASC) ,
  INDEX `fk_Session_ApplicationPage_ApplicationPage1` (`ApplicationPageID` ASC) ,
  CONSTRAINT `fk_Session_ApplicationPage_Session1`
    FOREIGN KEY (`SessionID` )
    REFERENCES `mydb`.`Session` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Session_ApplicationPage_ApplicationPage1`
    FOREIGN KEY (`ApplicationPageID` )
    REFERENCES `mydb`.`ApplicationPage` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`NotificationType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`NotificationType` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Notification`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Notification` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `WorkflowDefinitionID` INT NOT NULL ,
  `StatusID` INT NOT NULL ,
  `StatusTime` DATETIME NOT NULL ,
  `NotificationTypeID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_Notification_Status` (`StatusID` ASC) ,
  INDEX `fk_Notification_NotificationType1` (`NotificationTypeID` ASC) ,
  INDEX `fk_Notification_WorkflowDefinition1` (`WorkflowDefinitionID` ASC) ,
  CONSTRAINT `fk_Notification_Status`
    FOREIGN KEY (`StatusID` )
    REFERENCES `mydb`.`Status` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_NotificationType1`
    FOREIGN KEY (`NotificationTypeID` )
    REFERENCES `mydb`.`NotificationType` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_WorkflowDefinition1`
    FOREIGN KEY (`WorkflowDefinitionID` )
    REFERENCES `mydb`.`WorkflowDefinition` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TaskExecutionResult`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`TaskExecutionResult` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `TaskExecutionID` INT NOT NULL ,
  `Result` VARCHAR(1000) NOT NULL ,
  `StatusID` INT NOT NULL ,
  `StatusTIme` DATETIME NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_TaskExecutionResult_TaskExecution1` (`TaskExecutionID` ASC) ,
  INDEX `fk_TaskExecutionResult_Status1` (`StatusID` ASC) ,
  CONSTRAINT `fk_TaskExecutionResult_TaskExecution1`
    FOREIGN KEY (`TaskExecutionID` )
    REFERENCES `mydb`.`TaskExecution` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TaskExecutionResult_Status1`
    FOREIGN KEY (`StatusID` )
    REFERENCES `mydb`.`Status` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`WorkflowDefinition_TaskDefinition`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`WorkflowDefinition_TaskDefinition` (
  `TaskDefinitionID` INT NOT NULL ,
  `WorkflowDefinitionID` INT NOT NULL ,
  `ParentTaskDefinitionID` INT NULL ,
  INDEX `fk_WorkflowDefinition_TaskDefinition_TaskDefinition1` (`TaskDefinitionID` ASC) ,
  INDEX `fk_WorkflowDefinition_TaskDefinition_WorkflowDefinition1` (`WorkflowDefinitionID` ASC) ,
  PRIMARY KEY (`TaskDefinitionID`, `WorkflowDefinitionID`) ,
  INDEX `fk_WorkflowDefinition_TaskDefinition_TaskDefinition2` (`ParentTaskDefinitionID` ASC) ,
  CONSTRAINT `fk_WorkflowDefinition_TaskDefinition_TaskDefinition1`
    FOREIGN KEY (`TaskDefinitionID` )
    REFERENCES `mydb`.`TaskDefinition` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WorkflowDefinition_TaskDefinition_WorkflowDefinition1`
    FOREIGN KEY (`WorkflowDefinitionID` )
    REFERENCES `mydb`.`WorkflowDefinition` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WorkflowDefinition_TaskDefinition_TaskDefinition2`
    FOREIGN KEY (`ParentTaskDefinitionID` )
    REFERENCES `mydb`.`TaskDefinition` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
