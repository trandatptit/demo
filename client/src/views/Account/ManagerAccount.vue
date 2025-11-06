<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100">
    <Toast />

    <!-- Header -->
    <div class="bg-white shadow-md">
      <div class="container mx-auto px-4 lg:px-8 py-6">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="text-3xl font-bold text-blue-900">Quản Lý Tài Khoản</h1>
            <p class="text-gray-600 mt-1">
              Quản lý toàn bộ tài khoản người dùng trong hệ thống
            </p>
          </div>
          <Button
            label="Thêm Tài Khoản"
            icon="pi pi-plus"
            class="bg-blue-600 hover:bg-blue-700 text-white border-0"
            @click="openCreateDialog"
          />
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="container mx-auto px-4 lg:px-8 py-8">
      <!-- Filter & Search Bar -->
      <Card class="mb-6 shadow-lg">
        <template #content>
          <div class="grid grid-cols-12 gap-4 lg:gap-6">
            <!-- Search -->
            <div class="col-span-12 lg:col-span-5">
              <IconField>
                <InputIcon class="pi pi-search" />
                <InputText
                  v-model="filters.global"
                  placeholder="Tìm kiếm theo tên, email, SĐT..."
                  class="w-full"
                  @input="onFilterChange"
                />
              </IconField>
            </div>

            <!-- Role Filter -->
            <div class="col-span-12 lg:col-span-3">
              <Select
                v-model="filters.role"
                :options="roleOptions"
                option-label="label"
                option-value="value"
                placeholder="Lọc theo vai trò"
                class="w-full"
                @change="onFilterChange"
              />
            </div>

            <!-- Status Filter -->
            <div class="col-span-12 lg:col-span-3">
              <Select
                v-model="filters.status"
                :options="statusOptions"
                option-label="label"
                option-value="value"
                placeholder="Lọc theo trạng thái"
                class="w-full"
                @change="onFilterChange"
              />
            </div>

            <!-- Clear Filters -->
            <div class="col-span-12 lg:col-span-1">
              <Button
                icon="pi pi-filter-slash"
                text
                severity="secondary"
                class="w-full"
                v-tooltip="'Xóa Bộ Lọc'"
                @click="clearFilters"
              />
            </div>
          </div>
        </template>
      </Card>

      <!-- Data Table -->
      <Card class="shadow-lg">
        <template #content>
          <DataTable
            v-model:selection="selectedUsers"
            :value="filteredUsers"
            :loading="loading"
            :paginator="true"
            :rows="10"
            :rows-per-page-options="[5, 10, 20, 50]"
            paginator-template="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            current-page-report-template="Hiển thị {first} đến {last} trong tổng số {totalRecords} tài khoản"
            striped-rows
            removable-sort
            class="p-datatable-sm"
          >
            <template #empty>
              <div class="text-center py-8">
                <i class="pi pi-inbox text-gray-400 text-6xl mb-4"></i>
                <p class="text-gray-600 text-lg">
                  Không tìm thấy tài khoản nào
                </p>
              </div>
            </template>

            <template #loading>
              <div class="text-center py-8">
                <ProgressSpinner />
                <p class="text-gray-600 mt-4">Đang tải dữ liệu...</p>
              </div>
            </template>

            <Column selection-mode="multiple" header-style="width: 3rem" />

            <Column field="id" header="ID" sortable style="min-width: 80px">
              <template #body="{ data }">
                <span class="font-mono text-sm text-gray-600"
                  >#{{ data.id }}</span
                >
              </template>
            </Column>

            <Column
              field="name"
              header="Họ Tên"
              sortable
              style="min-width: 200px"
            >
              <template #body="{ data }">
                <div class="flex items-center gap-3">
                  <Avatar
                    :label="data.name.charAt(0).toUpperCase()"
                    :style="{
                      backgroundColor: getAvatarColor(data.name),
                      color: '#fff',
                    }"
                    shape="circle"
                  />
                  <div>
                    <div class="font-semibold text-gray-800">
                      {{ data.name }}
                    </div>
                    <div class="text-sm text-gray-500">{{ data.email }}</div>
                  </div>
                </div>
              </template>
            </Column>

            <Column
              field="phone"
              header="Số Điện Thoại"
              sortable
              style="min-width: 150px"
            >
              <template #body="{ data }">
                <span class="text-gray-700">{{ data.phone || "N/A" }}</span>
              </template>
            </Column>

            <Column
              field="role"
              header="Vai Trò"
              sortable
              style="min-width: 130px"
            >
              <template #body="{ data }">
                <Tag
                  :value="getRoleLabel(data.role)"
                  :severity="getRoleSeverity(data.role)"
                  :icon="getRoleIcon(data.role)"
                />
              </template>
            </Column>

            <Column
              field="status"
              header="Trạng Thái"
              sortable
              style="min-width: 130px"
            >
              <template #body="{ data }">
                <Tag
                  :value="data.status === 'active' ? 'Hoạt động' : 'Tạm khóa'"
                  :severity="data.status === 'active' ? 'success' : 'danger'"
                  :icon="
                    data.status === 'active'
                      ? 'pi pi-check-circle'
                      : 'pi pi-lock'
                  "
                />
              </template>
            </Column>

            <Column
              field="createdAt"
              header="Ngày Tạo"
              sortable
              style="min-width: 150px"
            >
              <template #body="{ data }">
                <span class="text-gray-600">{{
                  formatDate(data.createdAt)
                }}</span>
              </template>
            </Column>

            <Column header="Thao Tác" style="min-width: 200px">
              <template #body="{ data }">
                <div class="flex gap-2">
                  <Button
                    icon="pi pi-eye"
                    rounded
                    text
                    severity="info"
                    v-tooltip="'Xem chi tiết'"
                    @click="viewUser(data)"
                  />
                  <Button
                    icon="pi pi-pencil"
                    rounded
                    text
                    severity="warning"
                    v-tooltip="'Chỉnh sửa'"
                    @click="editUser(data)"
                  />
                  <Button
                    :icon="
                      data.status === 'active' ? 'pi pi-lock' : 'pi pi-unlock'
                    "
                    rounded
                    text
                    :severity="
                      data.status === 'active' ? 'secondary' : 'success'
                    "
                    v-tooltip="
                      data.status === 'active' ? 'Khóa tài khoản' : 'Mở khóa'
                    "
                    @click="toggleUserStatus(data)"
                  />
                  <Button
                    icon="pi pi-trash"
                    rounded
                    text
                    severity="danger"
                    v-tooltip="'Xóa'"
                    @click="confirmDelete(data)"
                  />
                </div>
              </template>
            </Column>
          </DataTable>
        </template>
      </Card>
    </div>

    <!-- Create/Edit Dialog -->
    <Dialog
      v-model:visible="userDialog"
      :header="
        dialogMode === 'create' ? 'Thêm Tài Khoản Mới' : 'Chỉnh Sửa Tài Khoản'
      "
      :modal="true"
      :style="{ width: '600px' }"
      :closable="true"
      class="p-fluid"
    >
      <div class="space-y-4 py-4">
        <!-- Full Name -->
        <div>
          <label
            for="name"
            class="block text-sm font-semibold text-gray-700 mb-2"
          >
            Họ và Tên <span class="text-red-500">*</span>
          </label>
          <InputText
            id="name"
            v-model="userForm.name"
            placeholder="Nhập họ và tên"
            :class="{ 'p-invalid': submitted && !userForm.name }"
          />
          <small v-if="submitted && !userForm.name" class="p-error"
            >Họ tên là bắt buộc.</small
          >
        </div>

        <!-- Email -->
        <div>
          <label
            for="email"
            class="block text-sm font-semibold text-gray-700 mb-2"
          >
            Email <span class="text-red-500">*</span>
          </label>
          <InputText
            id="email"
            v-model="userForm.email"
            type="email"
            placeholder="example@email.com"
            :class="{ 'p-invalid': submitted && !userForm.email }"
          />
          <small v-if="submitted && !userForm.email" class="p-error"
            >Email là bắt buộc.</small
          >
        </div>

        <!-- Phone -->
        <div>
          <label
            for="phone"
            class="block text-sm font-semibold text-gray-700 mb-2"
          >
            Số Điện Thoại
          </label>
          <InputText
            id="phone"
            v-model="userForm.phone"
            placeholder="0123456789"
          />
        </div>

        <!-- Password (Only for Create) -->
        <div v-if="dialogMode === 'create'">
          <label
            for="password"
            class="block text-sm font-semibold text-gray-700 mb-2"
          >
            Mật Khẩu <span class="text-red-500">*</span>
          </label>
          <Password
            id="password"
            v-model="userForm.password"
            placeholder="Nhập mật khẩu"
            toggle-mask
            :class="{ 'p-invalid': submitted && !userForm.password }"
          />
          <small v-if="submitted && !userForm.password" class="p-error"
            >Mật khẩu là bắt buộc.</small
          >
        </div>

        <!-- Role -->
        <div>
          <label
            for="role"
            class="block text-sm font-semibold text-gray-700 mb-2"
          >
            Vai Trò <span class="text-red-500">*</span>
          </label>
          <Select
            id="role"
            v-model="userForm.role"
            :options="roleOptions.filter((r) => r.value)"
            option-label="label"
            option-value="value"
            placeholder="Chọn vai trò"
            :class="{ 'p-invalid': submitted && !userForm.role }"
          />
          <small v-if="submitted && !userForm.role" class="p-error"
            >Vai trò là bắt buộc.</small
          >
        </div>

        <!-- Status -->
        <div>
          <label
            for="status"
            class="block text-sm font-semibold text-gray-700 mb-2"
          >
            Trạng Thái
          </label>
          <Select
            id="status"
            v-model="userForm.status"
            :options="statusOptions.filter((s) => s.value)"
            option-label="label"
            option-value="value"
            placeholder="Chọn trạng thái"
          />
        </div>
      </div>

      <template #footer>
        <Button
          label="Hủy"
          icon="pi pi-times"
          text
          severity="secondary"
          @click="closeDialog"
        />
        <Button
          :label="dialogMode === 'create' ? 'Tạo Mới' : 'Cập Nhật'"
          icon="pi pi-check"
          class="bg-blue-600 hover:bg-blue-700 text-white border-0"
          @click="saveUser"
        />
      </template>
    </Dialog>

    <!-- View User Dialog -->
    <Dialog
      v-model:visible="viewDialog"
      header="Chi Tiết Tài Khoản"
      :modal="true"
      :style="{ width: '500px' }"
      :closable="true"
    >
      <div v-if="selectedUser" class="space-y-4 py-4">
        <div class="flex flex-col items-center mb-6">
          <Avatar
            :label="selectedUser.name.charAt(0).toUpperCase()"
            :style="{
              backgroundColor: getAvatarColor(selectedUser.name),
              color: '#fff',
            }"
            shape="circle"
            size="xlarge"
          />
          <h3 class="text-2xl font-bold text-gray-800 mt-4">
            {{ selectedUser.name }}
          </h3>
          <p class="text-gray-600">{{ selectedUser.email }}</p>
        </div>

        <div class="grid grid-cols-12 gap-4">
          <div class="col-span-12 lg:col-span-6">
            <label class="block text-sm font-semibold text-gray-600 mb-1"
              >ID</label
            >
            <p class="text-gray-800">#{{ selectedUser.id }}</p>
          </div>
          <div class="col-span-12 lg:col-span-6">
            <label class="block text-sm font-semibold text-gray-600 mb-1"
              >Số Điện Thoại</label
            >
            <p class="text-gray-800">{{ selectedUser.phone || "N/A" }}</p>
          </div>
          <div class="col-span-12 lg:col-span-6">
            <label class="block text-sm font-semibold text-gray-600 mb-1"
              >Vai Trò</label
            >
            <Tag
              :value="getRoleLabel(selectedUser.role)"
              :severity="getRoleSeverity(selectedUser.role)"
            />
          </div>
          <div class="col-span-12 lg:col-span-6">
            <label class="block text-sm font-semibold text-gray-600 mb-1"
              >Trạng Thái</label
            >
            <Tag
              :value="
                selectedUser.status === 'active' ? 'Hoạt động' : 'Tạm khóa'
              "
              :severity="
                selectedUser.status === 'active' ? 'success' : 'danger'
              "
            />
          </div>
          <div class="col-span-12">
            <label class="block text-sm font-semibold text-gray-600 mb-1"
              >Ngày Tạo</label
            >
            <p class="text-gray-800">
              {{ formatDate(selectedUser.createdAt) }}
            </p>
          </div>
        </div>
      </div>

      <template #footer>
        <Button
          label="Đóng"
          icon="pi pi-times"
          text
          @click="viewDialog = false"
        />
      </template>
    </Dialog>

    <!-- Delete Confirmation Dialog -->
    <Dialog
      v-model:visible="deleteDialog"
      header="Xác Nhận Xóa"
      :modal="true"
      :style="{ width: '450px' }"
    >
      <div class="flex items-center gap-4">
        <i class="pi pi-exclamation-triangle text-red-500 text-4xl"></i>
        <span v-if="selectedUser">
          Bạn có chắc chắn muốn xóa tài khoản
          <strong>{{ selectedUser.name }}</strong
          >? <br />Hành động này không thể hoàn tác.
        </span>
      </div>

      <template #footer>
        <Button
          label="Hủy"
          icon="pi pi-times"
          text
          @click="deleteDialog = false"
        />
        <Button
          label="Xóa"
          icon="pi pi-trash"
          severity="danger"
          @click="deleteUser"
        />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useToast } from "primevue/usetoast";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import Card from "primevue/card";
