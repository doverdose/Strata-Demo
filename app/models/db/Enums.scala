package models.db

object RoleType extends Enumeration {
  type RoleType = Value
  val Doctor = Value(1,"Doctor")
  val Patient = Value(2,"Patient")
  val CareProvider = Value(3,"CareProvider")
}

object TaskType extends Enumeration {
  type TaskType = Value
  val Statement = Value(1,"Statement")
  val MultipleChoice = Value(2,"MultipleChoice")
  val TrueFalse = Value(3,"TrueFalse")
  val NumberEntry = Value(4, "NumberEntry")
}

object RelationshipType extends Enumeration {
  type RelationshipType = Value
  val DoctorPatient = Value(1,"DoctorPatient")
  val PatientPatient = Value(2,"PatientPatient")
  val CareProviderPatient = Value(3,"CareProviderPatient")
}

object NotificationType extends Enumeration {
  type NotificationType = Value
  val Email = Value(1,"Email")
}

object Status extends Enumeration {
  type Status = Value
  val NotStarted = Value(1,"NotStarted")
  val Created = Value(2,"Created")
  val InProgress = Value(3,"InProgress")
  val Stopped = Value(4,"Stopped")
  val InternalError = Value(5,"InternalError")
  val Complete = Value(6, "Complete")
  val Ignore = Value(7,"Ignore")
  val PendingAction = Value(8,"PendingAction")
  val Undefined = Value(9,"Undefined")

  def isFinal(status : Status) : Boolean = Set(Complete,Ignore,InternalError).contains(status)
}
