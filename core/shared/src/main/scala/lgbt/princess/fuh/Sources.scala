package lgbt.princess.fuh

import cats.MonadThrow
import io.circe.Codec
import io.circe.generic.extras.semiauto.deriveUnwrappedCodec

import scala.collection.generic.IsSeq

final case class Sources(value: Seq[Source]) {
  def resolved[F[_]: MonadThrow](lookup: SourceLookup): F[Sources] =
    value
      .map(_.resolved(lookup))
      .sequence
      .map(Sources(_))
}

object Sources {
  implicit val isSeq: IsSeq[Sources] { type A = Source } =
    new IsSeq[Sources] {
      override type A = Source
      override type C = Seq[Source]
      override def apply(coll: Sources): C = coll.value
    }
  implicit val codec: Codec[Sources] = deriveUnwrappedCodec
}
