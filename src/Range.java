class Range {
    long from;
    long length;

    Range(long from, long length) {
        this.from = from;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Range{" +
                "from=" + from +
                ", length=" + length +
                '}';
    }
}
