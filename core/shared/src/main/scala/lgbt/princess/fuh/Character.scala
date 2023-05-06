package lgbt.princess.fuh

import cats.MonadThrow
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

final case class Character(primaryName: Name, additionalNames: Name*) {
  def names: Iterable[Name] = additionalNames.view.prepended(primaryName)

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
      primary    <- primaryName.resolved(lookup)
      additional <- additionalNames.map(_.resolved(lookup)).sequence
    } yield Character(primary, additional: _*)
}

object Character {
  implicit val ordering: Ordering[Character] = Ordering.by(_.primaryName)
  implicit val codec: Codec[Character]       = deriveCodec
}
