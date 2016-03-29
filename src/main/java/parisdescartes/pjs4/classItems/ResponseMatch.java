package parisdescartes.pjs4.classItems;

/**
 * Created by Killian on 29/03/2016.
 */
public class ResponseMatch {
    private Integer code;
    private Boolean matched;

    public ResponseMatch(Integer code, Boolean matched) {
        this.code = code;
        this.matched = matched;
    }

    public Integer getCode() {
        return code;
    }

    public Boolean getMatched() {
        return matched;
    }
}
