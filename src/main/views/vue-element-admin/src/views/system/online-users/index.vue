<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="loginName"
        label="用户"
      />
      <el-table-column
        prop="username"
        label="姓名"
      />
      <el-table-column
        prop="loginTime"
        label="登陆时间"
      />
      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button
            type="text"
            size="small"
            @click="handleForceLogout(scope.row)"
          >强制退出
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
