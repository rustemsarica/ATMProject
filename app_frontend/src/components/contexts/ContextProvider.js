import { useContext } from "react";
import { useState } from "react";
import { createContext } from "react";

const StateContext = createContext ({
    username: null,
    userId: null,
    token:null,
    role:null,
    refreshToken:null,
    isAdmin: null,
    pageName: 'Home',
    themeMode: 'dark',
    setName: () => {},
    setUserId: () => {},
    setToken: () => {},
    setRole: () => {},
    setRefreshToken: () => {},
    setIsAdmin: () => {},
    setPageName: () => {},
    setThemeMode: () => {},
})

export const ContextProvider = ({children}) => {
    const [username, _setName] = useState(localStorage.getItem('USERNAME'));
    const [userId, _setUserId] = useState(localStorage.getItem('USERID'));
    const [token, _setToken] = useState(localStorage.getItem('ACCESS_TOKEN'));
    const [role, _setRole] = useState(localStorage.getItem('ROLE'));
    const [isAdmin, _setIsAdmin] = useState(localStorage.getItem('IS_ADMIN'));
    const [refreshToken, _setRefreshToken] = useState(localStorage.getItem('REFRESH_TOKEN'));
    const [pageName, _setPageName] = useState(localStorage.getItem('PAGE_NAME'));
    const [themeMode, _setThemeMode] = useState(localStorage.getItem('THEME_MODE'));

    const setToken = (token)=>{
        _setToken(token)
        if(token){
            localStorage.setItem('ACCESS_TOKEN', token);
        }else{
            localStorage.removeItem('ACCESS_TOKEN');
        }
    }
    const setRefreshToken = (refreshToken)=>{
        _setRefreshToken(refreshToken)
        if(refreshToken){
            localStorage.setItem('REFRESH_TOKEN', refreshToken);
        }else{
            localStorage.removeItem('REFRESH_TOKEN');
        }
    }
    const setName = (username)=>{
        _setName(username)
        if(username){
            localStorage.setItem('USERNAME', username);
        }else{
            localStorage.removeItem('USERNAME');
        }
    }
    const setUserId = (userId)=>{
        _setUserId(userId)
        if(userId){
            localStorage.setItem('USERID', userId);
        }else{
            localStorage.removeItem('USERID');
        }
    }

    const setRole = (role)=>{
        _setRole(role)
        if(role){
            localStorage.setItem('ROLE', role);
        }else{
            localStorage.removeItem('ROLE');
        }
    }

    const setIsAdmin = (isAdmin)=>{
        _setIsAdmin(isAdmin)
        if(role){
            localStorage.setItem('IS_ADMIN', isAdmin);
        }else{
            localStorage.removeItem('IS_ADMIN');
        }
    }

    const setPageName = (pageName)=>{
        document.title = "ATM Project | "+pageName;   
        _setPageName(pageName)
        localStorage.setItem('PAGE_NAME', pageName);  
    }

    const setThemeMode = (themeMode)=>{
        _setThemeMode(themeMode);
        if(themeMode){
            localStorage.setItem('THEME_MODE', themeMode);
        }else{
            localStorage.setItem('THEME_MODE', 'dark');
        }       
    }
    
    return (
        <StateContext.Provider value={{
            username,
            userId,
            token,
            role,
            refreshToken,
            isAdmin,
            setName,
            setUserId,
            setToken,
            setRole,
            setRefreshToken,
            setIsAdmin,
            pageName,
            setPageName,
            themeMode,
            setThemeMode
        }}>
            {children}
        </StateContext.Provider>
    )
}

export const useStateContext = () => useContext(StateContext)