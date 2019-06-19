package zs.qimai.com.viewtest

import android.support.v4.app.FragmentManager
import android.view.View

class DialogController {
    lateinit var fragmentManager: FragmentManager
    var layoutResId: Int = 0
    var width: Int = 0
    var height: Int = 0
    var gravity: Int = 0
    var onViewClickListener: OnViewClickListener? = null
    lateinit var dialogView: View
    var ids: IntArray? = null
    var paramsMap: MutableMap<Int, String>? = null

    class Params {
        lateinit var fragmentManager: FragmentManager
        var layoutResId: Int = 0
        var width: Int = 0
        var height: Int = 0
        var gravity: Int = 0
        var onViewClickListener: OnViewClickListener? = null
        var ids: IntArray? = null
        var paramsMap: MutableMap<Int, String> = mutableMapOf()
        lateinit var dialogView: View

        fun apply(dialogController: DialogController) {
            //dialogController.dialogView = this.dialogView
            dialogController.fragmentManager = this.fragmentManager
            dialogController.gravity = this.gravity
            dialogController.width = this.width
            dialogController.height = this.height
            dialogController.layoutResId = this.layoutResId
            dialogController.onViewClickListener = this.onViewClickListener
            dialogController.ids = this.ids
            dialogController.paramsMap = this.paramsMap
        }
    }

}