import Button from "primevue/button";
import InputText from "primevue/inputtext";
import Select from "primevue/select";
import Dialog from "primevue/dialog";
import Avatar from "primevue/avatar";
import Tag from "primevue/tag";
import IconField from "primevue/iconfield";
import InputIcon from "primevue/inputicon";
import Password from "primevue/password";
import ProgressSpinner from "primevue/progressspinner";
import Toast from "primevue/toast";
// import userApi from "../../apis/userApi";

const toast = useToast();

// State
const loading = ref(false);
const users = ref([]);
const selectedUsers = ref([]);
const selectedUser = ref(null);
const userDialog = ref(false);
const viewDialog = ref(false);
const deleteDialog = ref(false);
const dialogMode = ref("create"); // 'create' or 'edit'
const submitted = ref(false);

// Filters
const filters = ref({
  global: "",
  role: null,
  status: null,
});

// Form
const userForm = ref({
  name: "",
  email: "",
  phone: "",
  password: "",
  role: null,
  status: "active",
});

// Options
const roleOptions = [
  { label: "Tất cả vai trò", value: null },
  { label: "Admin", value: "admin" },
  { label: "User", value: "user" },
  { label: "Manager", value: "manager" },
];

const statusOptions = [
  { label: "Tất cả trạng thái", value: null },
  { label: "Hoạt động", value: "active" },
  { label: "Tạm khóa", value: "inactive" },
];

