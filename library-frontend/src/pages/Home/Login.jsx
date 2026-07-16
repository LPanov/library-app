import React, { useState } from 'react';
import { BookOpen, Mail, Lock, Eye, EyeOff, ArrowRight } from 'lucide-react';
import api from "../../config/api.js";
import { useNavigate } from 'react-router-dom'; 

const Login = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({ email: '', password: '' });
  const [error, setError] = useState(''); 
  const [loading, setLoading] = useState(false); 
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const payload = {
        username: formData.email, 
        password: formData.password
      };

      // 3. Send the POST request to your API Gateway via the configured client
      const response = await api.post('/api/v1/user/login', payload);

      localStorage.setItem('access_token', response.data.access_token);
      localStorage.setItem('refresh_token', response.data.refresh_token);

      navigate('/dashboard');
      
      console.log('Login successful:', response.data);

    } catch (err) {
      console.error('Login error:', err);
      setError(err.response?.data?.message || 'Invalid username or password. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-white to-purple-50 flex flex-col justify-center items-center p-4">
      
      <div className="absolute top-1/4 left-1/4 w-72 h-72 bg-indigo-300/20 rounded-full blur-3xl pointer-events-none"></div>
      <div className="absolute bottom-1/4 right-1/4 w-72 h-72 bg-purple-300/20 rounded-full blur-3xl pointer-events-none"></div>

      <div className="w-full max-w-md relative group z-10">
        
        <div className="absolute -inset-1 bg-gradient-to-r from-purple-400 to-indigo-400 rounded-3xl blur opacity-15 group-hover:opacity-25 transition duration-1000"></div>

        <div className="relative bg-white border border-gray-100 shadow-xl rounded-3xl p-8 sm:p-10 flex flex-col">
          
          <div className="flex flex-col items-center mb-8 text-center">
            <div className="p-3 rounded-2xl bg-indigo-600 text-white shadow-md shadow-indigo-100 mb-4">
              <BookOpen className="w-6 h-6" />
            </div>
            <h2 className="text-3xl font-extrabold text-gray-900 tracking-tight mb-2">
              Welcome <span className="bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent">Back</span>
            </h2>
            <p className="text-sm text-gray-500">
              Sign in to manage your bookshelf and reservations
            </p>
          </div>

          {error && (
            <div className="mb-4 p-3 rounded-xl bg-red-50 border border-red-100 text-sm font-medium text-red-600 text-center">
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-5">
            
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-1.5 ml-0.5">
                Username or Email
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none text-gray-400">
                  <Mail className="w-5 h-5" />
                </div>
                <input
                  type="text" 
                  required
                  placeholder="Enter your username or email"
                  value={formData.email}
                  onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                  className="w-full pl-11 pr-4 py-3 bg-white border border-gray-200 rounded-xl text-gray-900 placeholder-gray-400 focus:outline-none focus:border-indigo-500 focus:ring-2 focus:ring-indigo-100 transition-all duration-200"
                />
              </div>
            </div>

            <div>
              <div className="flex justify-between items-center mb-1.5 ml-0.5">
                <label className="block text-sm font-semibold text-gray-700">
                  Password
                </label>
                <a href="#forgot" className="text-xs font-semibold text-indigo-600 hover:text-indigo-700 hover:underline">
                  Forgot password?
                </a>
              </div>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none text-gray-400">
                  <Lock className="w-5 h-5" />
                </div>
                <input
                  type={showPassword ? 'text' : 'password'}
                  required
                  placeholder="••••••••"
                  value={formData.password}
                  onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                  className="w-full pl-11 pr-11 py-3 bg-white border border-gray-200 rounded-xl text-gray-900 placeholder-gray-400 focus:outline-none focus:border-indigo-500 focus:ring-2 focus:ring-indigo-100 transition-all duration-200"
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute inset-y-0 right-0 pr-3.5 flex items-center text-gray-400 hover:text-gray-600 transition-colors"
                >
                  {showPassword ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                </button>
              </div>
            </div>

            <div className="flex items-center ml-0.5">
              <input
                id="remember-me"
                type="checkbox"
                className="w-4 h-4 text-indigo-600 border-gray-300 rounded focus:ring-indigo-500"
              />
              <label htmlFor="remember-me" className="ml-2 block text-sm text-gray-600 font-medium cursor-pointer select-none">
                Remember me on this device
              </label>
            </div>

            {/* 6. Button disables itself during active network request */}
            <button
              type="submit"
              disabled={loading}
              className="w-full mt-2 inline-flex items-center justify-center px-6 py-3.5 bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-400 text-white font-semibold rounded-xl shadow-md hover:shadow-lg shadow-indigo-100 transition-all duration-200 gap-2 group cursor-pointer disabled:cursor-not-allowed"
            >
              {loading ? 'Signing In...' : 'Sign In'}
              {!loading && <ArrowRight className="w-5 h-5 group-hover:translate-x-1 transition-transform" />}
            </button>
          </form>

          <div className="mt-8 pt-6 border-t border-gray-100 text-center">
            <p className="text-sm text-gray-600">
              Don't have an account?{' '}
              <button className="font-bold text-indigo-600 hover:text-indigo-700 hover:underline transition-all">
                Create an account
              </button>
            </p>
          </div>

        </div>
      </div>
    </div>
  );
};

export default Login;