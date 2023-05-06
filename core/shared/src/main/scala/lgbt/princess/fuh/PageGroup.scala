package lgbt.princess.fuh

final case class PageGroup(startingPageNumber: Int, posts: IndexedSeq[Post]) {
  require(startingPageNumber > 0, s"starting page number must be positive; was $startingPageNumber")

  def alignsWith(postsPerPage: Int): Boolean = {
    require(postsPerPage > 0, s"number of posts per page must be positive; was $postsPerPage")
    posts.length % postsPerPage == 0
  }
}
