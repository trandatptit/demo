<template>
  <div class="manager-account-page">
    <Toast />

    <!-- Header -->
    <div class="page-header">
      <div class="container mx-auto px-4 lg:px-8 py-6">
        <div class="flex items-center justify-between">
          <div>
            <div class="header-accent"></div>
            <span class="header-badge">
              <i class="pi pi-users"></i> Quản Lý
            </span>
            <h1 class="header-title">Quản Lý Tài Khoản</h1>
            <p class="header-subtitle">
              Quản lý toàn bộ tài khoản người dùng trong hệ thống
            </p>
          </div>
          <Button
            label="Thêm Tài Khoản"
            icon="pi pi-plus"
            class="add-btn"
            @click="openCreateDialog"
          />
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="container mx-auto px-4 lg:px-8 py-8">
      <!-- Filter & Search Bar -->
      <Card class="filter-card mb-6">
        <template #content>
          <div class="grid grid-cols-12 gap-4 lg:gap-6">
            <!-- Search -->
            <div class="col-span-12 lg:col-span-9">
              <IconField>
                <InputIcon class="pi pi-search" />
                <InputText
                  v-model="filters.global"
                  placeholder="Tìm kiếm theo tên, email, SĐT..."
                  class="w-full"
                  @input="onFilterChange($event)"
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
            <!-- <div class="col-span-12 lg:col-span-3">
              <Select
                v-model="filters.status"
                :options="statusOptions"
                option-label="label"
                option-value="value"
                placeholder="Lọc theo trạng thái"
                class="w-full"
                @change="onFilterChange"
              />
            </div> -->

            <!-- Clear Filters -->
            <!-- <div class="col-span-12 lg:col-span-1">
              <Button
                icon="pi pi-filter-slash"
                text
                severity="secondary"
                class="w-full"
                v-tooltip="'Xóa Bộ Lọc'"
                @click="clearFilters"
              />
            </div> -->
          </div>
        </template>
      </Card>

      <!-- Data Table -->
      <Card class="table-card">
        <template #content>
          <DataTable
            v-model:selection="selectedUsers"
            :value="filteredUsers"
            :loading="loading"
            :paginator="true"
            :rows="5"
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
                    :label="data.username?.charAt(0)?.toUpperCase()"
                    :style="{
                      backgroundColor: getAvatarColor(data.username),
                      color: '#fff',
                    }"
                    shape="circle"
                  />
                  <div>
                    <div class="font-semibold text-gray-800">
                      {{ data.username }}
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
                <span class="text-gray-700">{{
                  data.phoneNumber || "N/A"
                }}</span>
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

            <!-- <Column
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
            </Column> -->

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
                  <!-- <Button
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
                  /> -->
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
      :style="{ width: '600px', overflow: 'hidden' }"
      :closable="true"
      class="p-fluid dialog-styled"
    >
      <div class="dialog-form-body">
        <!-- Username -->
        <div class="form-group">
          <label for="username" class="form-label">
            <i class="pi pi-user form-label-icon"></i>
            Tên đăng nhập <span class="required-mark">*</span>
          </label>
          <InputText
            id="username"
            v-model="userForm.username"
            placeholder="Nhập tên đăng nhập"
            :class="{ 'p-invalid': submitted && !userForm.username }"
          />
          <small v-if="submitted && !userForm.username" class="p-error"
            >Tên đăng nhập là bắt buộc.</small
          >
        </div>

        <!-- Full Name -->
        <div class="form-group">
          <label for="name" class="form-label">
            <i class="pi pi-id-card form-label-icon"></i>
            Họ và Tên <span class="required-mark">*</span>
          </label>
          <InputText
            id="name"
            v-model="userForm.fullName"
            placeholder="Nhập họ và tên"
            :class="{ 'p-invalid': submitted && !userForm.fullName }"
          />
          <small v-if="submitted && !userForm.fullName" class="p-error"
            >Họ tên là bắt buộc.</small
          >
        </div>

        <!-- Email -->
        <div class="form-group">
          <label for="email" class="form-label">
            <i class="pi pi-envelope form-label-icon"></i>
            Email <span class="required-mark">*</span>
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
        <div class="form-group">
          <label for="phone" class="form-label">
            <i class="pi pi-phone form-label-icon"></i>
            Số Điện Thoại
          </label>
          <InputText
            id="phone"
            v-model="userForm.phoneNumber"
            placeholder="0123456789"
          />
        </div>

        <!-- Password (Only for Create) -->
        <div v-if="dialogMode === 'create'" class="form-group">
          <label for="password" class="form-label">
            <i class="pi pi-lock form-label-icon"></i>
            Mật Khẩu <span class="required-mark">*</span>
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
      </div>

      <template #footer>
        <Button
          label="Hủy"
          icon="pi pi-times"
          text
          class="cancel-btn"
          @click="closeDialog"
        />
        <Button
          :label="dialogMode === 'create' ? 'Tạo Mới' : 'Cập Nhật'"
          icon="pi pi-check"
          class="save-btn"
          @click="saveUser"
        />
      </template>
    </Dialog>

    <!-- View User Dialog -->
    <Dialog
      v-model:visible="viewDialog"
      header="Chi Tiết Tài Khoản"
      :modal="true"
      :style="{ width: '500px', overflow: 'hidden' }"
      :closable="true"
      class="dialog-styled"
    >
      <div v-if="selectedUser" class="dialog-view-body">
        <!-- Avatar Hero Section -->
        <div class="view-hero">
          <Avatar
            :label="selectedUser.fullName?.charAt(0)?.toUpperCase()"
            :style="{
              backgroundColor: getAvatarColor(selectedUser.fullName),
              color: '#fff',
            }"
            shape="circle"
            size="xlarge"
            class="view-avatar"
          />
          <h3 class="view-user-name">
            {{ selectedUser.fullName }}
          </h3>
          <p class="view-user-email">{{ selectedUser.email }}</p>
        </div>

        <!-- Detail Cards -->
        <div class="grid grid-cols-12 gap-3">
          <div class="col-span-12 lg:col-span-6">
            <div class="detail-card">
              <i class="pi pi-hashtag detail-card-icon"></i>
              <div>
                <span class="detail-card-label">ID</span>
                <span class="detail-card-value">#{{ selectedUser.id }}</span>
              </div>
            </div>
          </div>
          <div class="col-span-12 lg:col-span-6">
            <div class="detail-card">
              <i class="pi pi-phone detail-card-icon"></i>
              <div>
                <span class="detail-card-label">Số Điện Thoại</span>
                <span class="detail-card-value">{{
                  selectedUser.phoneNumber || "N/A"
                }}</span>
              </div>
            </div>
          </div>
          <div class="col-span-12 lg:col-span-6">
            <div class="detail-card">
              <i class="pi pi-shield detail-card-icon"></i>
              <div>
                <span class="detail-card-label">Vai Trò</span>
                <Tag
                  :value="getRoleLabel(selectedUser.role)"
                  :severity="getRoleSeverity(selectedUser.role)"
                  class="mt-1"
                />
              </div>
            </div>
          </div>
          <div class="col-span-12 lg:col-span-6">
            <div class="detail-card">
              <i class="pi pi-calendar detail-card-icon"></i>
              <div>
                <span class="detail-card-label">Ngày Tạo</span>
                <span class="detail-card-value">
                  {{ formatDate(selectedUser.createdAt) }}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <template #footer>
        <Button
          label="Đóng"
          icon="pi pi-times"
          text
          class="cancel-btn"
          @click="viewDialog = false"
        />
      </template>
    </Dialog>

    <!-- Delete Confirmation Dialog -->
    <Dialog
      v-model:visible="deleteDialog"
      header="Xác Nhận Xóa"
      :modal="true"
      class="dialog-styled dialog-delete"
      :style="{ width: '450px', overflow: 'hidden' }"
    >
      <div class="delete-body">
        <div class="delete-icon-wrap">
          <i class="pi pi-exclamation-triangle"></i>
        </div>
        <div class="delete-text" v-if="selectedUser">
          <p>Bạn có chắc chắn muốn xóa tài khoản</p>
          <p class="delete-user-name">{{ selectedUser.name }}</p>
          <p class="delete-warning">Hành động này không thể hoàn tác.</p>
        </div>
      </div>

      <template #footer>
        <Button
          label="Hủy"
          icon="pi pi-times"
          text
          class="cancel-btn"
          @click="deleteDialog = false"
        />
        <Button
          label="Xóa"
          icon="pi pi-trash"
          class="delete-btn"
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
import userApi from "@/apis/userApi";
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
  username: "",
  fullName: "",
  email: "",
  phoneNumber: "",
  password: "",
});

