import okhttp3.{OkHttpClient, Request}

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.util.control.NonFatal

object TemplateGenerator {

  private val client = new OkHttpClient()

  def fromMustacheTemplate_impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    def fail(message: String) = c.abort(c.enclosingPosition, message)

    def download(url: String): String = {
      val request = new Request.Builder().url(url).build()
      try {
        client.newCall(request).execute().body().string()
      } catch {
        case NonFatal(e) => fail(s"Failed to download template body from $url. Exception: $e")
      }
    }

    val url = c.macroApplication match {
      case Apply(Select(Apply(_, List(Literal(Constant(u: String)))), _), _) => u
      case _ => fail("You must provide the template URL")
    }

    annottees.map(_.tree) match {
      // As a pretty arbitrary restriction to make my life easier, we only accept empty objects
      case List(q"object $name extends $parent { ..$body }") if body.isEmpty =>
        val templateBody = download(url)
        val variableNames = MustacheTemplateParser.findVariableNames(templateBody)
        val ctorParams = variableNames.toSeq.sorted.map(name => q"val ${TermName(name)}: String")
        val mapElements = variableNames.toSeq.sorted.map(name => q"$name -> ${TermName(name)}")

        val tree = q"""
            object $name extends $parent { 
              val template: String = $templateBody
            }
            case class ${TypeName(name.toString)}(..$ctorParams) {
              def toMap: _root_.scala.collection.immutable.Map[String, String] =
                _root_.scala.collection.immutable.Map(..$mapElements)
            }
          """
        c.Expr[Any](tree)

      case _ => fail("You must annotate an object definition with an empty body.")
    }

  }
}
