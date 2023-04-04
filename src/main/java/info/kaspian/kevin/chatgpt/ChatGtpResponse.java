package info.kaspian.kevin.chatgpt;

import info.kaspian.kevin.ChatResponse;
import io.reactivex.Flowable;

public record ChatGtpResponse(Flowable<String> responseStream) implements ChatResponse {
}
