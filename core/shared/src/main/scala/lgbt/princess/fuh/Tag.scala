package lgbt.princess.fuh

import cats.MonadThrow
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import org.typelevel.ci.CIString

final case class Tag(text: CIString, sources: Sources) {
  def resolved[F[_]: MonadThrow](lookup: SourceLookup): F[Tag] =
    sources.resolved(lookup).map(s => copy(sources = s))
}

object Tag {
  implicit val ordering: Ordering[Tag] = Ordering.by(_.text)
  implicit val codec: Codec[Tag]       = deriveCodec

  def apply(text: CIString, sources: Source*): Tag =
    apply(text, Sources(sources))
}
