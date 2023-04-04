package info.kaspian.kevin;

import io.reactivex.Flowable;

public interface ChatResponse {
    Flowable<String> responseStream();
}
