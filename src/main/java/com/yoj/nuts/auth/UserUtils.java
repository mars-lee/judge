package com.yoj.nuts.auth;

import com.yoj.web.bean.User;
import com.yoj.web.bean.util.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserUtils {
    public User getCurrentUser(){
        UserDetailsImpl userDetails = null;
        User user = null;
        try{
            userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = (User) userDetails.getUser();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
