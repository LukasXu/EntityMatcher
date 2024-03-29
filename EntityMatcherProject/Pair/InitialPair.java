package EntityMatcherProject.Pair;
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

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getMentions() {
        return mentions;
    }
}
