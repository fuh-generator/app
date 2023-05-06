package lgbt.princess.fuh

import cats.MonadThrow
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import org.typelevel.ci._

final case class Tag(text: CIString, sources: Sources) {
  def resolved[F[_]: MonadThrow](lookup: SourceLookup): F[Tag] =
    sources.resolved(lookup).map(s => copy(sources = s))
}

object Tag {
  implicit val ordering: Ordering[Tag] = {
    val base = new Ordering[Tag] {
      @inline private[this] def rank(tag: Tag): Int = tag match {
        case OP => 1
        case _ => 0
      }
      override def compare(x: Tag, y: Tag): Int =
        Ordering[Int].compare(rank(x), rank(y))
    }
    base.orElseBy(_.text)
  }
  implicit val codec: Codec[Tag]       = deriveCodec

  def apply(text: CIString, sources: Source*): Tag =
    apply(text, Sources(sources))

  final val OP = Tag(ci"Original Poster", Source.Canon)
}
