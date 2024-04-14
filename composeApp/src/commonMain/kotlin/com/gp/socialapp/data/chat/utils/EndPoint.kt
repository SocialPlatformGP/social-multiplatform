package com.gp.socialapp.data.chat.utils

sealed class EndPoint(val url: String) {
    data object CheckIfRoomExists : EndPoint("/isRoomExist")
    data object GetAllRecentRooms : EndPoint("/getRecentRooms")
    data object CreateGroupRoom : EndPoint("/createGroupRoom")
}