package lgbt.princess.fuh
package data

import lgbt.princess.fuh.{Nickname, User}

final case class Nicknames private (map: Map[User, Seq[Nickname]]) extends Associations[Nickname, Nicknames] {
  def factory: Associations.Factory[Nickname, Nicknames] = Nicknames
}

// TODO: handle automatically swapping `_` with ` `,
//       as well as just dropping either when generating search map
object Nicknames extends Associations.Factory[Nickname, Nicknames] {
  private type Nick = Nickname

  private[data] def unsafeFromMap(map: Map[User, Seq[Nick]]): Nicknames = apply(map)

  object Canon {
    final val `Tin_Mommy`: Nick = Users.Canon.`Tin_Mother`.nicknamed("Tin_Mommy")

    val all: Nicknames =
      apply(`Tin_Mommy`)
  }
}
