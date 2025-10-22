# Product Context

## Project Purpose

Dự án **FHeight Growth Client** là một Vue 3 web application được thiết lập với các công nghệ hiện đại nhất để phát triển frontend.

## Problems Being Solved

### Development Efficiency

-   **Fast Styling**: Tailwind CSS cho phép styling nhanh chóng mà không cần viết custom CSS
-   **Type-Safe Routing**: Vue Router cung cấp navigation pattern rõ ràng và dễ maintain
-   **Predictable State**: Pinia đơn giản hóa state management với API trực quan
-   **Fast Development**: Vite cung cấp HMR (Hot Module Replacement) cực nhanh

### Code Organization

-   Component-based architecture giúp code dễ maintain và reuse
-   Centralized state management tránh prop drilling
-   Clear separation of concerns giữa views, components, và stores
-   Consistent styling patterns với Tailwind utilities

## User Experience Goals

### Performance

-   Fast initial load với Vite optimization
-   Instant page transitions với SPA routing
-   Smooth interactions với Vue's reactivity system
-   Minimal bundle size với tree-shaking

### Usability

-   Intuitive navigation với Vue Router
-   Responsive design cho tất cả devices
-   Consistent UI patterns với Tailwind
-   Clear visual feedback cho user actions

### Developer Experience

-   Fast feedback loop với Vite HMR
-   Clear project structure
-   Well-documented codebase
-   Easy to extend và maintain

## How It Should Work

### Application Flow

1. **Initial Load**

    - User truy cập application
    - Vue app được mount tới DOM
    - Router khởi tạo và load Home route
    - Pinia stores được initialized

2. **Navigation**

    - User click vào navigation links
    - Vue Router handles route change
    - New component được rendered trong `<router-view>`
    - URL updates (với history mode)

3. **State Management**
    - User interactions trigger actions
    - Pinia stores update state
    - Components reactively update
    - Changes reflect immediately in UI

### Key Features

#### Counter Example (Home Page)

-   **Purpose**: Demo Pinia state management
-   **Functionality**:
    -   Increment/decrement counter
    -   Display current count
    -   Show computed property (double count)
    -   State persists across navigation

#### Navigation

-   **Purpose**: Demo Vue Router
-   **Functionality**:
    -   Navigate between Home và About
    -   URL changes với route
    -   Styled navigation buttons
    -   Active route indication (automatic)

#### Styling

-   **Purpose**: Demo Tailwind CSS
-   **Functionality**:
    -   Gradient backgrounds
    -   Card components
    -   Button variants
    -   Responsive layouts
    -   Hover effects

## Current Implementation Status

### ✅ Completed

-   Vue 3 application setup
-   Tailwind CSS integration
-   Vue Router configuration
-   Pinia store setup
-   Example pages (Home, About)
-   Counter example với Pinia
-   Responsive styling
-   Navigation system

### 🎯 Ready For

-   Adding more routes và pages
-   Creating reusable components
-   Adding more Pinia stores
-   Implementing real features
-   API integration
-   Form handling
-   Authentication (if needed)
-   Advanced Tailwind customization

## Design Principles

### Simplicity

-   Keep components focused và single-purpose
-   Use Tailwind utilities thay vì custom CSS
-   Composition API với `<script setup>`
-   Minimal boilerplate

### Consistency

-   Consistent file structure
-   Standardized component patterns
-   Uniform styling approach
-   Clear naming conventions

### Maintainability

-   Well-organized code
-   Clear documentation
-   Easy to understand patterns
-   Modular architecture

### Performance

-   Minimal dependencies
-   Efficient rendering
-   Optimized builds
-   Fast development cycles
