package EntityMatcherProject;

public class Pair {
    private int first;
    private int second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public boolean isSame(Pair p) {
        if(p.getFirst() == this.first && p.getSecond() == this.second) {
            return true;
        }
        if(p.getFirst() == this.second && p.getSecond() == this.first) {
            return true;
        }
        return false;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Pair(" + first + ", " + second + ")";
    }
}
