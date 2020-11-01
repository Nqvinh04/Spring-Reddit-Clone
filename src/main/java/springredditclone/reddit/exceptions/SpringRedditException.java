package springredditclone.reddit.exceptions;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage, Exception e) {
        super(exMessage, e);
    }

    public SpringRedditException(String exception){
        super(exception);
    }
}
