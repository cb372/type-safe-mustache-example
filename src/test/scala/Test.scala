import com.github.jknack.handlebars.Handlebars
import scala.collection.JavaConverters._

object Test extends App {

  /*
  This macro annotation downloads the template,
  parses it to extract all the variable names from the {{placeholders}},
  and builds a `CCPaymentTaken` case class with corresponding constructor args.
   */
  @MustacheTemplate("https://raw.githubusercontent.com/cb372/type-safe-mustache-example/master/example-template.txt")
  object CCPaymentTaken

  /*
  Whoops! This one doesn't compile, because I forgot the date variable:

  -----
  [error] /Users/chris.birchall/code/type-safe-mustache-example/src/test/scala/Test.scala:15: not enough arguments for method apply: (amount: String, currencySymbol: String, date: String, name: String)Test.CCPaymentTaken in object CCPaymentTaken.
	[error] Unspecified value parameter date.
	[error]   val template = CCPaymentTaken(
	[error]                                ^
	[error] one error found
  -----

  val template = CCPaymentTaken(
    amount = "10.00",
    currencySymbol = "£",
    name = "Chris"
  )
   */

  // This one compiles just fine
  val template = CCPaymentTaken(
    amount = "10.00",
    currencySymbol = "£",
    date = "19/10/2016",
    name = "Chris"
  )

  val renderer = new Handlebars().compileInline(CCPaymentTaken.template)
  println(renderer.apply(template.toMap.asJava))
}
