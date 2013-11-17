package uk.seicfg.service;

import uk.seicfg.to.User;

public interface SecurityService
{

     User authenticateUser(String s, String s1)
        throws Exception;

     User getActiveUser(String s);

     boolean updatePassword(String s, String s1);

     boolean logout(uk.seicfg.orm.entities.SEIUser user);

     boolean lockUser(String s);

     boolean unLockUser(String s);

     boolean changePassword(String s, String s1)
        throws Exception;

}