// Options
const roleOptions = [
  { label: "Tất cả vai trò", value: null },
  { label: "Admin", value: "admin" },
  { label: "User", value: "user" },
  // { label: "Manager", value: "manager" },
];

const statusOptions = [
  { label: "Tất cả trạng thái", value: null },
  { label: "Hoạt động", value: "active" },
  { label: "Tạm khóa", value: "inactive" },
];

// Computed
const filteredUsers = ref([]);
const handlefilteredUsers = () => {
  let result = [...users.value];
  for (const userItem of result) {
    if (userItem?.isAdmin) {
      userItem.role = "Admin";
    } else {
      userItem.role = "User";
    }
  }

  // Global search
  if (filters.value.global) {
    const searchTerm = filters.value.global.toLowerCase();
    result = result.filter(
      (user) =>
        user?.username?.toLowerCase().includes(searchTerm) ||
        user?.email?.toLowerCase().includes(searchTerm) ||
        (user?.phoneNumber && user?.phoneNumber.includes(searchTerm))
    );
  }

  // Role filter
  if (filters.value.role) {
    result = result.filter(
      (user) => user?.role?.toLowerCase() === filters.value.role?.toLowerCase()
    );
  }

  return result;
};

// Methods
const loadUsers = async () => {
  loading.value = true;
  try {
    const response = await userApi.getAll();
    // Mock data nếu API chưa có data
    users.value = response?.result?.content ?? [];
    filteredUsers.value = handlefilteredUsers() ?? [];
    // toast.add({
    //   severity: "success",
    //   summary: "Thành công",
    //   detail: "Đã tải danh sách tài khoản",
    //   life: 3000,
    // });
  } catch (error) {
    console.error("Error loading users:", error);
    // Use mock data for demo
    users.value = generateMockUsers();
    // toast.add({
    //   severity: "info",
    //   summary: "Thông báo",
    //   detail: "Đang sử dụng dữ liệu mẫu",
    //   life: 3000,
    // });
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
    username: "",
    fullName: "",
    email: "",
    phoneNumber: "",
    password: "",
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
  if (
    !userForm.value.username ||
    !userForm.value.email ||
    !userForm.value.fullName
  ) {
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

      var response = await userApi.createFullUserInfo(newUser);
      if (response?.status) {
        toast.add({
          severity: "success",
          summary: "Thành công",
          detail: "Đã tạo tài khoản mới",
          life: 3000,
        });
        await loadUsers();
      } else {
        toast.add({
          severity: "error",
          summary: "Lỗi",
          detail: response?.message || "Có lỗi xảy ra khi tạo tài khoản",
          life: 3000,
        });
        return;
      }
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
    const response = await userApi.deleteUser(selectedUser.value.id);
    if (!response?.status) {
      toast.add({
        severity: "error",
        summary: "Lỗi",
        detail: response?.message || "Có lỗi xảy ra khi xóa tài khoản",
        life: 3000,
      });
      return;
    }

    toast.add({
      severity: "success",
      summary: "Thành công",
      detail: "Đã xóa tài khoản",
      life: 3000,
    });
    await loadUsers();

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

const onFilterChange = ($event) => {
  console.log($event);
  filteredUsers.value = handlefilteredUsers() ?? [];
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
  const index = name?.charCodeAt(0) % colors.length;
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
/* ─── Design Tokens ─── */
.manager-account-page {
  --primary: #2563eb;
  --primary-light: #3b82f6;
  --primary-dark: #1d4ed8;
  --primary-bg: #eff6ff;
  --primary-bg-deep: #dbeafe;
  --primary-ring: rgba(37, 99, 235, 0.15);
  --surface: #ffffff;
  --surface-alt: #f8fafc;
  --text-primary: #0f172a;
  --text-secondary: #475569;
  --text-muted: #94a3b8;
  --border: #e2e8f0;
  --border-hover: #cbd5e1;
  --radius: 12px;
  --shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
  --shadow-md: 0 4px 12px rgba(0, 0, 0, 0.08);
  --shadow-lg: 0 8px 24px rgba(0, 0, 0, 0.1);

  min-height: 100vh;
  background: linear-gradient(135deg, var(--surface-alt) 0%, #eef2ff 100%);
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    sans-serif;
}

/* ─── Page Header ─── */
.page-header {
  background: var(--surface);
  box-shadow: var(--shadow-md);
  border-bottom: 1px solid var(--border);
}

.header-accent {
  width: 48px;
  height: 4px;
  border-radius: 4px;
  background: linear-gradient(90deg, var(--primary), var(--primary-light));
  margin-bottom: 12px;
}

.header-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 14px;
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  color: var(--primary);
  background: var(--primary-bg);
  border: 1px solid var(--primary-bg-deep);
  border-radius: 999px;
  margin-bottom: 10px;
}

.header-title {
  font-size: clamp(1.5rem, 3vw, 2rem);
  font-weight: 800;
  color: var(--text-primary);
  margin: 0 0 4px;
  line-height: 1.25;
}

.header-subtitle {
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--text-secondary);
  margin: 0;
}

/* ─── Add / Save Buttons ─── */
:deep(.add-btn.p-button),
:deep(.save-btn.p-button) {
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-light) 100%
  );
  border: none;
  padding: 10px 20px;
  font-size: 0.9rem;
  font-weight: 700;
  border-radius: var(--radius);
  box-shadow: 0 4px 14px rgba(37, 99, 235, 0.35);
  transition: all 0.25s ease;
  color: white;
}

:deep(.add-btn.p-button:hover),
:deep(.save-btn.p-button:hover) {
  background: linear-gradient(
    135deg,
    var(--primary-dark) 0%,
    var(--primary) 100%
  );
  box-shadow: 0 6px 20px rgba(37, 99, 235, 0.45);
  transform: translateY(-2px);
}

/* ─── Cards ─── */
:deep(.filter-card.p-card),
:deep(.table-card.p-card) {
  border-radius: 16px;
  border: 1px solid var(--border);
  box-shadow: var(--shadow-lg);
  background: var(--surface);
  position: relative;
  overflow: hidden;
}

:deep(.filter-card.p-card)::before,
:deep(.table-card.p-card)::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(
    90deg,
    var(--primary),
    var(--primary-light),
    #818cf8
  );
}

