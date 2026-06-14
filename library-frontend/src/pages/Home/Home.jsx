import React from 'react';
import { ArrowRight, LogIn, UserPlus, BookOpen } from 'lucide-react'; 

const Home = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-white to-purple-50 flex flex-col justify-between">
      
      {/* GLOBAL NAVBAR */}
      <nav className="w-full bg-white/70 backdrop-blur-md border-b border-gray-100 sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            
            {/* Logo/Branding */}
            <div className="flex items-center gap-2.5 cursor-pointer">
              <div className="p-2 rounded-xl bg-indigo-600 text-white shadow-sm shadow-indigo-200">
                <BookOpen className="w-5 h-5" />
              </div>
              <span className="text-xl font-bold tracking-tight bg-gradient-to-r from-gray-900 to-gray-700 bg-clip-text text-transparent">
                Library Hub
              </span>
            </div>

            {/* Navigation Right Actions */}
            <div className="flex items-center gap-3">
              <button className="inline-flex items-center gap-1.5 px-4 py-2 font-semibold text-gray-600 hover:text-indigo-600 transition-colors">
                <LogIn className="w-4 h-4" />
                Sign In
              </button>
              
              <button className="inline-flex items-center gap-1.5 px-4 py-2 font-semibold text-white bg-indigo-600 hover:bg-indigo-700 rounded-xl shadow-sm transition-all shadow-indigo-100">
                <UserPlus className="w-4 h-4" />
                Sign Up
              </button>
            </div>

          </div>
        </div>
      </nav>

      {/* MAIN HERO CONTENT */}
      <main className="max-w-7xl w-full mx-auto px-4 sm:px-6 lg:px-8 py-12 lg:py-20 flex-1 flex flex-col lg:flex-row items-center gap-12">
        
        {/* Left Side: Call to Action */}
        <div className="flex-1 space-y-6 text-center lg:text-left w-full">
          <div className="inline-flex items-center gap-2 px-3 py-1.5 rounded-full bg-indigo-50 border border-indigo-100 text-indigo-600 text-sm font-medium">
            <span className="text-xs">✦</span> Welcome to Library Hub
          </div>
          
          <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold text-gray-900 tracking-tight leading-tight">
            Your Gateway to{" "}
            <span className="bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent">
              Endless Knowledge
            </span>
          </h1>
          
          <p className="text-base sm:text-lg text-gray-600 max-w-xl mx-auto lg:mx-0 leading-relaxed">
            Discover, reserve, and enjoy thousands of books from our extensive collection. 
            Join our community of readers and experience seamless library management.
          </p>
          
          {/* Action Buttons */}
          <div className="flex flex-col sm:flex-row gap-3 justify-center lg:justify-start pt-2">
            <button className="inline-flex items-center justify-center px-6 py-3.5 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold rounded-xl shadow-md hover:shadow-lg transition-all duration-200 gap-2 group">
              Explore Books
              <ArrowRight className="w-5 h-5 group-hover:translate-x-1 transition-transform" />
            </button>
            
            <button className="inline-flex items-center justify-center px-5 py-3.5 bg-white border border-gray-300 hover:border-indigo-600 text-gray-700 hover:text-indigo-600 font-semibold rounded-xl transition-all duration-200 gap-2">
              <LogIn className="w-4 h-4" />
              Sign In
            </button>

            <button className="inline-flex items-center justify-center px-5 py-3.5 bg-indigo-50/50 border border-indigo-200 hover:border-indigo-600 text-indigo-600 hover:bg-indigo-600 hover:text-white font-semibold rounded-xl transition-all duration-200 gap-2">
              <UserPlus className="w-4 h-4" />
              Sign Up
            </button>
          </div>
        </div>

        {/* Right Side: Visual Illustration Card */}
        <div className="flex-1 w-full max-w-xl relative group">
          {/* Decorative ambient glows */}
          <div className="absolute -inset-1 bg-gradient-to-r from-purple-400 to-indigo-400 rounded-3xl blur opacity-20 group-hover:opacity-30 transition duration-1000"></div>
          
          {/* Card Frame Container */}
          <div className="relative bg-white border border-gray-100 shadow-xl rounded-3xl p-8 aspect-[4/3] w-full flex flex-col justify-center items-center">
            
            {/* New Arrivals Tag */}
            <div className="absolute top-6 right-6 inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-amber-50 border border-amber-200 text-amber-800 text-xs font-semibold shadow-sm z-10">
              📚 New Arrivals
            </div>

            {/* 💡 UPDATED: Columns Graphic Container - Expanded height and max width */}
            <div className="w-full h-56 flex items-end justify-center gap-5 sm:gap-6 px-4 max-w-md">
              {/* 💡 Wider columns using w-16 and smooth rounded shapes */}
              <div className="w-16 bg-blue-400 rounded-2xl transition-all duration-300 hover:opacity-90 shadow-md shadow-blue-100 h-[65%] animate-pulse"></div>
              <div className="w-16 bg-indigo-500 rounded-2xl transition-all duration-300 hover:scale-105 shadow-md shadow-indigo-200 h-[90%]"></div>
              <div className="w-16 bg-pink-500 rounded-2xl transition-all duration-300 hover:opacity-90 shadow-md shadow-pink-100 h-[50%]"></div>
              <div className="w-16 bg-sky-400 rounded-2xl transition-all duration-300 hover:opacity-90 shadow-md shadow-sky-100 h-[75%]"></div>
            </div>

          </div>
        </div>
      </main>

      {/* FOOTER QUICK STATS BAR */}
      <footer className="border-t border-gray-100 bg-white/60 backdrop-blur-md py-6">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex flex-wrap justify-center lg:justify-start gap-x-12 gap-y-4 text-sm font-medium">
            
            <div className="flex items-center gap-2 text-gray-600">
              <span className="w-2 h-2 rounded-full bg-emerald-500"></span>
              <strong className="text-gray-900 font-bold">10,000+</strong> Books
            </div>

            <div className="flex items-center gap-2 text-gray-600">
              <span className="w-2 h-2 rounded-full bg-emerald-500"></span>
              <strong className="text-gray-900 font-bold">5,000+</strong> Members
            </div>

            <div className="flex items-center gap-2 text-gray-600">
              <span className="w-2 h-2 rounded-full bg-emerald-500"></span>
              <strong className="text-gray-900 font-bold">24/7</strong> Access
            </div>

          </div>
        </div>
      </footer>

    </div>
  );
};

export default Home;