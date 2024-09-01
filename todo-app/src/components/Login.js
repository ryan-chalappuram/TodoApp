import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';


function Login() {
    const [userName, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const [showMessage, setShowMessage] = React.useState(false);
    const [message, setMessage] = React.useState('');

    const handleLogin = async () => {
        try {
            const response = await axios.post(`/api/auth/login`, { userName, password });
            if (response.status === 200) {
                sessionStorage.setItem('userId',response.data['id'])
                navigate('/projects'); 
                //console.log("susssfs")
            }
        } catch (error) {
            if(error.response['status'] === 401){
                setShowMessage(true);
                setMessage(error.response['data'])
            }
            console.error('Login failed', error);
        }
    };

    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <div className="bg-white p-8 rounded-lg shadow-md w-96">
                <h2 className="text-2xl font-bold mb-4">Login</h2>
                <input
                    type="text"
                    placeholder="Username"
                    className="w-full p-2 mb-4 border rounded-md"
                    value={userName}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    className="w-full p-2 mb-4 border rounded-md"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button
                    className="w-full bg-blue-500 text-white py-2 rounded-md"
                    onClick={handleLogin}
                >
                    Login
                </button>
                <div>
                    <span>New User? </span>
                    <a href='/register'>Click Here</a>
                </div>
                <div className="text-red-700">
                {   
                    showMessage && (
                    <div>{message}</div>)
                
                }
                </div>
            </div>
        </div>
    );
}

export default Login;
