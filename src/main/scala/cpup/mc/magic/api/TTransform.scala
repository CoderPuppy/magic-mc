package cpup.mc.magic.api

trait TTransform {
	def isValid(rune: TRune): Boolean
	def transform(context: TContext, rune: TRune): TRune
}