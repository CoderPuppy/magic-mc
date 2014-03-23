package cpup.mc.magic.api.oldenLanguage.textParsing

import cpup.mc.magic.api.oldenLanguage.runes.TRune

/**
 * Created by cpup on 3/23/14.
 */
trait TParsingTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = transform(context, TextParser.parseRune(rune.text))
	def transform(context: TContext, rune: ParsedRune): TRune
}
