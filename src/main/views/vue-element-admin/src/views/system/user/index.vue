<template>
  <div class="app-container">
    <el-button type="primary" @click="handleAddUser">New User</el-button>

    <el-table v-loading="listLoading" :data="userList" style="width: 100%;margin-top:30px;" border>
      <el-table-column align="center" label="username" width="220">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="nickname" width="220">
        <template slot-scope="scope">
          {{ scope.row.nickname }}
        </template>
      </el-table-column>
      <el-table-column align="header-center" label="Description">
        <template slot-scope="scope">
          {{ scope.row.description }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="Operations">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope)">Edit</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      style="margin-top:30px;"
      :current-page.sync="listQuery.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size.sync="listQuery.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @current-change="getUsers"
      @size-change="search"
    />

    <el-dialog :visible.sync="dialogVisible" :title="dialogType==='edit'?'Edit User':'New User'">
      <el-form ref="form" :model="user" :rules="userRules" label-width="80px" label-position="left">
        <el-form-item label="User" prop="username">
          <el-input v-model="user.username" :disabled="dialogType==='edit'" placeholder="username" />
        </el-form-item>
        <el-form-item v-if="dialogType==='new'" label="Pass" prop="password">
          <el-input v-model="user.password" placeholder="password" type="password" :show-password="true" />
        </el-form-item>
        <el-form-item label="Nick" prop="nickname">
          <el-input v-model="user.nickname" placeholder="nickname" />
        </el-form-item>
        <el-form-item label="Desc">
          <el-input
            v-model="user.description"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="User Description"
          />
        </el-form-item>
        <el-form-item label="Role">
          <el-select v-model="user.role.id" clearable placeholder="请选择">
            <el-option
              v-for="item in rolesList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">Cancel</el-button>
        <el-button type="primary" @click="confirmUser">Confirm</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { deepClone } from '@/utils'
import { getUsers, validUsername, createUser, updateUser, deleteUser } from '@/api/user'
import { getAllRole } from '@/api/role'

const defaultUser = {
  id: '',
  username: '',
  password: '',
  nickname: '',
  avatar: '',
  introduction: '',
  role: {
    id: null
  }
}

export default {
  name: 'User',
  data() {
    const validateUsername = async(rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('The username can not be less than 5 digits'))
      } else {
        const { data } = await validUsername(this.user.id, value)
        if (!data) {
          callback(new Error('username already exits'))
        } else {
          callback()
        }
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (!value || value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      user: deepClone(defaultUser),
      userList: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        size: 10,
        sort: ''
      },
      dialogVisible: false,
      dialogType: 'new',
      userRules: {
        nickname: [{ required: true, trigger: 'blur' }],
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      rolesList: []
    }
  },
  created() {
    this.getUsers()
    this.getAllRole()
  },
  methods: {
    async getUsers() {
      this.listLoading = true
      const { data: { content, totalElements }} = await getUsers(this.listQuery)
      this.userList = content
      this.total = totalElements
      this.listLoading = false
    },
    async getAllRole() {
      const res = await getAllRole()
      this.rolesList = res.data
    },

    search() {
      this.listQuery.page = 1
      this.getUsers()
    },
    handleAddUser() {
      this.user = deepClone(defaultUser)
      this.dialogType = 'new'
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.form.resetFields()
      })
    },
    handleEdit(scope) {
      this.dialogType = 'edit'
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.form.clearValidate()
        const temp = deepClone(scope.row)
        if (!temp.role) {
          temp.role = {
            id: null
          }
        }
        this.user = temp
      })
    },
    handleDelete({ $index, row }) {
      this.$confirm('Confirm to remove the user?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(async() => {
          await deleteUser(row.id)
          this.$message.success('Delete succed!')
          this.getUsers()
        })
        .catch(err => { console.error(err) })
    },

    confirmUser() {
      this.$refs.form.validate(async(valid) => {
        if (valid) {
          const isEdit = this.dialogType === 'edit'
          if (isEdit) {
            await updateUser(this.user)
          } else {
            console
              .log(this.user)
            await createUser(this.user)
          }
          this.dialogVisible = false
          this.$message.success('succed!')
          this.getUsers()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }

  }
}
</script>

<style scoped>

</style>
