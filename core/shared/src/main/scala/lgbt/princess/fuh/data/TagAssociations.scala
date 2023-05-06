package lgbt.princess.fuh
package data

final case class TagAssociations(map: Map[User, Seq[TagAssociation]])
    extends Associations[TagAssociation, TagAssociations] {
  def factory: Associations.Factory[TagAssociation, TagAssociations] = TagAssociations
}

object TagAssociations extends Associations.Factory[TagAssociation, TagAssociations] {
  private type Assoc = TagAssociation

  private[data] def unsafeFromMap(map: Map[User, Seq[Assoc]]): TagAssociations = apply(map)

  object Canon {
    object `Bagrat` {
      final val `The Guy in the Know`: Assoc =
        Users.Canon.`Bagrat`.hasTag(Tags.Canon.`The Guy in the Know`, Source.Canon)
      final val `Veteran Member`: Assoc = Users.Canon.`Bagrat`.hasTag(Tags.Canon.`Veteran Member`, Source.Canon)
    }

    val all: TagAssociations =
      apply(
        `Bagrat`.`The Guy in the Know`,
        `Bagrat`.`Veteran Member`,
      )
  }
}
