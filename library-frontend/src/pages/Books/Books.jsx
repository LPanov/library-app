import React from 'react';
import GenreFilter from './GenreFilter';
import { InputAdornment, TextField, FormControl, InputLabel, Select, MenuItem } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import SortIcon from '@mui/icons-material/Sort';
import BookCard from './BookCard';

const genres = [
  {
    "active": true,
    "bookCount": 120,
    "code": "FICTION",
    "createdAt": "2025-10-10T10:40:08.725525",
    "description": "Genre that includes imaginative or invented stories, often exploring characters, plots, and themes.",
    "displayOrder": 1,
    "id": 1,
    "name": "Fiction",
    "parentGenreId": null,
    "parentGenreName": null,
    "subGenres": null,
    "updatedAt": "2025-10-10T10:40:08.725525"
  },
  {
    "active": true,
    "bookCount": 85,
    "code": "MYSTERY",
    "createdAt": "2025-10-10T11:15:22.104321",
    "description": "Fiction involving a puzzling crime, situation, or circumstances that need to be solved.",
    "displayOrder": 2,
    "id": 2,
    "name": "Mystery",
    "parentGenreId": null,
    "parentGenreName": null,
    "subGenres": null,
    "updatedAt": "2025-10-10T11:15:22.104321"
  },
  {
    "active": true,
    "bookCount": 64,
    "code": "SCI_FI",
    "createdAt": "2025-10-12T09:30:15.442118",
    "description": "Stories exploring futuristic science, technology, space travel, and time travel.",
    "displayOrder": 3,
    "id": 3,
    "name": "Science Fiction",
    "parentGenreId": null,
    "parentGenreName": null,
    "subGenres": null,
    "updatedAt": "2025-10-12T09:30:15.442118"
  },
  {
    "active": true,
    "bookCount": 95,
    "code": "BIOGRAPHY",
    "createdAt": "2025-10-14T14:22:40.891002",
    "description": "Non-fiction detailed descriptions of a person's life written by someone else.",
    "displayOrder": 4,
    "id": 4,
    "name": "Biography",
    "parentGenreId": null,
    "parentGenreName": null,
    "subGenres": null,
    "updatedAt": "2025-10-14T14:22:40.891002"
  },
  {
    "active": true,
    "bookCount": 110,
    "code": "FANTASY",
    "createdAt": "2025-10-15T16:05:11.233541",
    "description": "Speculative fiction set in a fictional universe, often inspired by real-world myth and folklore.",
    "displayOrder": 5,
    "id": 5,
    "name": "Fantasy",
    "parentGenreId": null,
    "parentGenreName": null,
    "subGenres": null,
    "updatedAt": "2025-10-15T16:05:11.233541"
  }
];

