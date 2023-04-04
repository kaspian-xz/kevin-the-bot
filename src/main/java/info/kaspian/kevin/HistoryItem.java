package info.kaspian.kevin;

public record HistoryItem(ChatRole role, String message) {

    @Override
    public String toString() {
        return "HistoryItem{" +
                "role=" + role +
                ", message='" + message + '\'' +
                '}';
    }
}