:deep(.p-card .p-card-content) {
  padding: 1.5rem;
}

/* ─── Data Table ─── */
:deep(.p-datatable .p-datatable-thead > tr > th) {
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-dark) 100%
  );
  color: white;
  font-weight: 600;
  border: none;
  font-size: 0.85rem;
  letter-spacing: 0.3px;
}

:deep(.p-datatable .p-datatable-tbody > tr) {
  transition: background-color 0.2s ease;
}

:deep(.p-datatable .p-datatable-tbody > tr:hover) {
  background-color: var(--primary-bg);
}

:deep(.p-datatable .p-datatable-tbody > tr > td) {
  border-color: var(--border);
}

/* ─── Dialogs ─── */
.dialog-styled {
  overflow: hidden;
}

:deep(.p-dialog) {
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

:deep(.p-dialog .p-dialog-header) {
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-dark) 100%
  );
  color: white;
  border: none;
  border-radius: 16px 16px 0 0;
  padding: 1.25rem 1.5rem;
}

:deep(.p-dialog .p-dialog-header .p-dialog-header-close) {
  color: rgba(255, 255, 255, 0.8);
}

:deep(.p-dialog .p-dialog-header .p-dialog-header-close:hover) {
  color: white;
  background: rgba(255, 255, 255, 0.15);
}

