import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

class MustacheTemplate(url: String) extends StaticAnnotation {

  def macroTransform(annottees: Any*) = macro TemplateGenerator.fromMustacheTemplate_impl

}
