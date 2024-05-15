## 🎨 Overview

This is the implementation of the test for Umain that I developed and can be
found [here](https://github.com/apegroup/Code-test?tab=readme-ov-file).
The design that I was to implement
is [at this Figma file](https://www.figma.com/file/yw7DttG4w7F28tmTaxXrLh/Code-test?type=design&node-id=0-1&mode=design&t=K7zNb9PQzWqeJQMj-0)
and the endpoints that I was told to use are the
ones [found here](https://food-delivery.umain.io/swagger/).
<br>
<br>
<br>

## 🖼 Screenshots
|                                                                                                         |                                                                                                         |
|---------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| ![Screenshot #1](https://raw.github.com/OneManStudioDotSe/test-for-umain/main/screenshots/screen_1.png) | ![Screenshot #2](https://raw.github.com/OneManStudioDotSe/test-for-umain/main/screenshots/screen_2.png) |
| ![Screenshot #3](https://raw.github.com/OneManStudioDotSe/test-for-umain/main/screenshots/screen_3.png) | ![Screenshot #4](https://raw.github.com/OneManStudioDotSe/test-for-umain/main/screenshots/screen_5.png) |

## 📝 Features
- 100% Compose UI and Kotlin
- Custom theme with colors and typography
- Bottom sheet
- Content repository for REST-based network calls with Retrofit and Sandwich
- MVVM with separated Viewmodels
- DI with Hilt
- Logging with Timber
- Separated UI components
- 
## 🛠 Tech Stack & Open Source Libraries

- Minimum SDK level 24.
- [Material 3 design](https://m3.material.io/) used across the whole app
- 100% [Jetpack Compose](https://developer.android.com/jetpack/compose) based
  on [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous fetching of content
- Jetpack
    - Compose: Android’s modern toolkit for building native UI
    - ViewModel: UI related data holder and lifecycle aware
    - App Startup: Provides a straightforward, performant way to initialize components at
      application startup
    - Navigation: For navigating screens
      and [Hilt Navigation Compose](https://developer.android.com/jetpack/compose/libraries#hilt)
      for injecting dependencies
    - [Hilt](https://dagger.dev/hilt/): Dependency Injection
- [Coil](https://github.com/coil-kt/coil): Jetpack Compose image loading library that fetches and
  displays network images with Coil
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and handle
  network data
- [Sandwich](https://github.com/skydoves/Sandwich): Construct a lightweight and modern response
  interface to handle network payload for Android
  <br>
  <br>
  <br>

## 🏛️ Architecture

This small app follows
the [Google's official architecture guidance](https://developer.android.com/topic/architecture) and
was built with [Guide to app architecture](https://developer.android.com/topic/architecture), in
order to demonstrate an established modern and flexible way to to build a real-world app.<br>

The overall architecture is composed of two layers where each layer has dedicated components and
they each have different responsibilities:

1. UI Layer
2. Data layer

#### UI Layer

The UI Layer consists of UI elements like lists, cards, buttons and a top bar that could interact
with the user and also
a [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that holds the
app states.

#### Data Layer

The data Layer consists of the repository which include the business logic, such as querying data
from the local API endpoints which fetches remote data from the network.<br>
<br>
<br>
<br>

## 🖌️ Small details for a better project

Even though it was not needed or so useful for the scope of this test app, I have done the following
in order to demonstrate a "healthy" project:

- I have added an app icon for the sake of making the test a bit more different than the
  competition :)
- All strings are localized at strings.xml
- All used assets are following common naming patterns
- I have added as a tool a script that helps to easily find the latest stable version of all
  dependencies
- I have added 2 build types to demonstrate an easy way to have a debug and release variant where
  there can be build-specific fields such as the API endpoint (staging and live) and a field that
  depends on the build type
- I have added support for Timber through an Application class which is used only at debug builds in
  order to make sure that now logs leak into a release build
- Unnecessary files and folders that were created by default for the project are removed
- The most necessary aspects of the app's theme are defined in order to be able to implement a dark
  mode easily
- Even though unnecessary, the navigation destinations within the app are at their own class, thus
  making it easy to add more screens/destinations either at a potential bottom bar or separate
  screens
- As a good practise, I have separated the DTOs that are used for the communication from the API to
  the repository and I use separate models (that exist at the 'models' package) which are
  specifically used between the viewmodel and the UI
  <br>
  <br>
  <br>

## 📋 Notes on the UI

- At Figma, the components page shows the filter having an elevation of 10dp which is not used at
  the main screen, so I have removed it
- There was an extra space between the filters list and the app top bar called 'gradient' which
  didn't have an actual gradient color, therefore I considered it a space between the top bar and
  the filters list.
- There was no specification on the transition from the list to the details so based on the icon
  used, the down chevron, I took the creative liberty to assume that the details of a restaurant
  would be shown on a bottom sheet, which is what I also implemented.
- The colors of the background of the filter tags were using transparency (#FFFFFF )thus making the
  extraction
  of the color not straightforward and fully compatible with a standardised Material theme so I had
  to use specific color values
- It was not clear if you wanted the bottom sheet to be behind or below the status bar as there was
  not text (time, level of battery, signal), so I added support for both (HomeScreen.kt:L183-184)
  <br>
  <br>
  <br>

## 🪴 Points of potential improvement

There are a few things here and there that could be potentially be improved but I skipped them for
the sake of taking a reasonable time to complete the test:

- Support for dark mode and dynamic colors
- Add a splash screen where the content for the app is fetched so it is available instantly at the
  Home screen
- Optimise the bottom sheet UI so
- The down chevron could be animated while the bottom sheet opens and closes
- The placeholder image for the restaurant image could be resized to show properly
- Tests could be added for the view model