Compose UI and network communication
.
.
Retrofit is a type-safe HTTP client for Android and Java, it simplifies the process of making network requests 
by abstracting the boilerplate code required for HTTP communication. 
Retrofit allows you to define REST API endpoints as Java or Kotlin interfaces, making it easy to integrate with backend services.
.
.
Create a news reading application

- https://newsapi.org/
- API example
      https://newsapi.org/v2/top-headlines?country=us&apiKey=...
- Use the following technologies:
    - MVI architecture
    - Hilt for DI
    - ViewModel
    - Retrofit for network communication
    - Try to show the raw string result
    - Parse the JSON response and show the formatted news in Cards/LazyColumn
