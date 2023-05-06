package lgbt.princess.fuh

import cats.MonadThrow
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import org.typelevel.ci.CIString

final case class Name(value: CIString, sources: Sources) {
  def resolved[F[_]: MonadThrow](lookup: SourceLookup): F[Name] =
    sources.resolved(lookup).map(s => copy(sources = s))
}

object Name {
  implicit val ordering: Ordering[Name] = Ordering.by(_.value)
  implicit val codec: Codec[Name]       = deriveCodec

  def apply(value: CIString, sources: Source*): Name =
    apply(value, Sources(sources))
}
