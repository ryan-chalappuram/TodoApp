import React, { useEffect } from 'react';
import { Routes, Route, useNavigate, useLocation } from 'react-router-dom';
import Login from './components/Login';
import Project from './components/ProjectList';
import './App.css';
import axios from 'axios';
import Register from './components/Register';
import ProjectDetail from './components/ProjectDetails';
import Navbar from './components/Navbar';

axios.defaults.baseURL = 'http://localhost:8080/';
axios.defaults.withCredentials = true;

function App() {
  const [isAuthenticated,setIsAuthenticated] = React.useState();
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(()=>{
    const userId = sessionStorage.getItem('userId')
    if(userId){
      setIsAuthenticated(true);
    }  
    else{
      setIsAuthenticated(false);
      navigate('/login');
    }
  }, [navigate]);
  const showNavbar = location.pathname !== '/login' && location.pathname !== '/register';
  return (
    <div>
      {showNavbar && <Navbar />}
      <Routes>  
        <Route path="/login" element={<Login />} />
        <Route path='/register' element={<Register />} />
          {  isAuthenticated && 
              (
              <>
                <Route path='/projects' element={ <Project />} />
                <Route path='/projects/:projectId' element={<ProjectDetail />} />  
              </>
            )
          }
          
      </Routes>
    </div>
  );
}

export default App;
