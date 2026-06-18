# CineMatch AI 🎬

A premium, lightweight content-based movie recommendation engine. The system leverages a custom filtering algorithm built in core **Java** to analyze genres and sub-tags, delivering real-time cinematic recommendations through a high-end, responsive **Dark Neon Aesthetic UI**.

---

## 🚀 Key Features

- **Content-Based Filtering Algorithm:** Implements strict tag-matching and scoring logic using Java Streams to rank recommendations accurately.
- **Dynamic Flat-File Database:** Powered by an external `movies.txt` architecture holding over 150+ meticulously curated Bollywood and Hollywood titles.
- **Premium Glassmorphic UI:** Designed with modern CSS grid configurations, backdrop blurs, and buttery-smooth micro-interactions.
- **Robust Port Architecture:** Upgraded data pipeline utilizing custom server endpoints to handle asynchronous data transfer smoothly.

---

## 🛠️ Tech Stack

- **Backend:** Java Core (`HttpServer`, `java.net`, `Streams API`, `Collections`)
- **Frontend:** HTML5, CSS3 (Modern Flexbox/Grid, Keyframe Animations), JavaScript (Fetch API, Async/Await)
- **Database Architecture:** Flat-file text stream (`movies.txt`)

---

## 📦 Project Structure

```text
├── RecommendationServer.java  # Core Java backend & HTTP server architecture
├── movies.txt                 # Flat-file database containing 150+ categorized movies
├── index.html                 # Main structural layout for the premium dashboard
├── style.css                  # Advanced dark-theme stylesheets and custom animations
├── script.js                  # Asynchronous API fetch and dynamic DOM manipulation logic
└── .gitignore                 # Prevents compiled bytecode (.class) from clogging the repository