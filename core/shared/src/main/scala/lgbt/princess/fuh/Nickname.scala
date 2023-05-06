package lgbt.princess.fuh

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import org.typelevel.ci.CIString

final case class Nickname(user: User, value: CIString) extends Association[CIString] {
  override def associatedValue: CIString = value
}

object Nickname {
  implicit val ordering: Ordering[Nickname] =
    Ordering
      .by[Nickname, User](_.user)
      .orElseBy(_.associatedValue)
  implicit val codec: Codec[Nickname] = deriveCodec
}
