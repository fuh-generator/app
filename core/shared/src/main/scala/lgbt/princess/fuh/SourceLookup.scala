package lgbt.princess.fuh

import cats.MonadThrow
import org.typelevel.ci.CIString

trait SourceLookup {
  protected[this] def raiseFailedLookup[F[_]: MonadThrow](name: CIString): F[Source.Full] =
    MonadThrow[F].raiseError(
      new NoSuchElementException(s"no source found with name ${name.toString}")
    )

  def lookup[F[_]: MonadThrow](name: CIString): F[Source.Full]
}

object SourceLookup {
  private final case class Impl(map: Map[CIString, Source.Full]) extends SourceLookup {
    def lookup[F[_]: MonadThrow](name: CIString): F[Source.Full] =
      map.get(name).fold(raiseFailedLookup(name))(_.pure[F])
  }

  val empty: SourceLookup = Impl(Map.empty)

  def apply[F[_]: MonadThrow](sources: Seq[Source.Full]): F[SourceLookup] =
    build(empty, sources, Nil)

  def apply[F[_]: MonadThrow](sources: Seq[Source.Full], aliases: Seq[SourceAlias]): F[SourceLookup] =
    for {
      l1 <- apply(sources)
      l2 <- build(l1, sources, aliases)
    } yield l2

  private[this] def build[F[_]: MonadThrow](
      lookup: SourceLookup,
      sources: Seq[Source.Full],
      aliases: Seq[SourceAlias]
  ): F[SourceLookup] = {
    val sourceMappings =
      sources.view
        .map(s => (s.name -> s).pure[F])
        .toSeq
        .sequence
    val aliasMappings =
      aliases.view
        .map { a => lookup.lookup(a.value).map(a.value -> _) }
        .toSeq
        .sequence
    val mappings = for {
      m1 <- sourceMappings
      m2 <- aliasMappings
    } yield m1.view ++ m2.view

    @inline def multipleWithName(name: CIString): F[(CIString, Source.Full)] =
      MonadThrow[F].raiseError(
        new IllegalArgumentException(s"multiple sources associated with the name $name")
      )

    mappings
      .flatMap { ms =>
        ms.groupMap(_._1)(_._2)
          .view
          .map {
            case (name, view) if view.sizeIs == 1 => (name -> view.head).pure[F]
            case (name, _)                        => multipleWithName(name)
          }
          .to(LazyList)
          .sequence
      }
      .map(ll => Impl(ll.toMap))
  }
}
