package lgbt.princess.fuh
package data

import scala.collection.generic.IsMap

trait Associations[A <: Association[_], M <: Associations[A, M]] {
  self: M =>
  val map: Map[User, Seq[A]]

  def factory: Associations.Factory[A, M]

  def merge(that: M): M =
    factory.unsafeFromMap(
      (this.map.view ++ that.map.view)
        .groupMap(_._1)(_._2)
        .view
        .mapValues {
          case view if view.sizeIs == 1 => view.head
          case multiple                 => multiple.flatten.iterator.distinct.toSeq
        }
        .toMap
    )

  final def ++(that: M): M = merge(that)
}

object Associations {
  implicit def isMap[A0 <: Association[_], M <: Associations[A0, M]]: IsMap[M] { type K = User; type V = Seq[A0] } =
    new IsMap[M] {
      type K = User
      type V = Seq[A0]
      type C = Map[K, V]
      def apply(c: M): C = c.map
    }

  abstract class Factory[A <: Association[_], M <: Associations[A, M]] {
    private[data] def unsafeFromMap(map: Map[User, Seq[A]]): M

    def from(associations: Iterable[A]): M =
      unsafeFromMap(
        associations.view
          .groupBy(_.user)
          .view
          .mapValues {
            _.iterator.distinct.toSeq
          }
          .toMap
      )

    final def apply(associations: A*): M = from(associations)
  }
}