const books = [
  {
    "active": true,
    "alreadyHaveLoan": null,
    "alreadyHaveReservation": null,
    "author": "John Doe",
    "availableCopies": 0,
    "coverImageUrl": "http://res.cloudinary.com/dxoqwusir/image/upload/v1761368804/pexels-bohlemec",
    "createdAt": "2025-10-25T10:37:00.576206",
    "description": "An advanced developer's handbook on building scalable, production-grade microservices.",
    "genreCode": "PROGRAMMING",
    "genreId": 10,
    "genreName": "PROGRAMMING",
    "id": 9,
    "isbn": "978-1-4028-9462-6",
    "language": "English",
    "pages": 320,
    "price": 499,
    "publicationDate": "2024-06-25",
    "publisher": "Zosh Publications",
    "title": "Mastering Spring Boot and Microservices",
    "totalCopies": 2,
    "updatedAt": "2025-12-25T18:58:46.903441"
  },
  {
    "active": true,
    "alreadyHaveLoan": null,
    "alreadyHaveReservation": null,
    "author": "Sarah Jenkins",
    "availableCopies": 4,
    "coverImageUrl": "http://res.cloudinary.com/dxoqwusir/image/upload/v1761368805/pexels-bookcover2",
    "createdAt": "2025-10-26T11:20:15.122405",
    "description": "A thrilling noir mystery set in neon-lit streets following a detective chasing historical shadows.",
    "genreCode": "MYSTERY",
    "genreId": 2,
    "genreName": "Mystery",
    "id": 12,
    "isbn": "978-3-16-148410-0",
    "language": "English",
    "pages": 412,
    "price": 299,
    "publicationDate": "2025-02-14",
    "publisher": "Zosh Publications",
    "title": "Echoes in the Dark",
    "totalCopies": 5,
    "updatedAt": "2025-11-01T09:15:30.412000"
  },
  {
    "active": true,
    "alreadyHaveLoan": null,
    "alreadyHaveReservation": null,
    "author": "David Vance",
    "availableCopies": 1,
    "coverImageUrl": "http://res.cloudinary.com/dxoqwusir/image/upload/v1761368806/pexels-bookcover3",
    "createdAt": "2025-10-28T14:45:10.981321",
    "description": "An epic space opera detailing humanity's final stand across colonial systems on the brink of collapse.",
    "genreCode": "SCI_FI",
    "genreId": 3,
    "genreName": "Science Fiction",
    "id": 15,
    "isbn": "978-0-7432-7356-5",
    "language": "English",
    "pages": 528,
    "price": 550,
    "publicationDate": "2024-11-30",
    "publisher": "Zosh Publications",
    "title": "Chronicles of the Core",
    "totalCopies": 3,
    "updatedAt": "2025-12-15T14:22:11.890123"
  },
  {
    "active": true,
    "alreadyHaveLoan": null,
    "alreadyHaveReservation": null,
    "author": "Elena Rostova",
    "availableCopies": 0,
    "coverImageUrl": "http://res.cloudinary.com/dxoqwusir/image/upload/v1761368807/pexels-bookcover4",
    "createdAt": "2025-10-30T08:12:44.231900",
    "description": "An enchanting fantasy setting involving hidden architectural societies and forbidden high magic.",
    "genreCode": "FANTASY",
    "genreId": 5,
    "genreName": "Fantasy",
    "id": 18,
    "isbn": "978-0-452-28423-4",
    "language": "English",
    "pages": 380,
    "price": 350,
    "publicationDate": "2025-05-19",
    "publisher": "Zosh Publications",
    "title": "The Weaver's Gambit",
    "totalCopies": 1,
    "updatedAt": "2025-12-20T11:05:55.334111"
  },
  {
    "active": true,
    "alreadyHaveLoan": null,
    "alreadyHaveReservation": null,
    "author": "Marcus Aurelius",
    "availableCopies": 7,
    "coverImageUrl": "http://res.cloudinary.com/dxoqwusir/image/upload/v1761368808/pexels-bookcover5",
    "createdAt": "2025-11-02T16:50:22.887221",
    "description": "A comprehensive analysis of classical philosophy translated and updated for modern engineering and daily workflows.",
    "genreCode": "FICTION",
    "genreId": 1,
    "genreName": "Fiction",
    "id": 22,
    "isbn": "978-0-14-044949-5",
    "language": "English",
    "pages": 240,
    "price": 199,
    "publicationDate": "2023-08-10",
    "publisher": "Zosh Publications",
    "title": "Stoic Practices for Modern Minds",
    "totalCopies": 10,
    "updatedAt": "2025-12-28T20:30:12.774550"
  }
]

