package vo;

import java.util.Set;

/**
 * Created by maqiyue on 2018/5/20
 */
public class RangeFrequency {
    private int frequency;
    private Set<Integer> range;

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Set<Integer> getRange() {
        return range;
    }

    public void setRange(Set<Integer> range) {
        this.range = range;
    }
    public void add(int id){
        range.add(id);
    }

}
