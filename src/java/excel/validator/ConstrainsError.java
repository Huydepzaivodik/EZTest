package excel.validator;

import java.util.Arrays;

public class ConstrainsError {//lỗi của validate

    private String type;
    private Object[] params;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }


    public ConstrainsError type(String type) {
        setType(type);
        return this;
    }

    public ConstrainsError params(Object... params) {
        setParams(params);
        return this;
    }

    @Override
    public String toString() {
        return "ConstrainsError{" +
                "type='" + type + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
