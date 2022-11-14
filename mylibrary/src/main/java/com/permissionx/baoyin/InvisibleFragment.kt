package com.permissionx.baoyin

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

typealias PermissionCallBack = (Boolean, List<String>) -> Unit
class InvisibleFragment : Fragment() {

    private var callback: ((Boolean, List<String>) -> Unit)? = null

    //这里用来获取运行时权限并把运行时权限进行申请
    @Suppress("DEPRECATION")
    fun requestNow(cb: PermissionCallBack, vararg permission: String){
        callback = cb
        requestPermissions(permission, 1)
    }

    //对申请的运行时权限进行处理
    @Suppress("OVERRIDE_DEPRECATION")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()){
                if ( result != PackageManager.PERMISSION_GRANTED){
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }
    }
}