package lgbt.princess.fuh
package data

import org.typelevel.ci._

object Characters {
  private[this] def canonical(name: String): Name = Name(CIString(name), Source.Canon)
  private[this] def fanonical(name: String): Name = Name(CIString(name), Source.Fanon)

  object Canon {
    final val AmyDallon = Character(
      canonical("Amy Claire Dallon"),
      canonical("Amelia Claire Dallon"),
      canonical("Amelia Claire Lavere"),
      canonical("Ames"),
      canonical("Panacea"),
      canonical("Prisoner 612"),
      canonical("The Red Queen"),
      fanonical("mousy haired"),
      Name(ci"racoon", Source.Gaylor),
      Name(ci"Pandemic", Source.Inheritance)
    )
    final val Dragon = Character(
      canonical("Dragon"),
      canonical("Tess Theresa Richter"),
    )
    final val GregVeder = Character(canonical("Greg Veder"))
    final val LisaWilbourn = Character(
      canonical("Lisa Wilbourn"),
      canonical("Sarah Livsey"),
      canonical("Tattletale"),
      canonical("Tt"),
      canonical("TT"),
      canonical("Tats"),
      canonical("the negotiator"),
      fanonical("Insight"),
      fanonical("vulpine smirk"),
    )
    final val Simurgh = Character(
      canonical("The Simurgh"),
      canonical("Ziz"),
      canonical("Israfel"),
      canonical("The Third"),
      canonical("Ulama"),
      canonical("lady in silver"),
      canonical("the all-knowing angel"),
      canonical("the silver woman"),
      canonical("the winged woman"),
      canonical("the winged Endbringer"),
      canonical("Simmy"),
      canonical("psycho alien bird bitch"),
      canonical("lunatic alien bird thing"),
      canonical("bird woman"),
      canonical("the smurf"),
      fanonical("Simmie"),
    )
    final val Taylor = Character(
      canonical("Taylor Anne Hebert"),
      canonical("Bug"),
      canonical("Skitter"),
      canonical("Weaver"),
      canonical("Khepri"),
      canonical("Bug Girl"),
      fanonical("Tay"),
      fanonical("TayTay"),
      fanonical("Tay Tay"),
      Name(ci"Butcher XV", Source.Inheritance),
    )
    final val VictoriaDallon = Character(
      canonical("Victoria Dallon"),
      canonical("Vicky Dallon"),
      canonical("Glory Girl"),
      canonical("Antares"),
      canonical("Alexandria Junior"),
      canonical("Glory Hole"),
      canonical("Big V"),
      fanonical("GG"),
      fanonical("Collateral Damage Barbie"),
    )
  }
}
