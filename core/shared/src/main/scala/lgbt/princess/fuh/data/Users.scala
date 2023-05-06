package lgbt.princess.fuh
package data

import org.typelevel.ci._

object Users {
  private[this] def canonical(username: String): User = User(CIString(username), Source.Canon)
  private[this] def fanonical(username: String): User = User(CIString(username), Source.Fanon)

  object Canon {
    final val `Bagrat`             = canonical("Bagrat")
    final val `Point_Me_@_The_Sky` = canonical("Point_Me_@_The_Sky")
    final val `Tin_Mother`         = canonical("Tin_Mother")
    final val `XxVoid_CowboyxX`    = canonical("XxVoid_CowboyxX")
  }

  object Fanon {
    final val `AllSeeingEye`   = fanonical("AllSeeingEye")
    final val `All_Seeing_Eye` = fanonical("All_Seeing_Eye")
    final val `WingedOne`      = fanonical("WingedOne")
    final val `Winged_One`     = fanonical("Winged_One")
  }
}
