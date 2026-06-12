import React from 'react'
import CurrentLoanCard from './CurrentLoanCard';

const CurrentLoans = () => {
  return (
    <div className='p-6'>

      <h3 className='text-2xl font-bold text-gray-900 mb-6'>
        Books You're Currently Reading
      </h3>

      <div className='space-y-4'>
        {/* List of current loans will go here */}

        {[1,1,1,1].map((item, index) => <CurrentLoanCard key={index}/>)}
      </div>

    </div>
  )
}

export default CurrentLoans