
package model;

import java.util.List;


public class SelectedChoiceDTO {
    private int questionId;
    private List<String> selectedAnswerIds;

    public SelectedChoiceDTO() {
    }

    public SelectedChoiceDTO(int questionId, List<String> selectedAnswerIds) {
        this.questionId = questionId;
        this.selectedAnswerIds = selectedAnswerIds;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public List<String> getSelectedAnswerIds() {
        return selectedAnswerIds;
    }

    public void setSelectedAnswerIds(List<String> selectedAnswerIds) {
        this.selectedAnswerIds = selectedAnswerIds;
    }
    
}
