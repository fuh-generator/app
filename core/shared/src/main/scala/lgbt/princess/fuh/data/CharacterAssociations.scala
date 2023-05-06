package lgbt.princess.fuh
package data

final case class CharacterAssociations(map: Map[User, Seq[CharacterAssociation]])
    extends Associations[CharacterAssociation, CharacterAssociations] {
  def factory: Associations.Factory[CharacterAssociation, CharacterAssociations] = CharacterAssociations
}

object CharacterAssociations extends Associations.Factory[CharacterAssociation, CharacterAssociations] {
  private type Assoc = CharacterAssociation

  private[data] def unsafeFromMap(map: Map[User, Seq[Assoc]]): CharacterAssociations = apply(map)

  import Characters.Canon._

  object Canon {
    final val `Point_Me_@_The_Sky`: Assoc = Users.Canon.`Point_Me_@_The_Sky`.isCharacter(VictoriaDallon, Source.Canon)
    final val `XxVoid_CowboyxX`: Assoc    = Users.Canon.`XxVoid_CowboyxX`.isCharacter(GregVeder, Source.Canon)

    val all: CharacterAssociations =
      apply(
        `Point_Me_@_The_Sky`,
        `XxVoid_CowboyxX`,
      )
  }

  object Fanon {
    final val `AllSeeingEye`: Assoc   = Users.Fanon.`AllSeeingEye`.isCharacter(LisaWilbourn, Source.Fanon)
    final val `All_Seeing_Eye`: Assoc = Users.Fanon.`All_Seeing_Eye`.isCharacter(LisaWilbourn, Source.Fanon)
    final val `WingedOne`: Assoc      = Users.Fanon.`WingedOne`.isCharacter(Simurgh, Source.Fanon)
    final val `Winged_One`: Assoc     = Users.Fanon.`Winged_One`.isCharacter(Simurgh, Source.Fanon)

    val all: CharacterAssociations =
      apply(
        `AllSeeingEye`,
        `All_Seeing_Eye`,
        `WingedOne`,
        `Winged_One`,
      )
  }
}
