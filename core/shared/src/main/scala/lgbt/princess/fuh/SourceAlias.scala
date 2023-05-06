package lgbt.princess.fuh

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import org.typelevel.ci._

final case class SourceAlias(value: CIString, aliasOf: Source.Id)

object SourceAlias {
  implicit val ordering: Ordering[SourceAlias] =
    Ordering.by(_.value)
  implicit val codec: Codec[SourceAlias] = deriveCodec

  final val Gaylor = apply(ci"Gaylor", Source.Gaylor.toId)
}
