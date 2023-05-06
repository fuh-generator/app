package lgbt.princess.fuh

import cats.MonadThrow
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

final case class Character(primaryName: Name, aliases: Name*) {
  def names: Iterable[Name] = aliases.view.prepended(primaryName)

  def sources: Sources = {
    val aggregated =
      names.view
        .flatMap(_.sources.value)
        .iterator
        .distinct
        .buffered
    if (aggregated.headOption.contains(Source.Canon)) Sources(List(Source.Canon))
    else Sources(aggregated.toSeq)
  }

  def resolved[F[_]: MonadThrow](lookup: SourceLookup): F[Character] =
    for {
      primary <- primaryName.resolved(lookup)
      others  <- aliases.map(_.resolved(lookup)).sequence
    } yield Character(primary, others: _*)
}

object Character {
  implicit val ordering: Ordering[Character] = Ordering.by(_.primaryName)
  implicit val codec: Codec[Character]       = deriveCodec
}
