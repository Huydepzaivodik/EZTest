package excel.converter;

import java.util.Arrays;

public class ConvertError {// lỗi ở đọc

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

    public ConvertError type(String type) {
        setType(type);
        return this;
    }

    public ConvertError params(Object... params) {
        setParams(params);
        return this;
    }

    @Override
    public String toString() {
        return "Field " + Arrays.toString(params) + " Is Invalid format data";
    }
}
