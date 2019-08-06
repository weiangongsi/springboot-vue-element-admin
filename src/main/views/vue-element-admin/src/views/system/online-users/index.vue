<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="username"
        label="username"
      />
      <el-table-column
        prop="nickname"
        label="nickname"
      />
      <el-table-column
        prop="loginTime"
        label="loginTime"
      />
      <el-table-column
        label="operations"
      >
        <template slot-scope="scope">
          <el-button
            type="text"
            size="small"
            @click="handleForceLogout(scope.row)"
          >Log Out
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { forceLogout, getAllOnlineUsers } from '@/api/online-user'

export default {
  name: 'OnlineUsers',
  data() {
    return {
      list: null,
      listLoading: true
    }
  },

  created() {
    this.getList()
  },
  methods: {
    // 获取列表
    async getList() {
      this.listLoading = true
      const { data } = await getAllOnlineUsers()
      this.list = data
      this.listLoading = false
    },
    // 强制退出
    handleForceLogout(row) {
      this.$confirm('是否确认该操作？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        forceLogout(row.loginName).then(() => {
          this.$confirm('操作成功', '提示', {
            showClose: false,
            showCancelButton: false,
            closeOnClickModal: false,
            type: 'success'
          }).then(_ => {
            this.getList()
          })
        }).catch(() => {
        })
      }).catch(() => {

      })
    }

  }
}
</script>

<style scoped>

</style>
