package cpup.mc.magic.api.oldenLanguage

trait TTransform {
	def transform(context: TContext, rune: TextRune): TRune
}