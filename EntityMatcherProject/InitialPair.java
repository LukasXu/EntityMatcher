package EntityMatcherProject;
public class InitialPair {
    int year;
    int frequency;
    int mentions;

    public InitialPair(int year, int frequency, int mentions) {
        this.year = year;
        this.frequency = frequency;
        this.mentions = mentions;
    }

    public String toString() {
        return year + "," + frequency + "," + mentions;
    }

    public int getYear() {
        return year;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getMentions() {
        return mentions;
    }
}
