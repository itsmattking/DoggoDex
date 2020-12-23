# DoggoDex

### Application requirements:

* As a **user running the application** I can **select breed from the list** So that **I can view pictures of that breed**

#### Scenario: Viewing the breed list
* **When** I launch the app 
* **Then** I see a list of dog breeds

#### Scenario: Viewing pictures of breed - UPDATED
* **Given** I have launched the app
* **When** I select a breed from the list
* **Then** I see 10 images of the breed
* **UPDATE:** the implementation shows one image at a time in a swipe-able carousel

## Running the app

Note that the latest stable Android Studio is required to build and run the app as Hilt relies on AS version 4.0 and higher.

## Architecture

The app is written using **MVVM** and **Clean architecture**. It contains the main **app** module as well as a **common** module that can be used when expanding to other feature modules.

* **Domain** Contains all business logic, models, use cases, and interfaces for repositories
* **Data** Contains repository implementations, services to retrieve data, and mappers to convert data models to domain models
* **Presentation** Contains view models, Android views, Fragment and Activity classes.

## Libraries used

### General
* **Kotlin Coroutines** for running tasks on background threads

### Network Layer

* **OKHttp** for network communication
* **Retrofit** for modeling HTTP requests
* **Moshi** for JSON serialisation

### Dependency Injection

Using **Hilt**, built on top of **Dagger** for dependency injection.

### Presentation/UI

* **AndroidViewModel** for ViewModel layer
* **Android LiveData** for observable data changes
* **Glide** for image loading
* **Navigation Component** for navigating between fragments
* **Android ViewBinding** for safe and automatic access to views

### Testing
* **JUnit4** for general unit tests
* **mockk** mocking library
* **AndroidX testing libraries** for live data and coroutines testing
* **Robolectric** for Android-specific unit testing (custom views)
* **Truth** for assertions
