package lgbt.princess.fuh

import cats.MonadThrow
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

final case class CharacterAssociation(user: User, character: Character, sources: Sources)
    extends Association[Character] {
  override def associatedValue: Character = character

  def resolved[F[_]: MonadThrow](lookup: SourceLookup): F[CharacterAssociation] =
    sources.resolved(lookup).map(s => copy(sources = s))
}

object CharacterAssociation {
  implicit val ordering: Ordering[CharacterAssociation] =
    Ordering
      .by[CharacterAssociation, User](_.user)
      .orElseBy(_.character)
  implicit val codec: Codec[CharacterAssociation] = deriveCodec

  def apply(user: User, character: Character, sources: Source*): CharacterAssociation =
    apply(user, character, Sources(sources))
}