// Computed
const filteredUsers = computed(() => {
  let result = [...users.value];

  // Global search
  if (filters.value.global) {
    const searchTerm = filters.value.global.toLowerCase();
    result = result.filter(
      (user) =>
        user.name.toLowerCase().includes(searchTerm) ||
        user.email.toLowerCase().includes(searchTerm) ||
        (user.phone && user.phone.includes(searchTerm))
    );
  }

  // Role filter
  if (filters.value.role) {
    result = result.filter((user) => user.role === filters.value.role);
  }

  // Status filter
  if (filters.value.status) {
    result = result.filter((user) => user.status === filters.value.status);
  }

  return result;
});

// Methods
const loadUsers = async () => {
  loading.value = true;
  try {
    // const response = await userApi.getAll();
    // Mock data nếu API chưa có data
    users.value = generateMockUsers();
    toast.add({
      severity: "success",
      summary: "Thành công",
      detail: "Đã tải danh sách tài khoản",
      life: 3000,
    });
  } catch (error) {
    console.error("Error loading users:", error);
    // Use mock data for demo
    users.value = generateMockUsers();
    toast.add({
      severity: "info",
      summary: "Thông báo",
      detail: "Đang sử dụng dữ liệu mẫu",
      life: 3000,
    });
  } finally {
    loading.value = false;
  }
};

