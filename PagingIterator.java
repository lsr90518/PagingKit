
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * DB等を回す時に一気に取ってくるのが心配な場合にこれを使ってください。
 * 基本的なイテレーターの用にfor文で回してもらえれば、次のデータが無くなった際に
 * コンストラクタで渡した処理を、取得しにいきます。
 *
 * 以下の場合、ユーザーIDをデフォルト(100件)ずつ取得しにいきます。
 * <pre>
 * PagingIterator<Long> userIdIterator = new PagingIterator<>(
 *    paging -> userDao.getListByFromToUserId(0L, 1000000L, paging));
 * while (userIdIterator.hasNext() && postCount < count) {
 *   log.debug("userId[{}]", userIdIterator.next());
 * }
 * </pre>
 *
 */
public class PagingIterator<E> implements Iterator {

    private static final Integer DEFAULT_LIMIT = 100;
    private Pagination<E> pagination;
    private Paging paging;
    private Iterator<E> currentPage;
    private Iterator<E> nextPage;

    public PagingIterator(Pagination<E> pagination, Paging paging) {
        this.pagination = pagination;
        this.paging = paging;
        Collection<E> page = pagination.next(paging);
        currentPage = page.iterator();
    }

    public PagingIterator(Pagination<E> pagination) {
        this(pagination, Paging.builder().offset(0L).limit(DEFAULT_LIMIT).build());
    }

    @Override
    public E next() {
        if (currentPage.hasNext()) {
            return currentPage.next();
        }

        if (nextPage == null) {
            getNextPage();
        }

        currentPage = nextPage;
        nextPage = null;
        return currentPage.next();
    }

    @Override
    public boolean hasNext() {
        if (currentPage.hasNext()) {
            return true;
        }

        if (nextPage == null) {
            getNextPage();
        }

        return nextPage.hasNext();
    }

    private void getNextPage() {
        paging.setOffset(paging.getOffset() + paging.getLimit());
        Collection<E> page = pagination.next(paging);
        nextPage = page.iterator();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(Consumer action) {
        throw new UnsupportedOperationException();
    }

    public interface Pagination<T> {
        Collection<T> next(Paging paging);
    }
}
