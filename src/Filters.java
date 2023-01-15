public class Filters {
    private Sort sort;
    private Contains contains;

    public Filters() {
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Contains getContains() {
        return contains;
    }

    public void setContains(Contains contains) {
        this.contains = contains;
    }

    @Override
    public String toString() {
        return "Filters{" +
                "sort=" + sort +
                ", contains=" + contains +
                '}';
    }
}