const generateMockUsers = () => {
  return [
    {
      id: 1,
      name: "Nguyễn Văn A",
      email: "nguyenvana@example.com",
      phone: "0123456789",
      role: "admin",
      status: "active",
      createdAt: new Date("2024-01-15"),
    },
    {
      id: 2,
      name: "Trần Thị B",
      email: "tranthib@example.com",
      phone: "0987654321",
      role: "user",
      status: "active",
      createdAt: new Date("2024-02-20"),
    },
    {
      id: 3,
      name: "Lê Văn C",
      email: "levanc@example.com",
      phone: "0369852147",
      role: "manager",
      status: "inactive",
      createdAt: new Date("2024-03-10"),
    },
    {
      id: 4,
      name: "Phạm Thị D",
      email: "phamthid@example.com",
      phone: "0147258369",
      role: "user",
      status: "active",
      createdAt: new Date("2024-04-05"),
    },
    {
      id: 5,
      name: "Hoàng Văn E",
      email: "hoangvane@example.com",
      phone: "0258963147",
      role: "user",
      status: "active",
      createdAt: new Date("2024-05-12"),
    },
  ];
};

const openCreateDialog = () => {
  userForm.value = {
    name: "",
    email: "",
    phone: "",
    password: "",
    role: null,
    status: "active",
  };
  dialogMode.value = "create";
  submitted.value = false;
  userDialog.value = true;
};

