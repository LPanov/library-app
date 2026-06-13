import React from 'react'
import CurrentLoanCard from './CurrentLoanCard';

const loan = {
    bookTitle: "The Great Gatsby",
    bookCoverImage: "https://images.unsplash.com/photo-1553060146-71667aa3f223?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    bookAuthor: "F. Scott Fitzgerald",
    dueDate: "2024-07-15",
    status: "ACTIVATE",
    remainingDays: 10,
    overdueDays: 0
}

const CurrentLoans = () => {
  return (
    <div className='p-6'>

      <h3 className='text-2xl font-bold text-gray-900 mb-6'>
        Books You're Currently Reading
      </h3>

      <div className='space-y-4'>
        {/* List of current loans will go here */}

        {[1,1,1,1].map((item, index) => <CurrentLoanCard 
          loan={loan}
          key={index}/>)}
      </div>

    </div>
  )
}

export default CurrentLoans