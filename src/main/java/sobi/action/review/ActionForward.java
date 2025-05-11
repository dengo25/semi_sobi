package sobi.action.review;


/**
 * 액션 실행 후 이동할 경로와 이동 방식(forward/redirect)을 저장하는 클래스
 */
public class ActionForward {
    private String path;
    private boolean redirect;

    public ActionForward() {}

    public ActionForward(String path, boolean redirect) {
        this.path = path;
        this.redirect = redirect;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }
}