const editUser = (user) => {
  userForm.value = { ...user };
  dialogMode.value = "edit";
  submitted.value = false;
  selectedUser.value = user;
  userDialog.value = true;
};

const viewUser = (user) => {
  selectedUser.value = user;
  viewDialog.value = true;
};

const closeDialog = () => {
  userDialog.value = false;
  submitted.value = false;
};

const saveUser = async () => {
  submitted.value = true;

  // Validation
  if (!userForm.value.name || !userForm.value.email || !userForm.value.role) {
    return;
  }

  if (dialogMode.value === "create" && !userForm.value.password) {
    return;
  }

  try {
    if (dialogMode.value === "create") {
      // Create new user
      const newUser = {
        ...userForm.value,
        id: users.value.length + 1,
        createdAt: new Date(),
      };
      users.value.push(newUser);

      toast.add({
        severity: "success",
        summary: "Thành công",
        detail: "Đã tạo tài khoản mới",
        life: 3000,
      });
    } else {
      // Update existing user
      const index = users.value.findIndex(
        (u) => u.id === selectedUser.value.id
      );
      if (index !== -1) {
        users.value[index] = { ...userForm.value };
      }

      toast.add({
        severity: "success",
        summary: "Thành công",
        detail: "Đã cập nhật thông tin tài khoản",
        life: 3000,
      });
    }

    closeDialog();
  } catch (error) {
    console.error("Error saving user:", error);
    toast.add({
      severity: "error",
      summary: "Lỗi",
      detail: "Có lỗi xảy ra khi lưu thông tin",
      life: 3000,
    });
  }
};

