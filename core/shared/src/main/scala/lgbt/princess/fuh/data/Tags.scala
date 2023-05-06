package lgbt.princess.fuh
package data

import org.typelevel.ci._

object Tags {
  private[this] def canonical(text: String): Tag = Tag(CIString(text), Source.Canon)

  object Canon {
    final val `Admin`                = canonical("Admin")
    final val `Brockton Bay Refugee` = canonical("Brockton Bay Refugee")
    final val `The Guy in the Know`  = canonical("The Guy in the Know")
    final val `Verified Cape`        = canonical("Verified Cape")
    final val `Veteran Member`       = canonical("Veteran Member")
  }
}
