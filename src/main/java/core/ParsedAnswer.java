package core;

/**
 * Record holding a parsed answer and if it's correct
 * @param correct
 * @param answer
 */
record ParsedAnswer(boolean correct, String answer) {
    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return correct;
    }
}
