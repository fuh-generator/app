package lgbt.princess

import cats.syntax.AllSyntaxBinCompat
import io.circe.{Codec, Decoder, Encoder}
import org.http4s.Uri
import org.http4s.circe
import org.typelevel.ci.CIString

package object fuh extends AllSyntaxBinCompat {
  implicit val ciStringCodec: Codec[CIString] =
    Codec.from(
      Decoder[String].map(CIString(_)),
      Encoder[String].contramap(_.toString)
    )
  implicit val uriCodec: Codec[Uri] =
    Codec.from(circe.decodeUri, circe.encodeUri)
}
