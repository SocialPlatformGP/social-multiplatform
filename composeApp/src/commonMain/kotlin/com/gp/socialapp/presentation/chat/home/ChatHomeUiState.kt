package com.gp.socialapp.presentation.chat.home

import com.gp.socialapp.data.chat.model.RecentRoomResponse

data class ChatHomeUiState(
    val recentRooms: List<RecentRoomResponse> = rooms,
)


val rooms = listOf(
    RecentRoomResponse(
        "1",
        "Group 1",
        true,
        "1",
        "Mohamed",
        "https://www.google.com",
        "This is a bio for John Doe",
        "Hello",
        "12:00",
        1
    ),
    RecentRoomResponse(
        "2",
        "Group 2",
        true,
        "1",
        "Mohamed",
        "https://www.google.com",
        "This is a bio for John Doe",
        "Hello",
        "12:00",
        1
    ),
    RecentRoomResponse(
        "3",
        "Group 3",
        true,
        "1",
        "Mohamed",
        "https://www.google.com",
        "This is a bio for John Doe",
        "Hello",
        "12:00",
        1
    ),
    RecentRoomResponse(
        "4",
        "Group 4",
        true,
        "1",
        "Mohamed",
        "https://www.google.com",
        "This is a bio for John Doe",
        "Hello",
        "12:00",
        1
    ),
    RecentRoomResponse(
        "5",
        "Group 5",
        true,
        "1",
        "Mohamed",
        "https://www.google.com",
        "This is a bio for John Doe",
        "Hello",
        "12:00",
        1
    ),
    RecentRoomResponse(
        "6",
        "Group 6",
        true,
        "1",
        "Mohamed",
        "https://www.google.com",
        "This is a bio for John Doe",
        "Hello",
        "12:00",
        1
    ),
    RecentRoomResponse(
        "7",
        "Group 7",
        true,
        "1",
        "Mohamed",
        "https://www.google.com",
        "This is a bio for John Doe",
        "Hello",
        "12:00",
        1
    ),
    RecentRoomResponse(
        "8",
        "Group 8",
        true,
        "1",
        "Mohamed",
        "https://www.google.com",
        "This is a bio for John Doe",
        "Hello",
        "12:00",
        1
    ),
    RecentRoomResponse(
        "9",
        "Group 9",
        true,
        "1",
        "Mohamed",
        "https://www.google.com",
        "This is a bio for John Doe",
        "Hello",
        "12:00",
        1
    ),
    RecentRoomResponse(
        "10",
        "Group 10",
        true,
        "1",
        "Mohamed",
        "https://www.google.com",
        "This is a bio for John Doe",
        "Hello",
        "12:00",
        1
    )

)