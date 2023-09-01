
import './App.css';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useStateContext } from "./components/contexts/ContextProvider";
import { ThemeProvider , createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Home from './components/Home/Home';
import Register from './components/Auth/Register';
import Login from './components/Auth/Login';
import Header from "./components/Partials/Header";
import {  Container } from "@mui/material";
import UserDetail from './components/UserDetail';
const theme = createTheme({
  palette: {
    background: {
      default: "#e76097"
    },
    mode: 'light',
    common:{
      black:'#000',
      white: '#fff'
    },
    primary: {
      main: '#e0196a',
      soft:'#e76097',
      dark:'#b91660'
    },
    action:{
      disabled:'#e76097'
    }
    
  }
});

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
    common:{
      black:'#000',
      white: '#fff'
    },
    primary: {
      main: '#e0196a',
      soft:'#e76097',
      dark:'#b91660'
    },
    action:{
      disabled:'#e76097'
    }
  },
});

function App() {
  const {token,isAdmin,themeMode} = useStateContext();
  return (
    <div className="App" style={{paddingBottom:0}}>
      <ThemeProvider  theme={themeMode==='light' ? theme : darkTheme}>
        <CssBaseline />
        
          <div className='container'  style={{height:'100vh'}}>

            <Header></Header>
              
            <div className='row' style={{ verticalAlign:'middle' }}>
                <BrowserRouter>
                  <Routes>
                    <Route exact path='/' element={token == null ? <Navigate to="/auth/login"/> : <Home  />}></Route>

                    <Route exact path='/user/:userId' element={!isAdmin ? <Navigate to="/"/> : <UserDetail/>}></Route>
                    <Route exact path='/auth/register' element={token != null ? <Navigate to="/"/> : <Register/>}></Route>
                    <Route exact path='/auth/login' element={token != null ? <Navigate to="/"/> : <Login/>}></Route>
                    
                  </Routes>      
                </BrowserRouter>   
            </div>
          </div>
      </ThemeProvider>
    </div>
  );
}

export default App;
