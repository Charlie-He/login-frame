package org.example.loginframe.utils;

import lombok.Data;
import org.example.loginframe.entity.dto.User;

public class UserHolder {
    private static ThreadLocal<ReqInfo> tl = new ThreadLocal<>();

    public static void saveReqInfo(ReqInfo reqInfo){
        tl.set(reqInfo);
    }

    public static ReqInfo getReqInfo(){
        return tl.get();
    }

    public static void removeReqInfo(){
        tl.remove();
    }


    @Data
    public static class ReqInfo {
        private String token;

        private User user;

    }
}
