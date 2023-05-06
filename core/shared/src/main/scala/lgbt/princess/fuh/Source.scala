package lgbt.princess.fuh

import cats.MonadThrow
import io.circe.generic.semiauto.deriveCodec
import io.circe.{Codec, Decoder, Encoder}
import org.http4s.syntax.literals._
import org.typelevel.ci._

import scala.annotation.unused

sealed trait Source {
  def identifier: CIString
  def resolved[F[_]: MonadThrow](lookup: SourceLookup): F[Source.Full]
}

object Source {
  implicit val ordering: Ordering[Source] = {
    val base = new Ordering[Source] {
      @inline def rank(source: Source): Int = source.identifier match {
        case Canon.name  => 3
        case Fanon.name  => 2
        case Gaylor.name => 1
        case _           => 0
      }
      override def compare(x: Source, y: Source): Int =
        Ordering[Int].compare(rank(x), rank(y))
    }
    base.orElseBy(_.identifier)
  }

  final case class Id(value: CIString) extends Source {
    def identifier: CIString = value
    def resolved[F[_]: MonadThrow](lookup: SourceLookup): F[Full] =
      lookup.lookup(value)
  }

  object Id {
    implicit val codec: Codec[Id] =
      Codec.from(
        Decoder[CIString].map(apply),
        Encoder[CIString].contramap(_.value)
      )
  }

  final case class Full(name: CIString, links: Link*) extends Source {
    def toId: Id = Id(name)

    def identifier: CIString = name
    def resolved[F[_]: MonadThrow](@unused lookup: SourceLookup): F[Full] =
      (this: Full).pure[F]
  }

  object Full {
    implicit val codec: Codec[Source] = deriveCodec
  }

  implicit val codec: Codec[Source] =
    Codec.from(
      Id.codec.or(Full.codec),
      Encoder.instance {
        case id: Id     => Id.codec(id)
        case full: Full => Full.codec(full)
      }
    )

  final val Canon = Full(
    ci"canon",
    Link(uri"https://parahumans.wordpress.com/", "site"),
    Link(uri"https://worm.fandom.com/wiki/Worm_Wiki", "wiki"),
  )
  final val Fanon  = Full(ci"fanon")
  final val Gaylor = Full(ci"Gaylor Convention Center", Link(uri"https://discord.gg/gaylor", "Discord"))
  final val Inheritance =
    Full(ci"Inheritance", Link(uri"https://archiveofourown.org/works/36326725/chapters/90564028", "AO3"))
}