const confirmDelete = (user) => {
  selectedUser.value = user;
  deleteDialog.value = true;
};

const deleteUser = async () => {
  try {
    users.value = users.value.filter((u) => u.id !== selectedUser.value.id);

    toast.add({
      severity: "success",
      summary: "Thành công",
      detail: "Đã xóa tài khoản",
      life: 3000,
    });

    deleteDialog.value = false;
    selectedUser.value = null;
  } catch (error) {
    console.error("Error deleting user:", error);
    toast.add({
      severity: "error",
      summary: "Lỗi",
      detail: "Có lỗi xảy ra khi xóa tài khoản",
      life: 3000,
    });
  }
};

const toggleUserStatus = async (user) => {
  try {
    const newStatus = user.status === "active" ? "inactive" : "active";
    const index = users.value.findIndex((u) => u.id === user.id);

    if (index !== -1) {
      users.value[index].status = newStatus;
    }

    toast.add({
      severity: "success",
      summary: "Thành công",
      detail: `Đã ${newStatus === "active" ? "mở khóa" : "khóa"} tài khoản`,
      life: 3000,
    });
  } catch (error) {
    console.error("Error toggling user status:", error);
    toast.add({
      severity: "error",
      summary: "Lỗi",
      detail: "Có lỗi xảy ra khi thay đổi trạng thái",
      life: 3000,
    });
  }
};

const onFilterChange = () => {
  // Trigger computed property re-evaluation
};

const clearFilters = () => {
  filters.value = {
    global: "",
    role: null,
    status: null,
  };
};

// Helper Functions
const formatDate = (date) => {
  if (!date) return "N/A";
  return new Date(date).toLocaleDateString("vi-VN", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  });
};

const getAvatarColor = (name) => {
  const colors = [
    "#3b82f6",
    "#10b981",
    "#f59e0b",
    "#ef4444",
    "#8b5cf6",
    "#ec4899",
  ];
  const index = name.charCodeAt(0) % colors.length;
  return colors[index];
};

const getRoleLabel = (role) => {
  const labels = {
    admin: "Admin",
    user: "User",
    manager: "Manager",
  };
  return labels[role] || role;
};

const getRoleSeverity = (role) => {
  const severities = {
    admin: "danger",
    manager: "warning",
    user: "info",
  };
  return severities[role] || "secondary";
};

const getRoleIcon = (role) => {
  const icons = {
    admin: "pi pi-shield",
    manager: "pi pi-star",
    user: "pi pi-user",
  };
  return icons[role] || "pi pi-user";
};

// Lifecycle
onMounted(() => {
  loadUsers();
});
</script>

<style scoped>
:deep(.p-datatable .p-datatable-thead > tr > th) {
  background: linear-gradient(135deg, #2563eb 0%, #1d4ed8 100%);
  color: white;
  font-weight: 600;
  border: none;
}

:deep(.p-datatable .p-datatable-tbody > tr:hover) {
  background-color: #eff6ff;
}

:deep(.p-card) {
  border-radius: 12px;
}

:deep(.p-card .p-card-content) {
  padding: 1.5rem;
}

:deep(.p-dialog .p-dialog-header) {
  background: linear-gradient(135deg, #2563eb 0%, #1d4ed8 100%);
  color: white;
  border-radius: 12px 12px 0 0;
}

:deep(.p-tag) {
  font-weight: 600;
  padding: 0.25rem 0.75rem;
}

:deep(.p-inputtext),
:deep(.p-select),
:deep(.p-password input) {
  border: 1px solid #d1d5db;
  border-radius: 6px;
}

:deep(.p-inputtext:focus),
:deep(.p-select:focus),
:deep(.p-password input:focus) {
  border-color: #2563eb;
  box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
}
</style>
