# System Patterns

## Architecture Overview

### Application Structure

-   **Single Page Application (SPA)** với Vue Router
-   **Component-based architecture**
-   **Centralized state management** với Pinia
-   **Utility-first styling** với Tailwind CSS

## Component Architecture

### Component Hierarchy

```
App.vue (Root)
└── <router-view>
    ├── Home.vue
    │   └── Counter component (inline)
    └── About.vue
```

### Component Patterns

#### Composition API Setup

-   Sử dụng `<script setup>` cho tất cả components
-   Import utilities từ Vue và libraries
-   Define reactive state với `ref` và `computed`
-   Export implicitly (không cần explicit return)

#### Template Structure

-   Semantic HTML với Tailwind classes
-   Responsive design patterns
-   Consistent spacing và layout
-   Accessibility considerations

## Routing Patterns

### Router Configuration

-   Hash-based routing với `createWebHistory()`
-   Named routes cho dễ reference
-   Component import trực tiếp (không lazy loading hiện tại)

### Navigation

-   `<router-link>` cho internal navigation
-   Styled như buttons với Tailwind classes
-   Active route handling tự động

## State Management Patterns

### Pinia Store Structure

```javascript
// Composition API style
defineStore('storeName', () => {
    // State (ref)
    const state = ref(initialValue);

    // Getters (computed)
    const getter = computed(() => transformation);

    // Actions (functions)
    function action() {
        // mutation logic
    }

    return { state, getter, action };
});
```

### Store Usage in Components

```javascript
import { useStoreName } from '@/stores/storeName';

const store = useStoreName();
// Access: store.state, store.getter
// Call: store.action()
```

## Styling Patterns

### Tailwind Utility Classes

-   **Layout**: `flex`, `grid`, `container`, `mx-auto`
-   **Spacing**: `p-*`, `m-*`, `space-*`
-   **Colors**: `bg-*`, `text-*`, với shade numbers
-   **Effects**: `hover:*`, `transition-*`, `shadow-*`
-   **Responsive**: `sm:*`, `md:*`, `lg:*`, `xl:*`

### Common Patterns

-   **Cards**: `bg-white rounded-lg shadow-xl p-8`
-   **Buttons**: `px-* py-* bg-color text-white rounded-lg hover:bg-darker transition-colors`
-   **Gradients**: `bg-gradient-to-br from-* to-*`
-   **Containers**: `container mx-auto px-4 py-16`

## File Organization

### Directory Structure

-   `/src/views/` - Page-level components (routes)
-   `/src/components/` - Reusable components
-   `/src/stores/` - Pinia stores
-   `/src/router/` - Router configuration
-   `/src/assets/` - Static assets
-   `/memory-bank/` - Project documentation

### Naming Conventions

-   Components: PascalCase (e.g., `Home.vue`, `UserProfile.vue`)
-   Stores: camelCase with 'use' prefix (e.g., `useCounterStore`)
-   Files: PascalCase for components, camelCase for JS files
-   Folders: lowercase with hyphens if needed

## Development Patterns

### Import Patterns

```javascript
// Vue core
import { ref, computed, onMounted } from 'vue';

// Router
import { useRouter, useRoute } from 'vue-router';

// Pinia
import { useStoreName } from '@/stores/storeName';

// Components
import ComponentName from '@/components/ComponentName.vue';
```

### Reactive Data

-   Use `ref()` for primitive values
-   Use `computed()` for derived state
-   Use `reactive()` for objects (if needed)

### Event Handling

-   Inline handlers in template với `@click`
-   Method definitions in setup
-   Event modifiers: `@click.prevent`, `@submit.prevent`

## Best Practices

### Performance

-   Lazy load routes khi cần (hiện tại chưa implement)
-   Keep components small và focused
-   Minimize watchers, prefer computed properties

### Code Quality

-   Consistent formatting (được enforce bởi editor)
-   Clear component responsibilities
-   Descriptive variable và function names
-   Comments cho complex logic

### Scalability

-   Modular store structure
-   Reusable component patterns
-   Clear separation of concerns
-   Easy to add new routes và stores