:deep(.p-dialog .p-dialog-content) {
  padding: 1.5rem;
}

:deep(.p-dialog .p-dialog-footer) {
  padding: 1rem 1.5rem;
  border-top: 1px solid var(--border);
}

/* ─── Dialog Form Body ─── */
.dialog-form-body {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 8px 0;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.85rem;
  font-weight: 600;
  color: var(--text-primary);
  letter-spacing: 0.2px;
}

.form-label-icon {
  font-size: 14px;
  color: var(--primary-light);
}

.required-mark {
  color: #ef4444;
  font-weight: 700;
}

/* ─── View User Dialog ─── */
.dialog-view-body {
  padding: 8px 0;
}

.view-hero {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px 16px;
  margin: -8px -8px 20px;
  background: linear-gradient(
    135deg,
    var(--primary-bg) 0%,
    var(--primary-bg-deep) 100%
  );
  border-radius: 12px;
  border: 1px solid rgba(37, 99, 235, 0.1);
}

:deep(.view-avatar) {
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.12);
  border: 3px solid white;
}

.view-user-name {
  font-size: 1.4rem;
  font-weight: 800;
  color: var(--text-primary);
  margin-top: 14px;
  text-align: center;
}

.view-user-email {
  font-size: 0.85rem;
  color: var(--text-secondary);
  margin-top: 2px;
}

