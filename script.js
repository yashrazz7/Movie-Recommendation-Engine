const API_BASE = "http://localhost:8081";

async function loadDropdownMovies() {
    try {
        const response = await fetch(`${API_BASE}/getMovies`);
        const data = await response.text();
        const select = document.getElementById("movieSelect");
        
        if (data.trim() !== "") {
            data.split(",").forEach(movie => {
                const option = document.createElement("option");
                option.value = movie;
                option.textContent = movie;
                select.appendChild(option);
            });
        }
    } catch (error) {
        console.error("Error connecting to backend:", error);
    }
}

async function getAIRecommendations() {
    const movieSelect = document.getElementById("movieSelect");
    const selectedMovie = movieSelect.value;
    
    if (!selectedMovie) return;

    const grid = document.getElementById("recommendationsGrid");
    grid.innerHTML = '<div class="placeholder-text">Analyzing database tags...</div>';

    try {
        const response = await fetch(`${API_BASE}/recommend`, {
            method: "POST",
            body: selectedMovie
        });
        
        const data = await response.text();
        grid.innerHTML = "";

        if (data.trim() !== "") {
            data.split(",").forEach(movie => {
                const card = document.createElement("div");
                card.className = "movie-card";
                card.innerHTML = `
                    <div class="movie-icon">🎬</div>
                    <div class="movie-title">${movie}</div>
                `;
                grid.appendChild(card);
            });
        } else {
            grid.innerHTML = '<div class="placeholder-text">No similar movies found.</div>';
        }
    } catch (error) {
        grid.innerHTML = '<div class="placeholder-text">Server error. Make sure Java backend is running!</div>';
    }
}

loadDropdownMovies();