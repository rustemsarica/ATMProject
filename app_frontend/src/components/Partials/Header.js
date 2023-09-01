import { Card, CardContent } from "@mui/material";
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { useStateContext } from "../../components/contexts/ContextProvider";

export default function Header() {
    const {pageName,token, username,setName, setUserId, setToken, setIsAdmin} = useStateContext();
    
    const onLogout = () => {        
        setName(null)
        setUserId(null)
        setToken(null)
        setIsAdmin(null)
    }

    return (
        <div className="row" style={{height:'80px'}}
            sx={{
                position:'sticky',
                top:0,
                opacity:0.98,
                zIndex:2,
                p:0,
                boxShadow:'none',
                border:'none',
                borderRadius:0,
                width:'100%',
            }}
        >
            <CardContent sx={{p:0, '&:last-child':{pb:0}}}>
                <Toolbar style={{justifyContent:"space-between"}}>          
                    <Typography href="/" variant="h6" noWrap component="a" style={{textDecoration:"none"}}>
                    Home
                    </Typography>
                    {token!=null &&
                    <Typography component="div" variant="h6" onClick={onLogout} style={{cursor:"pointer"}}>
                        Logout
                    </Typography>}
                </Toolbar>   
            </CardContent>
        </div>     
    );
}