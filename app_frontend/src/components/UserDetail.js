import React, {useState, useEffect} from "react";
import axiosClient from "../axios-client";
import { useStateContext } from "./contexts/ContextProvider";

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { Card, CardContent, CardHeader, Container, Paper, TableContainer, Typography } from "@mui/material";
import TablePagination from '@mui/material/TablePagination';
import { useNavigate, useParams } from "react-router-dom";

function UserDetail(){
    const navigate = useNavigate();
    const {userId} = useParams();

    const {setPageName, token, role, isAdmin} = useStateContext();

    const [user, setUser] = useState(null);

    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [transactions, setTransactions] = useState([]);

    const[page, setPage] = useState(0);
    const[totalPages, setTotalPages] = useState(0);
    const[totalElements, setTotalElements] = useState(0);
    const[rowsPerPage, setRowsPerPage] = useState(10);

    const handleChangePage = (event, newPage) => {        
        setPage(newPage);
        getTransactions(newPage, rowsPerPage);
    };
    
    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(event.target.value);
        setPage(0);
        getTransactions(0,event.target.value);
    };

    
    const getTransactions = (pageNumber, size) => {
        axiosClient.get("/transactions/user/"+userId+"?page="+parseInt(pageNumber)+"&size="+parseInt(size))
        .then(
            ({data})=>{
                console.log(data)
                setTransactions(data.content);
                setTotalPages(data.totalPages);
                setTotalElements(data.totalElements);
            },
            (error)=>{
                setIsLoaded(true);
                setError(error);
            }
        )
    }

    const getUser = () => {
        axiosClient.get("/users/"+userId)
        .then(
            ({data})=>{
                console.log(data)
                setUser(data);
            },
            (error)=>{
                setIsLoaded(true);
                setError(error);
            }
        )
    }
    
    useEffect(()=>{
        setPageName("Home");
        getTransactions(page,rowsPerPage);
        getUser();
    },[])

        
    return (
        <Container style={{marginBottom:"80px"}}>
            {user!=null &&
                <Card>
                    <CardContent>                        
                        <Typography variant="h5" component="div" gutterBottom>{user.name}</Typography>
                        <Typography variant="h6" component="div" gutterBottom>Balance : {user.balance}</Typography>
                        <Typography variant="h6" component="div" gutterBottom>{user.username}</Typography>
                    </CardContent>
                </Card>
            }
            
            <Paper>
                <TableContainer>
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                            <TableCell align="left">
                                Name
                            </TableCell>
                            <TableCell align="left">Amount</TableCell>
                            <TableCell align="left">Type</TableCell>
                            <TableCell align="right">Date</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                        {transactions.map((element,i) => (
                            <TableRow hover
                                key={i}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >                                
                                <TableCell style={{cursor:isAdmin&&"pointer"}} onClick={isAdmin ? () => {navigate("/home");} : null} align="left"> {element.user.name } </TableCell>
                                <TableCell align="left">{element.amount}</TableCell>
                                <TableCell align="left">{element.type}</TableCell>
                                <TableCell align="right">{new Date(element.createdDate).toUTCString()}</TableCell>
                            </TableRow>
                        ))}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[10, 25, 100]}
                    component="div"
                    count={totalElements}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </Paper>
        </Container>
    )
       
}

export default UserDetail;