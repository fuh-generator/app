package lgbt.princess.fuh

trait Association[V] {
  val user: User
  def associatedValue: V
}
