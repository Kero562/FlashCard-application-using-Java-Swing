package JavaFx.FlashCard;

public class FlashCard {
    private String question;
    private String answer;

    FlashCard(String question, String answer)
    {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
