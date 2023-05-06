package lgbt.princess.fuh

import cats.MonadThrow
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import org.typelevel.ci.CIString

final case class User(username: CIString, sources: Sources) {
  def nicknamed(value: CIString): Nickname = new Nickname(this, value)
  def nicknamed(value: String): Nickname   = nicknamed(CIString(value))

  def isCharacter(character: Character, characterSources: Source*): CharacterAssociation =
    new CharacterAssociation(this, character, Sources(characterSources))

  def hasTag(tag: Tag, tagSources: Source*): TagAssociation =
    new TagAssociation(this, tag, Sources(tagSources))

  def resolved[F[_]: MonadThrow](lookup: SourceLookup): F[User] =
    sources.resolved(lookup).map(s => copy(sources = s))
}

object User {
  implicit val ordering: Ordering[User] = Ordering.by(_.username)
  implicit val codec: Codec[User]       = deriveCodec

  def apply(username: CIString, sources: Source*): User =
    apply(username, Sources(sources))
}
