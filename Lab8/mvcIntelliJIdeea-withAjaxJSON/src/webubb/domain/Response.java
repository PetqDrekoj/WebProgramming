package webubb.domain;

public class Response {
    private int id;
    private String response;
    private int question_id;
    private boolean correct;

    public Response(int id, String response, int question_id, boolean correct) {
        this.id = id;
        this.response = response;
        this.question_id = question_id;
        this.correct = correct;
    }

    public Response() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
