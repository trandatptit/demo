import { createApp } from 'vue';
import { createPinia } from 'pinia';
import router from './router';
import App from './App.vue';
import PrimeVue from 'primevue/config';
import ToastService from 'primevue/toastservice';
import DialogService from 'primevue/dialogservice';
import ConfirmationService from 'primevue/confirmationservice';

// Import PrimeVue styles
import 'primeicons/primeicons.css';

// Import common components
import Button from 'primevue/button';
import InputText from 'primevue/inputtext';
import Toast from 'primevue/toast';
import Dialog from 'primevue/dialog';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Dropdown from 'primevue/dropdown';
import Calendar from 'primevue/calendar';
import ConfirmDialog from 'primevue/confirmdialog'

// Import Tailwind styles
import './assets/css/main.css';

const app = createApp(App);

// Use Pinia and Router
app.use(createPinia());
app.use(router);

// Import PrimeVue configuration
import { primeVueConfig } from './config/primevue.config';

// Configure PrimeVue with services
app.use(PrimeVue, primeVueConfig);
app.use(ToastService);
app.use(DialogService);
app.use(ConfirmationService);

// Register common PrimeVue components
app.component('Button', Button);
app.component('InputText', InputText);
app.component('Toast', Toast);
app.component('Dialog', Dialog);
app.component('DataTable', DataTable);
app.component('Column', Column);
app.component('Dropdown', Dropdown);
app.component('Calendar', Calendar);

app.mount('#app');
app.component('ConfirmDialog', ConfirmDialog)
