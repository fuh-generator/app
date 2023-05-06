package lgbt.princess.fuh

import cats.MonadThrow
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

final case class TagAssociation(user: User, tag: Tag, sources: Sources) extends Association[Tag] {
  override def associatedValue: Tag = tag

  def resolved[F[_]: MonadThrow](lookup: SourceLookup): F[TagAssociation] =
    sources.resolved(lookup).map(s => copy(sources = s))
}

object TagAssociation {
  implicit val ordering: Ordering[TagAssociation] =
    Ordering
      .by[TagAssociation, User](_.user)
      .orElseBy(_.tag)
  implicit val codec: Codec[TagAssociation] = deriveCodec

  def apply(user: User, tag: Tag, sources: Source*): TagAssociation =
    apply(user, tag, Sources(sources))
}
