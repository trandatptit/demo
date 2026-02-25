<template>

  <ConfirmDialog 
    :pt="{
      root: { class: 'rounded-2xl shadow-2xl border-2 border-gray-200 dark:border-gray-700 overflow-hidden min-w-[400px]' },
      header: { class: 'bg-gradient-to-r from-orange-600 to-red-600 dark:from-orange-700 dark:to-red-700 text-white font-bold rounded-t-2xl px-6 py-5' },
      content: { class: 'bg-white dark:bg-gray-800 text-gray-800 dark:text-gray-100 px-6 py-6' },
      message: { class: 'text-gray-700 dark:text-gray-300 text-base leading-relaxed ml-4' },
      icon: { class: 'text-orange-500 dark:text-orange-400 text-5xl' },
      footer: { class: 'bg-gray-50 dark:bg-gray-900 rounded-b-2xl px-6 py-5 border-t border-gray-200 dark:border-gray-700 flex justify-end gap-3' },
      acceptButton: { 
        class: '!bg-gradient-to-r !from-red-600 !to-red-700 dark:!from-red-700 dark:!to-red-800 !text-white hover:!from-red-700 hover:!to-red-800 dark:hover:!from-red-800 dark:hover:!to-red-900 !rounded-xl !px-6 !py-3 !font-semibold !shadow-lg hover:!shadow-xl !transition-all !duration-200 !border-0' 
      },
      rejectButton: { 
        class: '!bg-gray-200 dark:!bg-gray-700 !text-gray-700 dark:!text-gray-200 hover:!bg-gray-300 dark:hover:!bg-gray-600 !rounded-xl !px-6 !py-3 !font-medium !transition-all !duration-200 !shadow-sm hover:!shadow-md !border !border-gray-300 dark:!border-gray-600' 
      }
    }"
  />


  <Dialog
    v-model:visible="visibleLocal"
    :header="title"
    :modal="true"
    :dismissable-mask="dismissableMask"
    :closable="true"
    :draggable="false"
    :style="{ width }"
    @hide="onDialogHide"
    :pt="{
      root: { class: 'rounded-2xl shadow-2xl border border-gray-200 dark:border-gray-700' },
      header: { class: 'bg-gradient-to-r from-blue-600 to-blue-700 dark:from-blue-700 dark:to-blue-800 text-white font-semibold rounded-t-2xl px-6 py-5 border-b-2 border-blue-800 dark:border-blue-900' },
      content: { class: 'bg-white dark:bg-gray-800 text-gray-800 dark:text-gray-100 px-6 py-6' },
      footer: { class: 'bg-gray-50 dark:bg-gray-900 rounded-b-2xl px-6 py-5 border-t border-gray-200 dark:border-gray-700' },
      mask: { class: 'backdrop-blur-sm bg-black/40 dark:bg-black/60' }
    }"
  >
    <!-- Nội dung -->
    <div class="space-y-6">
      <slot
        name="v-content"
        :formData="formData"
        :updateDirty="onSlotUpdateDirty"
      ></slot>

      <!-- fallback -->
      <slot
        v-if="!$slots['v-content']"
        :formData="formData"
        :updateDirty="onSlotUpdateDirty"
      ></slot>
    </div>

    <!-- Footer -->
    <template #footer>
      <div class="flex justify-end gap-3 mt-2">
        <Button
          :label="cancelLabel"
          icon="pi pi-times"
          class="!bg-gray-200 dark:!bg-gray-700 !text-gray-700 dark:!text-gray-200 hover:!bg-gray-300 dark:hover:!bg-gray-600 !rounded-xl !px-6 !py-3 !font-medium !transition-all !duration-200 !shadow-sm hover:!shadow-md !border !border-gray-300 dark:!border-gray-600"
          @click="onCancelClick"
        />
        <Button
          :label="saveLabel"
          icon="pi pi-check"
          class="!bg-gradient-to-r !from-blue-600 !to-blue-700 dark:!from-blue-700 dark:!to-blue-800 !text-white hover:!from-blue-700 hover:!to-blue-800 dark:hover:!from-blue-800 dark:hover:!to-blue-900 !rounded-xl !px-6 !py-3 !font-semibold !shadow-lg hover:!shadow-xl !transition-all !duration-200 !border-0"
          @click="onSave"
        />
      </div>
    </template>
  </Dialog>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useConfirm } from 'primevue/useconfirm'

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  isCustom: { type: Boolean, default: false },
  title: { type: String, default: 'Popup' },
  width: { type: String, default: '40rem' },
  dismissableMask: { type: Boolean, default: true },
  closeAfterSave: { type: Boolean, default: true },
  saveLabel: { type: String, default: 'Lưu' },
  cancelLabel: { type: String, default: 'Hủy bỏ' },
  cancelHandler: { type: Function, default: null },
  formData: { type: Object, default: () => ({}) },
})

const emit = defineEmits([
  'update:modelValue',
  'savedata',
  'cancelPopup',
  'update:dirty',
])

const visibleLocal = ref(props.modelValue)
const dirty = ref(false)
const confirm = useConfirm()

watch(
  () => props.modelValue,
  (v) => (visibleLocal.value = v)
)
watch(visibleLocal, (v) => emit('update:modelValue', v))

function onSlotUpdateDirty(val = true) {
  dirty.value = !!val
  emit('update:dirty', dirty.value)
}

function onSave() {
  emit('savedata', props.formData)
  if (props.closeAfterSave) {
    visibleLocal.value = false
    dirty.value = false
    emit('update:dirty', false)
  }
}

function onCancelClick() {
  handleClose()
}

function onDialogHide() {
  clearFormData()
  visibleLocal.value = false
  dirty.value = false
  emit('cancelPopup')
  emit('update:dirty', false)
}

function handleClose() {
  if (!dirty.value || !props.isCustom) {
    doCancel()
  } else {
    confirm.require({
      header: '⚠️ Xác nhận đóng',
      message: 'Bạn có chắc chắn muốn đóng? Mọi thay đổi chưa lưu sẽ bị mất.',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Đồng ý',
      rejectLabel: 'Hủy bỏ',
      accept: () => doCancel(),
    })
  }
}

function doCancel() {
  clearFormData()
  visibleLocal.value = false
  dirty.value = false
  emit('cancelPopup')
  emit('update:dirty', false)
}

function clearFormData() {
  if (props.formData && typeof props.formData === 'object') {
    for (const key in props.formData) {
      props.formData[key] = ''
    }
  }
}
</script>