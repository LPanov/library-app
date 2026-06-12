import React from 'react';
import { AccessTime, LibraryBooks } from '@mui/icons-material';

const CurrentLoanCard = ({ loan }) => {
  return (
    <div
      className="flex items-center justify-between p-6
      border border-gray-200 rounded-2xl "
      >
      <div className="flex items-center space-x-4 flex-1">
        <img
          src={loan.bookCoverImage}
          alt={loan.bookTitle}
          className="w-16 h-24 rounded-lg"
        />
      </div>
      <div className="flex-1">
        <h4 className="text-lg font-bold text-gray-900 mb-1">{loan.bookTitle}</h4>
        <p className="text-gray-600 mmb-2">by {loan.bookAuthor}</p>

        <div className="flex items-center space-x-4 text-sm">

            <AccessTime sx={{fontSize: 16}}/>
            <span>Due: {new Date(loan.dueDate).toLocaleDateString()}</span>

        </div>

      </div>
    </div>
  );
};

export default CurrentLoanCard;
