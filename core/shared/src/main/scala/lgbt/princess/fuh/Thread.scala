package lgbt.princess.fuh

final case class Thread(originalPost: Post, totalPageCount: Int, pageGroups: IndexedSeq[PageGroup]) {
  require(totalPageCount > 0, s"total page count must be positive; was $totalPageCount")
}
