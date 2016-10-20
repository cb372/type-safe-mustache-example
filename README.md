# Type-safe Mustache templates

Example of using a Scala macro annotation to ensure we are passing the correct variables to a Mustache template.

Given the URL of a Mustache template, the annotation downloads the template, parses it to extract the names of all variables, and generates a corresponding case class.

See [Test.scala](src/test/scala/Test.scala) for an example of it in action.

Run `sbt test:run` to try it for yourself. You should see something like this:

```
> test:run
[info] Running Test
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Hello Chris,

We received your payment of £10.00 on 19/10/2016.

Thanks!
```

## Acknowledgements

Inspired by Travis Brown's example here: https://github.com/travisbrown/type-provider-examples
