package cpup.mc.magic.api.oldenLanguage

trait TTransform[INTER <: Any] {
	def isValid(context: TContext, rune: TRune): Option[INTER]
	def transform(context: TContext, rune: TRune, inter: INTER): TRune
}