package com.gp.socialapp.data.chat.utils

import com.gp.socialapp.util.AppConstants.BASE_URL

sealed class EndPoint(val url: String) {
    data object CheckIfRoomExists : EndPoint("$BASE_URL/isRoomExist")
    data object GetAllRecentRooms : EndPoint("$BASE_URL/getRecentRooms")
}