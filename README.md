# PagingKit

  PagingIterator<Long> userIdIterator = new PagingIterator<>(
    paging -> userDao.getListByFromToUserId(0L, 1000000L, paging));
 while (userIdIterator.hasNext() && postCount < count) {
   log.debug("userId[{}]", userIdIterator.next());
 }

