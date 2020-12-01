import java.util.Vector;

public class ItemCollections {
    Vector<Item> v = new Vector<>();

    // add(): 컬렉션에 item 추가
    public void add(Item item) {
        v.add(item);
    }

    // modify(): title을 제목으로 가진 객체를 item으로 수정
    public void modify(String title, Item item) {
        Item target = find(title);
        if (target != null) {
            int idx = v.indexOf(target);
            v.set(idx, item);
        }
    }

    // delete(): title을 제목으로 가진 객체를 삭제
    public void delete(String title) {
        Item target = find(title);
        if (target != null) {
            int idx = v.indexOf(target);
            v.remove(idx);
        }
    }

    // find(): title을 제목으로 가진 객체를 리턴
    public Item find(String title) {
        for (Item i: v) {
            if (i.getTitle().equals(title)) {
                return i;
            }
        }
        return null;
    }

    public Vector<String> getTitles() {
        Vector<String> titles = new Vector<>();
        for (Item i: v) {
            titles.add(i.getTitle());
        }
        return titles;
    }
}
