package uk.seicfg.security;

import java.io.Serializable;

import uk.seicfg.to.User;

public class Session implements Serializable
{

    private static final long serialVersionUID = 1L;
    public static final String SESSIONKEY = "ubsession";
    public static final String SESSION_USERNAME_KEY = "username";
    private User user;
    private String selectedCity;
    private Boolean isHtml5;
    private int screenWidth;

    public Session()
    {
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getSelectedCity()
    {
        return selectedCity;
    }

    public void setSelectedCity(String selectedCity)
    {
        this.selectedCity = selectedCity;
    }

    public Boolean getIsHtml5()
    {
        return isHtml5;
    }

    public void setIsHtml5(Boolean b)
    {
        isHtml5 = b;
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth)
    {
        this.screenWidth = screenWidth;
    }
}