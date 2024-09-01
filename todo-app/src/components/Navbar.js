import axios from 'axios';
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Navbar() {
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
        await axios.post('api/auth/logout');
        sessionStorage.removeItem('userId');
        navigate('/login')
    }
    catch(error){
        console.log("Logout failed",error);
    }
  }
  return (
    <nav className="bg-gray-800 p-4">
      <div className="mx-2 flex justify-between items-center">
        <Link to="/projects" className="text-white text-lg font-bold">
          My Projects
        </Link>
        <div>
          <Link to="/projects" className="text-gray-300 hover:text-white mx-2">
            Projects
          </Link>
          <Link to="/login" className="text-gray-300 hover:text-white mx-2" onClick={handleLogout}>
            Logout
          </Link>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
