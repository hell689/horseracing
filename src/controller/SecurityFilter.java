package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Role;
import domain.User;

public class SecurityFilter implements Filter{
    
    private static final Map<String, Set<Role>> permissions = new HashMap<>();

    static {
        Set<Role> all = new HashSet<>();
        all.addAll(Arrays.asList(Role.values()));
        Set<Role> admin = new HashSet<>();
        admin.add(Role.ADMINISTRATOR);
        Set<Role> bookmaker = new HashSet<>();
        bookmaker.add(Role.BOOKMAKER);
        Set<Role> employees = new HashSet<>();
        employees.add(Role.ADMINISTRATOR);
        employees.add(Role.BOOKMAKER);
        Set<Role> client = new HashSet<>();
        client.add(Role.CLIENT);

        permissions.put("/logout", all);
        permissions.put("/password/edit", all);
        permissions.put("/password/save", all);
        permissions.put("/password/reset", admin);

        permissions.put("/user/list", admin);
        permissions.put("/user/edit", admin);
        permissions.put("/user/save", admin);
        permissions.put("/user/delete", admin);

        permissions.put("/bet/list", all);
        permissions.put("/bet/save", all);
        
        permissions.put("/horse/save", employees);
        permissions.put("/horse/edit", employees);
        permissions.put("/horse/list", all);

        permissions.put("/race/save", employees);
        permissions.put("/race/edit", employees);
        permissions.put("/race/list", all);
        
        permissions.put("/runner/save", employees);
        permissions.put("/runner/edit", employees);
        permissions.put("/runner/list", all);
        
        permissions.put("/user/list", admin);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {}
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest)req;
        HttpServletResponse httpResp = (HttpServletResponse)resp;
        String url = httpReq.getRequestURI();
        String context = httpReq.getContextPath();
        int postfixIndex = url.lastIndexOf(".html");
        if(postfixIndex != -1) {
            url = url.substring(context.length(), postfixIndex);
        } else {
            url = url.substring(context.length());
        }
        Set<Role> roles = permissions.get(url);
        if(roles != null) {
            HttpSession session = httpReq.getSession(false);
            if(session != null) {
                User user = (User)session.getAttribute("currentUser");
                if(user != null && roles.contains(user.getRole())) {
                    chain.doFilter(req, resp);
                    return;
                }
            }
        } else {
            chain.doFilter(req, resp);
            return;
        }
        httpResp.sendRedirect(context + "/login.html?message=Access denied");
    }


    
    @Override
    public void destroy() {}

}
