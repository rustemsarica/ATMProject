import React, {useState, useEffect} from "react";
import axiosClient from "../../axios-client";
import { useStateContext } from "../contexts/ContextProvider";

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { Container, Paper, TableContainer } from "@mui/material";
import TablePagination from '@mui/material/TablePagination';
import { useNavigate } from "react-router-dom";

import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';


function Home(){
    const navigate = useNavigate();

    const {setPageName, token, role, isAdmin} = useStateContext();

    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [transactions, setTransactions] = useState([]);

    const[page, setPage] = useState(0);
    const[totalPages, setTotalPages] = useState(0);
    const[totalElements, setTotalElements] = useState(0);
    const[rowsPerPage, setRowsPerPage] = useState(10);

    const[amount, setAmount] = useState(0);
    const[transaction, setTransaction] = useState(0);
    const[receiver, setReceiver] = useState("");

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
        axiosClient.get("/transactions?page="+parseInt(pageNumber)+"&size="+parseInt(size))
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

    const createTransactions = () => {
        var formData = new FormData();
        formData.append('amount', amount);
        formData.append('type', transaction);
        formData.append('receiver', receiver);
        
        axiosClient.post("/transactions/create", formData)
        .then(
            ({data})=>{       
                console.log(data);
                getTransactions(page,rowsPerPage);
            },
            (error)=>{
                console.log(error)
                setIsLoaded(true);
                setError(error);
            }
        )
        
    }
    
    useEffect(()=>{
        setPageName("Home");
        getTransactions(page,rowsPerPage);
    },[])

        
    return (
        <Container style={{marginBottom:"80px"}}>
            {isAdmin == "false" &&
                <Container>
                    <TextField id="outlined-basic" label="Amount" variant="outlined" defaultValue={amount} onChange={(e)=>{setAmount(e.target.value)}} />
                    <FormControl >
                        <InputLabel id="demo-simple-select-label">Transaction</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={transaction}
                            label="Transaction"
                            onChange={(e)=>{ setTransaction(e.target.value)}}
                        >
                            <MenuItem value={0}>Withdraw</MenuItem>
                            <MenuItem value={1}>Deposit</MenuItem>
                            <MenuItem value={2}>Transfer</MenuItem>
                        </Select>
                    </FormControl>
                    {transaction==2 &&
                        <TextField id="outlined-basic" label="Receiver" variant="outlined" value={receiver} onChange={(e)=>{setReceiver(e.target.value)}} />
                    }
                    <Button disabled={amount>0 & (transaction!=2 || (receiver!="" && receiver!=null)) ? false : true } onClick={ createTransactions } variant="contained">Contained</Button>
                </Container>
            }
            <Paper>
                <TableContainer>
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                            <TableCell align="left">
                            </TableCell>
                            <TableCell align="left" colSpan={4}>Details</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                        {transactions.map((element,i) => (
                            <TableRow hover
                                key={i}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >                                
                                <TableCell style={{cursor:isAdmin&&"pointer"}} onClick={isAdmin ? () => {navigate("/user/"+element.user.id);} : null} align="left"> {element.user.name } </TableCell>
                                <TableCell align="right">{element.amount}</TableCell>
                                <TableCell align="right">{element.type}</TableCell>
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

export default Home;