const Books = () => {
  const [selectedGenreId, setSelectedGenreId] = React.useState(null);
  const [availabilityFilter, setAvailabilityFilter] = React.useState('ALL');
  const [searchTerm, setSearchTerm] = React.useState("");
  const [sortBy, setSortBy] = React.useState('createdAt');
  const [sortDirection, setSortDirection] = React.useState('DESC');

  const handleGenreChange = (e) => {
    if (e === null) {
      setSelectedGenreId(null);
      return;
    }

    const value = e.target.value;

    setSelectedGenreId(value ? Number(value) : null);
  };

  const handleSortChange = (value) => {
    const [field, direction] = value.split("-");

    setSortBy(field);
    setSortDirection(direction.toUpperCase());
  }

  //   Correct: Uses curly braces {}
  const getCurrentSortValue = () => {
    return `${sortBy}-${sortDirection.toLowerCase()}`;
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-white to-purple-50">
      {/* Header */}
      <div className="bg-white border-b border-gray-200 text-center">
        <div className="px-4 sm:px-6 lg:px-8 py-8">
          <div className="text-4xl text-gray-900 mb-2">
            <h1 className="font-bold">
              Browse Our{" "}
              <span className="bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent">
                Collection
              </span>
            </h1>
          </div>
          <p className="text-lg text-gray-600">
            Discover thousands of books across all genres
          </p>
        </div>
      </div>

      {/* Main Content Layout Wrapper */}
      <div className="px-4 sm:px-6 lg:px-8 py-8">
        <div className="flex flex-col lg:flex-row gap-8">

          {/* LEFT SIDEBAR: Filters */}
          <aside className="lg:w-72 space-y-6">
            {/* Genre Filter Container */}
            <div className="space-y-6">
              <GenreFilter
                onGenreSelect={handleGenreChange}
                genres={genres}
                selectedGenreId={selectedGenreId}
              />
            </div>

            {/* Availability Filter Container */}
            <div className="bg-white rounded-xl shadow-md p-4 border border-gray-100">
              <h3 className="text-lg font-bold text-gray-900 mb-4 pb-3 border-b border-gray-200">
                Availability
              </h3>
              <FormControl fullWidth>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={availabilityFilter}
                  onChange={(e) => setAvailabilityFilter(e.target.value)}
                >
                  <MenuItem value={"ALL"}>All Books</MenuItem>
                  <MenuItem value={"AVAILABLE"}>Available Only</MenuItem>
                  <MenuItem value={"CHECKED_OUT"}>Checked Out</MenuItem>
                </Select>
              </FormControl>
            </div>
          </aside>

          {/* RIGHT CONTENT AREA: Search bar & Grid items */}
          <main className="flex-1 space-y-6">
            {/* search and sort */}
            <div className="flex flex-col md:flex-row gap-4">
              {/* Search Input */}
              <div className="flex-1">
                <TextField
                  fullWidth
                  placeholder="Search by title, author, or category..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  slotProps={{
                    input: {
                      endAdornment: (
                        <InputAdornment position="end">
                          <SearchIcon className="text-gray-400" />
                        </InputAdornment>
                      ),
                    },
                  }}
                  sx={{
                    '& .MuiOutlinedInput-root': {
                      '&:hover fieldset': {
                        borderColor: '#4F46E5',
                      },
                      '&.Mui-focused fieldset': {
                        borderColor: '#4F46E5',
                      },
                    },
                  }}
                />
              </div>

              {/* Sort Dropdown */}
              <div className="md:w-64">
                <FormControl fullWidth>
                  <InputLabel>Sort By</InputLabel>
                  <Select
                    value={getCurrentSortValue()}
                    onChange={(e) => handleSortChange(e.target.value)}
                    label="Sort By"
                    startAdornment={
                      <InputAdornment position="start">
                        <SortIcon className="text-gray-400" />
                      </InputAdornment>
                    }
                    sx={{
                      '& .MuiOutlinedInput-notchedOutline': {
                        borderColor: '#E5E7EB',
                      },
                      '&:hover .MuiOutlinedInput-notchedOutline': {
                        borderColor: '#4F46E5',
                      },
                      '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
                        borderColor: '#4F46E5',
                      },
                    }}
                  >
                    <MenuItem value="title-asc">Title (A-Z)</MenuItem>
                    <MenuItem value="title-desc">Title (Z-A)</MenuItem>
                    <MenuItem value="author-asc">Author (A-Z)</MenuItem>
                    <MenuItem value="author-desc">Author (Z-A)</MenuItem>
                    <MenuItem value="createdAt-desc">Newest First</MenuItem>
                    <MenuItem value="createdAt-asc">Oldest First</MenuItem>
                  </Select>
                </FormControl>
              </div>


            </div>

            {/* Books Grid */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {books.map((book)=> (<BookCard key={book.id} book={book}/>))}
            </div>
          </main>

        </div>
      </div>

    </div>
  );
};

export default Books;
