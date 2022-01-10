package com.developer.aurora.models

data class Event(
    val eventName: String? = null,
    val eventDateAndTime: String? = null,
    val venue: String? = null,
    val entryFees:String?=null,
    val aboutEvent:String?=null,
    val organizedBy:String?=null,
    val organizerEmail:String?=null,
    val organizerPhoneNumber:String?=null,
    val eventBannerUrl:String?=null
) {
}