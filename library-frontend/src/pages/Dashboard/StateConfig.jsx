import React from "react";
import EventAvailableIcon from "@mui/icons-material/EventAvailable";
import HistoryIcon from "@mui/icons-material/History";
import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import { LibraryBooks } from "@mui/icons-material";

export const statsConfig = ({ myLoans, reservations, stats }) => [
    {
        id: "loans",
        title: "Current Loans",
        subtitle: "Books you're reading",
        value: myLoans?.length || 0, 
        icon: <LibraryBooks sx={{ fontSize: 32, color: "#4F46E5" }} />,
        bgColor: "bg-indigo-100",
        textColor: "text-indigo-600",
    },
    {
        id: "reservations",
        title: "Reservations",
        subtitle: "Books on hold",
        value: reservations?.length || 0,
        icon: <EventAvailableIcon sx={{ fontSize: 32, color: "#9333EA" }} />,
        bgColor: "bg-purple-100",
        textColor: "text-purple-600",
    },
    {
        id: "history",
        title: "Books Read",
        subtitle: "This year",
        // FIX: Point this to the correct stats data property
        value: stats?.booksRead || 0, 
        icon: <HistoryIcon sx={{ fontSize: 32, color: "#10B981" }} />, // Green icon color
        bgColor: "bg-emerald-100",
        textColor: "text-emerald-600",
    },
    {
        id: "streak",
        title: "Day Streak",
        subtitle: "Keep it going!",
        // FIX: Point this to your readingStreak property
        value: stats?.readingStreak || 0, 
        icon: <TrendingUpIcon sx={{ fontSize: 32, color: "#F97316" }} />, // Orange icon color
        bgColor: "bg-orange-100",
        textColor: "text-orange-500",
    }
];