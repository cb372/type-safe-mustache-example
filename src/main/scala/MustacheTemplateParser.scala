object MustacheTemplateParser {

  // TODO this might be an over-simplified view of Mustache syntax.
  // We want to ignore partials, comments, etc. and only extract variable placeholders.
  private val Placeholder = """\{\{([A-Za-z0-9_]+)\}\}""".r

  def findVariableNames(template: String): Set[String] =
    Placeholder.findAllMatchIn(template).map(_.group(1)).toSet

}
