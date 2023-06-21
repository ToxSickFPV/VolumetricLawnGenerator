package engineer.straub.model;

public class GeneratorResult {

    private boolean success;
    private String message = "";

    public GeneratorResult(boolean success) {
        this.success = success;
    }

    public GeneratorResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
