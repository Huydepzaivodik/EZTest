package excel.validator;

public interface IDataValidator {// lớp chuyên validate dữ liệu
    //nếu mà ko thống nhất 
    ConstrainsError validate(Object value);// value là giá trị truyền vào, trong trường hợp lỗi thì output sẽ khác null, và ngược lại
}
