import React from 'react'
import StatesCard from './StateCard'
import{LibraryBooks, Book} from '@mui/icons-material'
import { statsConfig } from './StateConfig'

const Dashboard = () => {

    const stateData = statsConfig({
        myLoans: [1, 2, 3, 4, 5], 
        reservations: [], 
        stats: { 
            booksRead: 5, 
            readingStreak: 7 
        }
    });

    return (
        <div className='min-h-screen bg-gradient-to-br from-indigo-50 via-white to-bg-purple-500 py-8'>
            <div className='max-w-7xl px-4 sm:px-6 lg:px-8'>

                <div className='mb-8 animate-fade-in-up'>
                    <h1 className='text-4xl font-bold text-indigo-500 mb-2'>My {" "}
                        <span className='bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent'>Dashboard</span>
                    </h1>
                    <p className='text-lg text-gray-600'>Track your reading journey and manage your library with ease.</p>
                </div>

                {/* state card */}
                <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 animate-fade-in-up mb-8'>
                    {stateData.map((item) => (
                        <StatesCard 
                        bgColor={item.bgColor}
                        textColor={item.textColor}
                        icon={item.icon}
                        value={item.value}
                        title={item.title}
                        subtitle={item.subtitle}
                        key={item.id} />
                    ))}
                </div>

                {/* Reading Progress */}

            </div>
        </div>
    )
}

export default Dashboard