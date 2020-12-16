package me.mking.doggodex.presentation.viewstate

sealed class BrowseViewState {
    object Loading : BrowseViewState()
    object Ready : BrowseViewState()
}