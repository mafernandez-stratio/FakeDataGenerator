package io.miguel0afd

import java.io.File
import java.util.UUID

import com.github.tototoshi.csv.CSVWriter
import io.codearte.jfairy.Fairy
import io.codearte.jfairy.producer.person.PersonProperties
import org.joda.time.format.DateTimeFormat

case class Transaction(id: Int,
                  ipAddress: String,
                  nationalIdentificationNumber: String,
                  company: String,
                  vatIdentificationNumber: String,
                  url: String,
                  vendor: String,
                  expiryDate: String,
                  date: String,
                  description: String,
                  quantity: Int,
                  total: Double,
                  comment: String)

object TransactionGenerator extends App {
  val SIZE = if(args.length > 0){
    args(0).toInt
  } else {
    10
  }
  val f = new File(if(args.length > 1){
    args(1)
  } else {
    "transactions.csv"
  })
  val writer = CSVWriter.open(f)
  val fairy = Fairy.create()
  val dtfExpiry = DateTimeFormat.forPattern("MM/yyyy")
  val dtfTransaction = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm.ss")
  val header = List(
    "id",
    "ipAddress",
    "nationalIdentificationNumber",
    "company",
    "vatIdentificationNumber",
    "url",
    "vendor",
    "expiryDate",
    "date",
    "description",
    "quantity",
    "total",
    "comment"
)
  writer.writeRow(header)
  println("START")
  for(i <- 1 to SIZE){
    val person = fairy.person(PersonProperties.ageBetween(18, 65))
    val company = fairy.company()
    val ccard = fairy.creditCard()
    val textprod = fairy.textProducer()
    val baseprod = fairy.baseProducer()
    val t = new Transaction(
      i,
      fairy.networkProducer().ipAddress(),
      person.nationalIdentificationNumber(),
      company.name(),
      company.vatIdentificationNumber(),
      company.url(),
      ccard.vendor(),
      dtfExpiry.print(ccard.expiryDate()),
      dtfTransaction.print(fairy.dateProducer().randomDateBetweenYearAndNow(2010)),
      textprod.word(1),
      baseprod.randomBetween(0, 100),
      baseprod.randomBetween(0.0, 1000.0),
      textprod.sentence())
    val seq = List(
      UUID.randomUUID(),
      s"${t.ipAddress}",
      s"${t.nationalIdentificationNumber}",
      s"${t.company}",
      s"${t.vatIdentificationNumber}",
      s"${t.url}",
      s"${t.vendor}",
      s"${t.expiryDate}",
      s"${t.date}",
      s"${t.description}",
      t.quantity,
      f"${t.total}%1.2f",
      s"${t.comment}")
    writer.writeRow(seq)
  }
  writer.close
  println("END")


}


