package lgbt.princess.fuh

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import org.http4s.Uri

final case class Link(uri: Uri, name: String)

object Link {
  implicit val codec: Codec[Link] = deriveCodec
}
