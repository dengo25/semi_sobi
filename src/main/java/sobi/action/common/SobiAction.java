package sobi.action.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SobiAction {
  String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
  
}