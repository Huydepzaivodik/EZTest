package model;

import java.util.List;

public class SelectedChoices {

    private int studentId;
    private int questionId;
    private List<Integer> selectedAnswerIds;
    private int quizId;

    public SelectedChoices() {
    }

    private int teacherId;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public SelectedChoices(int questionId, List<Integer> selectedAnswerIds, int quizId, int teacherId) {
        this.questionId = questionId;
        this.selectedAnswerIds = selectedAnswerIds;
        this.quizId = quizId;
        this.teacherId = teacherId;
    }

    public SelectedChoices(int studentId, int questionId, List<Integer> selectedAnswerIds, int quizId) {
        this.studentId = studentId;
        this.questionId = questionId;
        this.selectedAnswerIds = selectedAnswerIds;
        this.quizId = quizId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public List<Integer> getSelectedAnswerIds() {
        return selectedAnswerIds;
    }

    public void setSelectedAnswerIds(List<Integer> selectedAnswerIds) {
        this.selectedAnswerIds = selectedAnswerIds;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

}