.detail-card {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 16px;
  background: var(--surface-alt);
  border: 1px solid var(--border);
  border-radius: 12px;
  transition: all 0.2s ease;
}

.detail-card:hover {
  background: var(--primary-bg);
  border-color: var(--primary-bg-deep);
}

.detail-card-icon {
  font-size: 18px;
  color: var(--primary-light);
  flex-shrink: 0;
}

.detail-card-label {
  display: block;
  font-size: 0.72rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  color: var(--text-muted);
}

.detail-card-value {
  display: block;
  font-size: 0.9rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-top: 2px;
}

/* ─── Delete Dialog ─── */
.delete-body {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 12px 0;
  gap: 16px;
}

.delete-icon-wrap {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  background: linear-gradient(135deg, #fef2f2 0%, #fee2e2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px solid #fecaca;
}

.delete-icon-wrap .pi {
  font-size: 28px;
  color: #ef4444;
}

.delete-text {
  color: var(--text-secondary);
  font-size: 0.9rem;
  line-height: 1.6;
}

.delete-user-name {
  font-size: 1.1rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 4px 0;
}

.delete-warning {
  font-size: 0.8rem;
  color: var(--text-muted);
  margin-top: 4px;
}

/* ─── Cancel / Delete Buttons ─── */
:deep(.cancel-btn.p-button) {
  color: var(--text-secondary) !important;
  font-weight: 600;
}

:deep(.cancel-btn.p-button:hover) {
  color: var(--text-primary) !important;
  background: var(--surface-alt) !important;
}

:deep(.delete-btn.p-button) {
  background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
  border: none;
  border-radius: var(--radius);
  font-weight: 700;
  box-shadow: 0 4px 14px rgba(239, 68, 68, 0.35);
  transition: all 0.25s ease;
  color: white;
}

:deep(.delete-btn.p-button:hover) {
  background: linear-gradient(135deg, #dc2626 0%, #b91c1c 100%);
  box-shadow: 0 6px 20px rgba(239, 68, 68, 0.45);
  transform: translateY(-2px);
}

/* ─── Tags ─── */
:deep(.p-tag) {
  font-weight: 600;
  padding: 0.25rem 0.75rem;
  border-radius: 8px;
}

/* ─── Form Inputs ─── */
:deep(.p-inputtext),
:deep(.p-select),
:deep(.p-password input) {
  border: 1.5px solid var(--border);
  border-radius: var(--radius);
  font-family: inherit;
  transition: all 0.2s ease;
}

:deep(.p-inputtext:focus),
:deep(.p-select:focus),
:deep(.p-password input:focus) {
  border-color: var(--primary-light);
  box-shadow: 0 0 0 3px var(--primary-ring);
}

/* ─── Animations ─── */
.page-header,
.filter-card,
.table-card {
  animation: fadeInUp 0.4s ease-out both;
}

.filter-card {
  animation-delay: 0.05s;
}

.table-card {
  animation-delay: 0.1s;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(14px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* ─── Dark Mode ─── */
@media (prefers-color-scheme: dark) {
  .manager-account-page {
    --surface: #1e293b;
    --surface-alt: #0f172a;
    --text-primary: #f1f5f9;
    --text-secondary: #cbd5e1;
    --text-muted: #64748b;
    --border: #334155;
    --border-hover: #475569;
    --primary-bg: rgba(37, 99, 235, 0.12);
    --primary-bg-deep: rgba(37, 99, 235, 0.2);
  }

  .page-header {
    background: #1e293b;
    border-bottom-color: #334155;
  }

  .header-badge {
    color: var(--primary-light);
    background: rgba(37, 99, 235, 0.15);
    border-color: rgba(37, 99, 235, 0.25);
  }

  :deep(.p-inputtext),
  :deep(.p-select),
  :deep(.p-password input) {
    background: #374151;
    border-color: #4b5563;
    color: var(--text-primary);
  }

  :deep(.p-datatable .p-datatable-tbody > tr:hover) {
    background-color: rgba(37, 99, 235, 0.08);
  }

  :deep(.p-dialog .p-dialog-content) {
    background: var(--surface);
  }

  :deep(.p-dialog .p-dialog-footer) {
    background: var(--surface);
    border-top-color: #334155;
  }
}
</style>
