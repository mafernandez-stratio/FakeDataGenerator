package io.miguel0afd

import java.io.File

import com.github.tototoshi.csv.CSVWriter
import io.codearte.jfairy.Fairy
import io.codearte.jfairy.producer.person.PersonProperties
import org.joda.time.format.DateTimeFormat

case class Person(id: Int,
                  firstName: String,
                  middleName: String,
                  lastName: String,
                  email: String,
                  username: String,
                  password: String,
                  sex: String,
                  addressPostalCode: String,
                  addressCity: String,
                  addressStreet: String,
                  addressStreetNumber: String,
                  telephoneNumber: String,
                  dateOfBirth: String,
                  age: Int,
                  companyName: String,
                  companyDomain: String,
                  companyEmail: String,
                  companyVat: String,
                  nationalIdentityCardNumber: String,
                  nationalIdentificationNumber: String,
                  passportNumber: String)

object Generator extends App {
  val SIZE = 10
  val f = new File("out.csv")
  val writer = CSVWriter.open(f)
  val fairy = Fairy.create()
  val dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy")
  val header = List(
    "id",
    "firstName",
    "middleName",
    "lastName",
    "email",
    "username",
    "password",
    "sex",
    "addressPostalCode",
    "addressCity",
    "addressStreet",
    "addressStreetNumber",
    "telephoneNumber",
    "dateOfBirth",
    "age",
    "companyName",
    "companyDomain",
    "companyEmail",
    "companyVat",
    "nationalIdentityCardNumber",
    "nationalIdentificationNumber",
    "passportNumber")
  writer.writeRow(header)
  for(i <- 1 to SIZE){
    val person = fairy.person(PersonProperties.ageBetween(18, 65))
    val address = person.getAddress
    val company = person.getCompany
    val p = new Person(
      i,
      person.firstName,
      person.middleName,
      person.lastName,
      person.email,
      person.username,
      person.password,
      person.sex.toString,
      address.getPostalCode,
      address.getCity,
      address.street,
      address.streetNumber,
      person.telephoneNumber,
      dtfOut.print(person.dateOfBirth),
      person.age,
      company.name,
      company.domain,
      company.email,
      company.vatIdentificationNumber,
      person.nationalIdentityCardNumber,
      person.nationalIdentificationNumber,
      person.passportNumber)
      val seq = List(
        p.id,
        p.firstName,
        p.middleName,
        p.lastName,
        p.email,
        p.username,
        p.password,
        p.sex,
        p.addressPostalCode,
        p.addressCity,
        p.addressStreet,
        p.addressStreetNumber,
        p.telephoneNumber,
        p.dateOfBirth,
        p.age,
        p.companyName,
        p.companyDomain,
        p.companyEmail,
        p.companyVat,
        p.nationalIdentityCardNumber,
        p.nationalIdentificationNumber,
        p.passportNumber)
      writer.writeRow(seq)
  }
  writer.close
}
