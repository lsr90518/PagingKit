# PagingKit

 DB等を回す時に一気に取ってくるのが心配な場合にこれを使ってください。
 基本的なイテレーターの用にfor文で回してもらえれば、次のデータが無くなった際に
 コンストラクタで渡した処理を、取得しにいきます。
  以下の場合、ユーザーIDをデフォルト(100件)ずつ取得しにいきます。
 <pre>
 PagingIterator<Long> userIdIterator = new PagingIterator<>(
    paging -> userDao.getListByFromToUserId(0L, 1000000L, paging));
 while (userIdIterator.hasNext() && postCount < count) {
   log.debug("userId[{}]", userIdIterator.next());
 }
 </pre>
