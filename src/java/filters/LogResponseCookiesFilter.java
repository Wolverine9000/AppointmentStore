package filters;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogResponseCookiesFilter implements Filter
{

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ResponseCookiesWrapper wrappedResponse =
                new ResponseCookiesWrapper(httpResponse);

        chain.doFilter(request, wrappedResponse);

        // TODO determine how and what requests and responses to log
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        ServletContext sc = filterConfig.getServletContext();
        String filterName = filterConfig.getFilterName();
        String browser = httpRequest.getHeader("User-Agent");

        String remoteAddress = httpRequest.getRemoteAddr();

        StringBuilder returnString = new StringBuilder();
        StringBuilder append = returnString.append(httpRequest.getMethod()).append(" ").append(httpRequest.getRequestURL()).append(" ").append(request.getProtocol()).append("\n\n");
        Enumeration headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements())
            {
            String name = (String) headerNames.nextElement();
            String value = httpRequest.getHeader(name);
            StringBuilder append1 = returnString.append(name).append("\t").append(value).append("\n");
            }

        String servletPath = "Servlet path: " + httpRequest.getServletPath();
        ArrayList<Cookie> cookies = wrappedResponse.getCookies();
        String cookiesString = "";
        for (Cookie c : cookies)
            {
            cookiesString += c.getName() + "=" + c.getValue() + " ";
            }

        sc.log(filterName + " | " + servletPath + " Remote Address: " + remoteAddress + " Headers: " + returnString
                + " | cookies: "
                + cookiesString);
    }

    @Override
    public void destroy()
    {
        filterConfig = null;
    }

    class ResponseCookiesWrapper extends HttpServletResponseWrapper
    {

        private ArrayList<Cookie> cookies = null;

        public ResponseCookiesWrapper(HttpServletResponse response)
        {
            super(response);
            cookies = new ArrayList<>();
        }

        // provide a new method for the ResponseWrapper class
        public ArrayList<Cookie> getCookies()
        {
            return cookies;
        }

        // override the addCookie method of the HttpServletResponse object
        @Override
        public void addCookie(Cookie cookie)
        {
            // store the cookie in the response wrapper object
            cookies.add(cookie);

            // store the cookie in the original response object
            HttpServletResponse httpResponse =
                    (HttpServletResponse) this.getResponse();
            httpResponse.addCookie(cookie);
        }
    }
}
