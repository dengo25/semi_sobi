package sobi.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SobiAction {
  String pro(HttpServletRequest request, HttpServletResponse response) throws Exception;
  
}