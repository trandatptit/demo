# Technical Context

## Technologies & Versions

### Core Dependencies

-   **Vue**: ^3.5.22
-   **Vue Router**: ^4.6.3
-   **Pinia**: ^3.0.3

### Dev Dependencies

-   **Vite**: ^7.1.7
-   **@vitejs/plugin-vue**: ^6.0.1
-   **Tailwind CSS**: ^4.1.15
-   **PostCSS**: Latest
-   **Autoprefixer**: ^10.4.21

## Configuration Files

### tailwind.config.js

-   Content paths: `./index.html`, `./src/**/*.{vue,js,ts,jsx,tsx}`
-   Configured for Vue components
-   Using default theme with ability to extend

### postcss.config.js

-   Tailwind CSS plugin enabled
-   Autoprefixer plugin enabled

### vite.config.js

-   Vue plugin configured
-   Standard Vite setup for Vue 3

## Development Setup

### Installation

```bash
npm install
```

### Running Development Server

```bash
npm run dev
# or
npm start
```

### Building for Production

```bash
npm run build
```

### Preview Production Build

```bash
npm run preview
```

## Styling Approach

### Tailwind CSS

-   Utility-first CSS framework
-   All directives imported in `src/style.css`
-   Using Tailwind classes throughout components
-   No custom CSS needed for basic styling

### Color Palette (Examples Used)

-   Primary: Indigo/Blue shades
-   Secondary: Purple/Pink shades
-   Success: Green shades
-   Danger: Red shades
-   Warning: Yellow shades

## Routing Configuration

### Routes

-   `/` - Home page with counter example
-   `/about` - About page with tech stack info

### Navigation

-   Using `<router-link>` for navigation
-   `<router-view>` in App.vue as outlet

## State Management

### Pinia Stores

-   Using Composition API style (setup syntax)
-   Store location: `src/stores/`
-   Counter store example implemented with:
    -   State: `count`
    -   Getters: `doubleCount`
    -   Actions: `increment`, `decrement`, `reset`

## Code Style

### Formatting

-   4 spaces indentation
-   Single quotes converted to double quotes
-   Semicolons added
-   Trailing commas in objects/arrays

### Vue Components

-   Using `<script setup>` syntax
-   Composition API preferred
-   Template-first approach
