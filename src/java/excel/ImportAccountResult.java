package excel;

public class ImportAccountResult {

    private String errorMessage;
    private Integer id;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ImportAccountResult errorMessage(String errorMessage) {
        setErrorMessage(errorMessage);
        return this;
    }

    public ImportAccountResult id(Integer id) {
        setId(id);
        return this;
    }
}
