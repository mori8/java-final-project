import java.io.Serializable;
import java.util.*;

public class ItemCollections implements Serializable {
    Vector<Item> v = new Vector<>();

    // add(): 컬렉션에 item 추가
    public void add(Item item) {
        v.add(item);
    }

    public void set(int index, Item item) {
        v.set(index, item);
    }

    // delete(): title을 제목으로 가진 객체를 삭제
    public void delete(Item item) {
        v.remove(item);
    }

    public int indexOf(Item item) {
        return v.indexOf(item);
    }

    // 제목으로 검색했을 때 해당하는 객체의 제목들을 벡터 타입으로 리턴
    public Vector<String> findAllByTitle(String keyword) {
        Vector<String> titles = new Vector<>();
        Iterator<Item> it = findAllItemsByTitle(keyword).iterator();
        while (it.hasNext()) {
            String str = it.next().getTitle();
            if (str.contains(keyword)) titles.add(str);
        }
        return titles;
    }
    // 제목으로 검색했을 때 해당하는 객체들을 벡터 타입으로 리턴
    public Vector<Item> findAllItemsByTitle(String keyword) {
        Vector<Item> items = new Vector<>();
        Iterator<Item> it = v.iterator();
        while (it.hasNext()) {
            Item i = it.next();
            if (i.getTitle().contains(keyword)) items.add(i);
        }
        return items;
    }
    // 별점으로 검색했을 때 해당하는 객체들의 제목을 벡터 타입으로 리턴
    public Vector<String> findAllByStars(int star) {
        Vector<String> titles = new Vector<>();
        Iterator<Item> it = findAllItemByStars(star).iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getStars() >= star) titles.add(item.getTitle());
        }
        return titles;
    }
    // 별점으로 검색했을 때 해당하는 객체들을 벡터 타입으로 리턴
    public Vector<Item> findAllItemByStars(int star) {
        Vector<Item> items = new Vector<>();
        Iterator<Item> it = v.iterator();
        while (it.hasNext()) {
            Item i = it.next();
            if (i.getStars() >= star) items.add(i);
        }
        return items;
    }

    public Movie findMovie(String title) {
        for (Item i: v) {
            if (i.getTitle().equals(title) && i instanceof Movie) {
                return (Movie) i;
            }
        }
        return null;
    }

    public Book findBook(String title) {
        for (Item i: v) {
            if (i.getTitle().equals(title) && i instanceof Book) {
                return (Book) i;
            }
        }
        return null;
    }

    public Item findByIndex(int index) {
        return v.elementAt(index);
    }

    public Vector<String> getTitles() {
        Vector<String> titles = new Vector<>();
        for (Item i: v) {
            titles.add(i.getTitle());
        }
        return titles;
    }

    public Vector<String> getMovieTitles() {
        Vector<String> titles = new Vector<>();
        for (Item i: v) {
            if (i instanceof Movie)
                titles.add(i.getTitle());
        }
        return titles;
    }

    public Vector<String> getBookTitles() {
        Vector<String> titles = new Vector<>();
        for (Item i: v) {
            if (i instanceof Book)
                titles.add(i.getTitle());
        }
        return titles;
    }
}
